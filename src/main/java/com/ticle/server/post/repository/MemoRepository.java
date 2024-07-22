package com.ticle.server.post.repository;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    Page<Memo> findByUserId(Long userId, Pageable pageable);

    Memo findByUserAndTargetTextAndContent(User user, String targetText, String content);
    
}
