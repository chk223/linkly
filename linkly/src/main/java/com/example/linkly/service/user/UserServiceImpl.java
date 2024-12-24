package com.example.linkly.service.user;

import com.example.linkly.config.PasswordEncoder;
import com.example.linkly.dto.user.PwUpdateRequestDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.dto.user.UserUpdateRequestDto;
import com.example.linkly.entity.User;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.UserRepository;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
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

        // (1) 탈퇴된 이메일 여부 확인

        // 탈퇴한 유저 리스트
        List<User> deletedUsers = userRepository.findDeletedUsersWithNativeQuery();

        if (!deletedUsers.isEmpty()) {
            User userWithEmail = null;
            for (User user : deletedUsers) {
                if (email.equals(user.getEmail())) {
                    userWithEmail = user;
                    break;
                }
            }
            // 가입하려는 이메일과 탈퇴한 유저의 이메일이 동일한 경우
            if (userWithEmail != null) {
                log.info("탈퇴한 사용자의 이메일입니다.");
                ErrorMessage errorMessage = ErrorMessage.valueOf("탈퇴한 사용자의 이메일입니다. ");
                throw new UserException(errorMessage.getMessage(), HttpStatus.CONFLICT); // 409 Conflict : 리소스 충돌
            }
        }
        // (2) 가입된 이메일 여부 확인
        else if (userRepository.findByEmail(email).isPresent()) {

            log.info("이미 가입된 이메일입니다.");
            ErrorMessage errorMessage = ErrorMessage.valueOf("이미 가입된 이메일입니다.");
            throw new UserException(errorMessage.getMessage(), HttpStatus.CONFLICT); // 409 Conflict : 리소스 충돌
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

        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserException(errorMessage.getMessage(), errorMessage.getStatus()));

        // 이름 수정
        if(dto.getName() != null) {
            user.updateName(dto.getName());
            log.info("이름 수정 완료: {} ", dto.getName());
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

        ErrorMessage errorMessage1 = ErrorMessage.ENTITY_NOT_FOUND;
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserException(errorMessage1.getMessage(), errorMessage1.getStatus()));

        // 비밀번호 검증 성공
        if(bcrypt.matches(dto.getOriginalPw(), user.getPassword())) {

            // originalPw == newPw CASE -> 예외처리
            if(bcrypt.matches(dto.getNewPw(), user.getPassword())) {
                log.info("이전과 동일한 비밀번호입니다. ");
                ErrorMessage errorMessage2 = ErrorMessage.VALID_ERROR;
                throw new UserException(errorMessage2.getMessage(), errorMessage2.getStatus());
            }
            user.updatePassword(bcrypt.encode(dto.getNewPw()));
        }

        // 비밀번호 검증 실패
        else{
            log.info("비밀번호 검증 실패");
            ErrorMessage errorMessage3 = ErrorMessage.PASSWORD_IS_WRONG;
            throw new UserException(errorMessage3.getMessage(), errorMessage3.getStatus());
        }

        userRepository.flush(); // flush 필수!!
        log.info("비밀번호 수정 완료");
    }

    // 유저 삭제
    @Override
    public void deleteUser(UUID id, String password) {

        User user = userRepository.findByIdOrElseThrow(id);

        // 비밀번호 검증 성공 -> 유저 삭제
        if(bcrypt.matches(password, user.getPassword())) {
            userRepository.delete(user);
            log.info("유저 삭제 완료");
        }
        // 비밀번호 검증 실패
        else{
            log.info("비밀번호 검증 실패");
            ErrorMessage errorMessage3 = ErrorMessage.PASSWORD_IS_WRONG;
            throw new UserException(errorMessage3.getMessage(), errorMessage3.getStatus());
        }
    }

    // 유저 등급 설정
    @Transactional
    @Override
    public void updateGrade(UUID id) {

        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserException(errorMessage.getMessage(), errorMessage.getStatus()));

        // 등급 설정
        user.updateGrade();

        userRepository.flush();

        log.info("등급 설정 완료 {}", user.getGrade());
    }
}
