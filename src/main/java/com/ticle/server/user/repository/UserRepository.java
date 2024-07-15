package com.ticle.server.user.repository;

import com.ticle.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
    boolean existsBynickName(String nickName);
    Optional<User> findByEmail(String email);
}