package com.example.linkly.repository;

import com.example.linkly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    List<User> findByNameLike(String name);

    default User findByIdOrElseThrow(UUID id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exitst id = " + id));
    }

    // 탈퇴 유저 조회
    // 네이티브 SQL을 사용하여 deleted = true인 유저 조회
    @Query(value = "SELECT * FROM user WHERE deleted = true", nativeQuery = true)
    List<User> findDeletedUsersWithNativeQuery();

}
