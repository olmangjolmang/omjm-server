package com.ticle.server.mypage.service;

import com.ticle.server.mypage.repository.MemoRepository;
import com.ticle.server.mypage.repository.QuestionRepository;
import com.ticle.server.mypage.repository.ScrapRepository;
import com.ticle.server.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
    private ScrapRepository scrapRepository;
    private QuestionRepository questionRepository;
    private MemoRepository memoRepository;
    private PostRepository postRepository;


}
