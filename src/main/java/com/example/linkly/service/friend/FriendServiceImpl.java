package com.example.linkly.service.friend;

import com.example.linkly.dto.friend.FriendResponseDto;
import com.example.linkly.entity.Friend;
import com.example.linkly.entity.User;
import com.example.linkly.exception.ApiException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.FriendRepository;
import com.example.linkly.repository.UserRepository;
import com.example.linkly.util.auth.ValidatorUser;
import com.example.linkly.util.exception.ExceptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class FriendServiceImpl implements FriendService {
    // 속성
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final ValidatorUser validatorUser;
    //기능
    //친구추가 비즈니스 로직
    @Transactional
    @Override
    public FriendResponseDto toggleFollow(UUID id, HttpServletRequest request) {
        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
        User me = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));
        User follow = userRepository.findById(id)
                .orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));
        log.info("내 id = {}, name = {} , email = {} , grade = {}",me.getId(), me.getName(), me.getEmail(), me.getGrade());
        log.info("친구 id = {}, name = {} , email = {} , grade = {}",follow.getId(), follow.getName(), follow.getEmail(), follow.getGrade());
        // 팔로우 상태 확인
        List<Friend> myFollowing = friendRepository.findByFollowing(me);
        log.info("myFollowing size = {}", myFollowing.size());
        // 상대방의 ID가 팔로우 목록에 있는지 확인
        boolean existingFriend = myFollowing.stream()
                .anyMatch(friend -> friend.getFollowing().getId().equals(id));
        log.info("친구 창에 있나요? ={} ", existingFriend);

        if (existingFriend) {
            // 이미 팔로우한 상태라면 언팔로우 처리
            log.info("삭제시작");
            friendRepository.deleteByFollowerAndFollowing(me, follow);
            log.info("삭제끝");
            return null;
        } else {
            // 팔로우하지 않은 상태라면 팔로우 처리
            Friend newFriend = new Friend(me, follow);
            log.info("{} << 이사람이 {} << 이사람을 팔로우 할게요!!",me.getName(),follow.getName());
            friendRepository.save(newFriend); // 팔로우 관계 저장
            return new FriendResponseDto(
                    newFriend.getId(),
                    newFriend.getFollowing().getId(),
                    follow.getName(),
                    newFriend.getFollowing().getProfileImg(),
                    newFriend.getCreatedAt()
            );
        }
    }

    //내가 팔로우 하는 사람 조회 비즈니스 로직
    @Transactional
    @Override
    public List<FriendResponseDto> getMyFollowings(HttpServletRequest request) {
        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
        User me = userRepository.findByEmail(userEmail).orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));
        // 내 아이디로 팔로잉 목록을 가져옴
        List<Friend> myFollowing = friendRepository.findByFollowing(me);

        List<UUID> followingIds = myFollowing.stream()
                .map(friend -> friend.getFollowing().getId())
                .collect(Collectors.toList());

        // 팔로잉들의 정보를 한 번의 쿼리로 가져옴
        List<User> followingUsers = userRepository.findAllById(followingIds);

        // 팔로워 리스트 반환
        return myFollowing.stream().map(friend -> {
            UUID followingId = friend.getFollowing().getId();
            User followingUser = followingUsers.stream()
                    .filter(user -> user.getId().equals(followingId))
                    .findFirst()
                    .orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));

            return new FriendResponseDto(
                    friend.getId(),
                    followingUser.getId(),
                    followingUser.getName(),
                    followingUser.getProfileImg(),
                    friend.getCreatedAt()
            );
        }).toList();
    }

    //내가 팔로우 하는 사람 조회 비즈니스 로직
    @Transactional
    @Override
    public List<FriendResponseDto> getMyFollowers(HttpServletRequest request) {
        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
        User me = userRepository.findByEmail(userEmail).orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));
        // 내 아이디로 팔로잉 목록을 가져옴
        List<Friend> myFollowing = friendRepository.findByFollower(me);

        // 팔로워의 아이디들만 먼저 조회하여 한 번에 가져옴
        List<UUID> followerIds = myFollowing.stream()
                .map(friend -> friend.getFollowing().getId())
                .collect(Collectors.toList());

        // 팔로워들의 정보를 한 번의 쿼리로 가져옴
        List<User> followingUsers = userRepository.findAllById(followerIds);

        // 팔로워 리스트 반환
        return myFollowing.stream().map(friend -> {
            UUID followingId = friend.getFollowing().getId();
            User followerUser = followingUsers.stream()
                    .filter(user -> user.getId().equals(followingId))
                    .findFirst()
                    .orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));

            return new FriendResponseDto(
                    friend.getId(),
                    followerUser.getId(),
                    followerUser.getName(),
                    followerUser.getProfileImg(),
                    friend.getCreatedAt()
            );
        }).toList();
    }
    //팔로우ㅡ취소 비즈니스 로직
    @Override
    public void deleteFollowing(Long friendId) {
        friendRepository.deleteById(friendId);
    }
    @Override
    public boolean isFollowed(UUID id, HttpServletRequest request) {
        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
        User me = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));

        // 내가 팔로우 중인 유저 목록을 가져옴
        List<Friend> myFollowing = friendRepository.findByFollowing(me);
        log.info("my Following size = {} ", myFollowing.size());
        // 상대방의 ID가 팔로우 목록에 있는지 확인
        return myFollowing.stream()
                .anyMatch(friend -> friend.getFollowing().getId().equals(id)); // 팔로우 중인 유저의 ID와 비교
    }

}
