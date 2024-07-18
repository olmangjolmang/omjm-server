package com.ticle.server.user.repository;

import com.ticle.server.user.domain.User;
import com.ticle.server.user.redis.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
//    @Cacheable(cacheNames = CacheNames.USERBYEMAIL, key = "'login'+#p0", unless = "#result==null")
//    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

}