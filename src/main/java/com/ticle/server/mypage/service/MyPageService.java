package com.ticle.server.mypage.service;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.mypage.dto.request.NoteUpdateRequest;
import com.ticle.server.mypage.dto.response.NoteResponse;
import com.ticle.server.mypage.dto.response.QnAResponse;
import com.ticle.server.mypage.dto.response.SavedTicleResponse;
import com.ticle.server.post.repository.MemoRepository;
import com.ticle.server.opinion.domain.Comment;
import com.ticle.server.opinion.repository.CommentRepository;
import com.ticle.server.opinion.repository.OpinionRepository;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.user.repository.UserRepository;
import com.ticle.server.user.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import com.ticle.server.scrapped.repository.ScrappedRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MyPageService {
    private final UserRepository userRepository;
    private final ScrappedRepository scrappedRepository;
    private final OpinionRepository opinionRepository;
    private final MemoRepository memoRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final int SIZE = 9;

    public List<SavedTicleResponse> getSavedArticles(CustomUserDetails customUserDetails, Pageable pageable) {
//        Pageable pageable = PageRequest.of(page-1,SIZE);
        log.info("Pageable: page={}, size={}, sort={}", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        Long userId = customUserDetails.getUserId();
        Page<Scrapped> scraps = scrappedRepository.findByUserId(userId,pageable);

        return scraps.stream()
                .map(scrap -> postRepository.findById(scrap.getPost().getPostId()).orElse(null))
                .filter(post -> post != null)
                .map(SavedTicleResponse::toDto)
                .collect(Collectors.toList());
    }

    public List<SavedTicleResponse> getSavedArticlesByCategory(CustomUserDetails customUserDetails, Category category, Pageable pageable) {
//        Pageable pageable = PageRequest.of(page-1,SIZE);
        log.info("Pageable: page={}, size={}, sort={}", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        Long userId = customUserDetails.getUserId();
        Page<Scrapped> scraps = scrappedRepository.findByUserIdAndPostCategory(userId, category,pageable);

        return scraps.stream()
                .map(scrap -> postRepository.findById(scrap.getPost().getPostId()).orElse(null))
                .filter(post -> post != null)
                .map(SavedTicleResponse::toDto)
                .collect(Collectors.toList());
    }


    //////////////////////////////////////////////티클문답///////////////////////////////////////////////////////////////


    public List<QnAResponse> getMyQnA(Long userId, Pageable pageable) {
        Page<Opinion> opinions = opinionRepository.findByUserId(userId,pageable);
        return opinions.stream()
                .map(opinion -> {
                    Optional<Comment> commentOpt = commentRepository.findByUserIdAndOpinionId(userId, opinion.getOpinionId());
                    String comment = commentOpt.get().getContent();
                    return new QnAResponse(opinion.getQuestion(), comment, opinion.getCreatedDate());
                })
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

    //////////////////////////////////////////////티클노트///////////////////////////////////////////////////////////////

    public List<NoteResponse> getMyNotes(Long userId) {
        List<Memo> memos = memoRepository.findByUserId(userId);
        return memos.stream()
                .map(NoteResponse::toDto)
                .collect(toList());
    }

    @Transactional
    public void updateNote(CustomUserDetails customUserDetails, Long noteId, NoteUpdateRequest noteUpdateRequest){
        Memo memo = memoRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Memo not found"));

        if (!memo.getUser().getId().equals(customUserDetails.getUserId())) {
            throw new RuntimeException("You do not have permission to edit this memo");
        }

        memo.updateNote(noteUpdateRequest.getContent());
        memoRepository.save(memo);
    }

    @Transactional
    public void deleteNote(CustomUserDetails customUserDetails, Long noteId) {
        Memo memo = memoRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Memo not found"));

        if (!memo.getUser().getId().equals(customUserDetails.getUserId())) {
            throw new RuntimeException("You do not have permission to delete this memo");
        }
        memoRepository.delete(memo);
    }

}
