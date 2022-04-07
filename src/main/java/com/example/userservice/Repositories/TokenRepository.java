package com.example.userservice.Repositories;

import com.example.userservice.Models.Token;
import com.example.userservice.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TokenRepository extends JpaRepository<Token,Long> {

    Optional<Token> findByPayload(String payload);

    /**
     *
     * @param payload Use this params to find Token
     * @param confirmLocalDateTime update confirmed date
     * @return
     */
    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.confirmedAt = ?2 WHERE t.payload = ?1")
    Integer updateConfirmedAt(String payload, LocalDateTime confirmLocalDateTime);

    @Query("SELECT u FROM Token t LEFT JOIN t.user u")
    Optional<User> findUsersByToken(String token);
}
