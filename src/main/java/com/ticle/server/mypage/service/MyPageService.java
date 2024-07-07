package com.ticle.server.mypage.service;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.mypage.dto.MyNoteDto;
import com.ticle.server.mypage.dto.MyQuestionDto;
import com.ticle.server.mypage.dto.SavedTicleDto;
import com.ticle.server.mypage.repository.MemoRepository;
import com.ticle.server.mypage.repository.QuestionRepository;
import com.ticle.server.mypage.repository.ScrapRepository;
import com.ticle.server.opinion.repository.CommentRepository;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.user.domain.type.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final ScrapRepository scrapRepository;
    private final QuestionRepository questionRepository;
    private final MemoRepository memoRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<SavedTicleDto> getSavedArticles(Long userId) {
        List<Scrapped> scraps = scrapRepository.findByUserId(userId);

        return scraps.stream()
                .map(scrap -> postRepository.findById(scrap.getPost().getPostId()).orElse(null))
                .filter(post -> post != null)
                .map(SavedTicleDto::toDto)
                .collect(Collectors.toList());
    }

    public List<SavedTicleDto> getSavedArticlesByCategory(Long userId, Category category) {
        List<Scrapped> scraps = scrapRepository.findByUserIdAndPostCategory(userId, category);

        return scraps.stream()
                .map(scrap -> postRepository.findById(scrap.getPost().getPostId()).orElse(null))
                .filter(post -> post != null)
                .map(SavedTicleDto::toDto)
                .collect(Collectors.toList());
    }


    public List<MyQuestionDto> getMyQuestions(Long userId) {
        List<Opinion> questions = questionRepository.findByUserId(userId);
        return questions.stream()
                .map(MyQuestionDto::toDto)
                .collect(toList());
    }

    public List<MyNoteDto> getMyNotes(Long userId) {
        List<Memo> memos = memoRepository.findByUserId(userId);
        return memos.stream()
                .map(MyNoteDto::toDto)
                .collect(toList());
    }
}
