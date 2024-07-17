package com.ticle.server.mypage.service;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.mypage.dto.MyNoteDto;
import com.ticle.server.mypage.dto.MyQuestionDto;
import com.ticle.server.mypage.dto.SavedTicleDto;
import com.ticle.server.mypage.repository.NoteRepository;
import com.ticle.server.opinion.domain.Comment;
import com.ticle.server.opinion.repository.CommentRepository;
import com.ticle.server.opinion.repository.OpinionRepository;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.user.repository.UserRepository;
import com.ticle.server.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.ticle.server.scrapped.repository.ScrappedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final ScrappedRepository scrappedRepository;
    private final OpinionRepository opinionRepository;
    private final NoteRepository noteRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final int SIZE = 9;

    public List<SavedTicleDto> getSavedArticles(Long userId,Pageable pageable) {
//        Pageable pageable = PageRequest.of(page-1,SIZE);
        Page<Scrapped> scraps = scrappedRepository.findByUserId(userId,pageable);

        return scraps.stream()
                .map(scrap -> postRepository.findById(scrap.getPost().getPostId()).orElse(null))
                .filter(post -> post != null)
                .map(SavedTicleDto::toDto)
                .collect(Collectors.toList());
    }

    public List<SavedTicleDto> getSavedArticlesByCategory(Long userId, Category category,Pageable pageable) {
//        Pageable pageable = PageRequest.of(page-1,SIZE);
        Page<Scrapped> scraps = scrappedRepository.findByUserIdAndPostCategory(userId, category,pageable);

        return scraps.stream()
                .map(scrap -> postRepository.findById(scrap.getPost().getPostId()).orElse(null))
                .filter(post -> post != null)
                .map(SavedTicleDto::toDto)
                .collect(Collectors.toList());
    }


    public List<MyQuestionDto> getMyQnA(Long userId) {
        Long tmp = 1L;
        List<Opinion> questions = opinionRepository.findByUserId(userId);
        Optional<Comment> comments = commentRepository.findByUserIdAndOpinionId(userId,tmp);
        return questions.stream()
                .map(MyQuestionDto::toDto)
                .collect(toList());
    }

    @Transactional
    public void updateComment(Long userId, Long opinionId, String newContent) {
//        Optional<User> user = userRepository.findById(userId);
//        Optional<Opinion> opinion = opinionRepository.findByOpinionIdWithFetch(opinionId);
//        Optional<Comment> comment = commentRepository.findByUserIdAndOpinionId(userId,opinionId);

        Comment comment = commentRepository.findByUserIdAndOpinionId(userId,opinionId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to edit this comment");
        }

        comment.updateContent(newContent);
//        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long userId, Long questionId) {
        Comment comment = commentRepository.findByUserIdAndOpinionId(userId, questionId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to delete this comment");
        }

        commentRepository.delete(comment);
    }


    public List<MyNoteDto> getMyNotes(Long userId) {
        List<Memo> memos = noteRepository.findByUserId(userId);
        return memos.stream()
                .map(MyNoteDto::toDto)
                .collect(toList());
    }

}
