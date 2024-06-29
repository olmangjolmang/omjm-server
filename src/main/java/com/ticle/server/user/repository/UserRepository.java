package com.ticle.server.user.repository;

import com.ticle.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public class UserRepository extends JpaRepository<User,Long> {

}
