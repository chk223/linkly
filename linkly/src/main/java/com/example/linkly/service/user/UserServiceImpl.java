package com.example.linkly.service.user;

import com.example.linkly.config.PasswordEncoder;
import com.example.linkly.dto.user.PwUpdateRequestDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.dto.user.UserUpdateRequestDto;
import com.example.linkly.entity.User;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    PasswordEncoder bcrypt = new PasswordEncoder();

    // 유저 생성
    @Override
    public void signUp(String email, String password, String userName) {

        if (userRepository.findByEmail(email) != null) {
            log.info("이미 존재하는 이메일입니다.");
            throw new RuntimeException("이미 존재하는 이메일입니다."); //수정
        }
        User user = new User(email, password, userName, null, null, null); // 프로필 사진, 소개, 링크는 프로필 수정에서
        User save = userRepository.save(user);
        log.info("user name : {} 수정날짜는 {} 등록날짜는 {}", save.getName(), save.getUpdatedAt(), save.getCreatedAt());
    }


    // 유저 조회
    @Override
    public List<UserResponseDto> findByNameContains(String name){

        List<User> userList = userRepository.findByNameLike("%"+name+"%"); // %LIKE% : name이 포함된 유저 조회
        List<UserResponseDto> userResponseDtoList =
                userList.stream()
                        .map(user -> new UserResponseDto(user))
                        .collect(Collectors.toList());

        return userResponseDtoList;
    }

    // 유저 수정
    @Transactional
    @Override
    public void updateUser(UUID id, UserUpdateRequestDto dto) {

        User user = userRepository.findByIdOrElseThrow(id);

//        User user = userRepository.findById(id).orElseThrow(() ->
//                new UserException("아이디에 해당하는 유저가 없습니다.", HttpStatus.NOT_FOUND));
        // 이름 수정
        if(dto.getName() != null) {
            user.updateName(dto.getName());
            log.info("이름 수정 완료: {} ", dto.getName());
        }

        // 비밀번호 수정
        if(dto.getPassword() != null) {
            user.updatePassword(bcrypt.encode(dto.getPassword()));
            log.info("비밀번호 수정 완료");
        }

        // 프로필 사진 수정
        if(dto.getProfileImg() != null) {
            user.updateProfileImg(dto.getProfileImg());
            log.info("프로필 사진 수정 완료: {} ", dto.getProfileImg());
        }

        // 프로필 소개 수정
        if(dto.getProfileIntro() != null) {
            user.updateProfileIntro(dto.getProfileIntro());
            log.info("프로필 소개 수정 완료: {} ", dto.getProfileIntro());
        }

        // 프로필 링크 수정
        if(dto.getProfileUrl() != null) {
            user.updateProfileUrl(dto.getProfileUrl());
            log.info("프로필 링크 수정 완료: {} ", dto.getProfileUrl());
        }
        userRepository.flush(); // flush 필수!!
    }

    // 비밀번호 변경
    @Transactional
    @Override
    public void updatePw(UUID id, PwUpdateRequestDto dto) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserException(errorMe));


    }

    @Override
    public void deleteUser(UUID id, String password) {

        User user = userRepository.findByIdOrElseThrow(id);

        // 비밀번호 일치 -> 유저 삭제
        if(bcrypt.matches(password, user.getPassword())) {
            userRepository.delete(user);
            log.info("유저 삭제 완료");
        }

        // 비밀번호 불일치
        ErrorMessage errorMessage = ErrorMessage.PASSWORD_IS_WRONG;
    }
}
