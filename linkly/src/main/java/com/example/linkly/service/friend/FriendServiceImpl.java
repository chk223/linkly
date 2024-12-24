package com.example.linkly.service.friend;

import com.example.linkly.dto.friend.FriendRequestDto;
import com.example.linkly.dto.friend.FriendResponseDto;
import com.example.linkly.entity.Friend;
import com.example.linkly.entity.User;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.FriendRepository;
import com.example.linkly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {
    // 속성
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    //기능
    //친구추가 비즈니스 로직
    @Override
    public FriendResponseDto addFriend(FriendRequestDto request) {
        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        User follower = userRepository.findById(request.getFollowerId())
                .orElseThrow(() -> new IllegalArgumentException("팔로워 ID를 찾을 수 업습니다."));
        User following = userRepository.findById(request.getFollowingId())
                .orElseThrow(() -> new IllegalArgumentException("팔로잉 ID를 찾을 수 업습니다."));
        Friend friend = new Friend(follower, following);
        friendRepository.save(friend);

        return new FriendResponseDto(
                friend.getId(),
                friend.getFollower().getId(),
                friend.getFollowing().getId(),
                friend.getCreatedAt()
        );
    }
    //나를 팔로우 하는 사람 조회 비즈니스 로직
    @Override
    public List<FriendResponseDto> getMyFollowers(UUID userId) {
        List<Friend> followers = friendRepository.findByFollowerId(userId);
        return followers.stream().map(friend -> new FriendResponseDto(
                friend.getId(),
                friend.getFollower().getId(),
                friend.getFollowing().getId(),
                friend.getCreatedAt()
        )).collect(Collectors.toList());
    }
    //내가 팔로우 하는 사람 조회 비즈니스 로직
    @Override
    public List<FriendResponseDto> getMyFollowings(UUID userId) {
        List<Friend> followings = friendRepository.findByFollowingId(userId);
        return followings.stream().map(friend -> new FriendResponseDto(
                friend.getId(),
                friend.getFollower().getId(),
                friend.getFollowing().getId(),
                friend.getCreatedAt()
        )).collect(Collectors.toList());
    }
    //팔로우ㅡ취소 비즈니스 로직
    @Override
    public void deleteFollowing(Long friendId) {
        friendRepository.deleteById(friendId);
    }
}
