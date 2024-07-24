package com.ticle.server.mypage.service;

import com.ticle.server.global.dto.PageInfo;
import com.ticle.server.memo.domain.Memo;
import com.ticle.server.mypage.dto.request.NoteUpdateRequest;
import com.ticle.server.mypage.dto.response.NoteResponse;
import com.ticle.server.mypage.dto.response.QnAResponse;
import com.ticle.server.mypage.dto.response.SavedTicleResponse;
import com.ticle.server.mypage.dto.response.SavedTicleResponseList;
import com.ticle.server.opinion.domain.Comment;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.opinion.exception.OpinionNotFoundException;
import com.ticle.server.opinion.repository.CommentRepository;
import com.ticle.server.opinion.repository.OpinionRepository;
import com.ticle.server.post.repository.MemoRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.repository.ScrappedRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.user.exception.UserNotFoundException;
import com.ticle.server.user.exception.errorcode.UserErrorCode;
import com.ticle.server.user.jwt.CustomUserDetails;
import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.ticle.server.opinion.domain.type.Order.TIME;
import static com.ticle.server.opinion.domain.type.Order.getOrder;
import static com.ticle.server.opinion.exception.errorcode.OpinionErrorCode.OPINION_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final ScrappedRepository scrappedRepository;
    private final OpinionRepository opinionRepository;
    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final int SIZE = 9;

    public SavedTicleResponseList getSavedArticles(CustomUserDetails customUserDetails, int page) {
        Pageable pageable = PageRequest.of(page - 1, SIZE, getOrder(TIME));

        Long userId;

        try{
            userId = customUserDetails.getUserId();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }

        Page<Scrapped> scrappeds = scrappedRepository.findByUserId(userId,pageable);
        PageInfo pageInfo = PageInfo.from(scrappeds);

        List<SavedTicleResponse> responses = scrappeds.stream()
                .map(SavedTicleResponse::toDto)
                .toList();

        return SavedTicleResponseList.of(pageInfo, responses);
    }

    public SavedTicleResponseList getSavedArticlesByCategory(CustomUserDetails customUserDetails, Category category, int page) {
        Pageable pageable = PageRequest.of(page - 1, SIZE, getOrder(TIME));

        Long userId;

        try {
            userId = customUserDetails.getUserId();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }

        Page<Scrapped> scrappeds = scrappedRepository.findByUserIdAndPostCategory(userId, category, pageable);
        PageInfo pageInfo = PageInfo.from(scrappeds);

        List<SavedTicleResponse> responses = scrappeds.stream()
                .map(SavedTicleResponse::toDto)
                .toList();

        return SavedTicleResponseList.of(pageInfo, responses);
    }


    //////////////////////////////////////////////티클문답///////////////////////////////////////////////////////////////


    public List<QnAResponse> getMyQnA(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        Page<Comment> comments = commentRepository.findByUser(user,pageable);
        PageInfo pageInfo = PageInfo.from(comments);

        return comments.stream()
                .map(comment -> {
                    log.info("Fetching comment for userId: {}, opinionId: {}", userId, comment.getOpinion().getOpinionId());
                    Opinion opinion = opinionRepository.findByOpinionIdWithFetch(comment.getOpinion().getOpinionId())
                            .orElseThrow(() -> new RuntimeException("댓글이 없습니다"));
                    return new QnAResponse(opinion.getQuestion(), comment.getOpinion().getOpinionId(), comment.getContent(), opinion.getCreatedDate().format(DateTimeFormatter.ISO_DATE_TIME), pageInfo);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long userId, Long opinionId, String newContent) {Comment comment = commentRepository.findByUserIdAndOpinionId(userId,opinionId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to edit this comment");
        }

        comment.updateContent(newContent);
    }

    @Transactional
    public void deleteComment(Long userId, Long questionId) {
        Comment comment = commentRepository.findByUserIdAndOpinionId(userId, questionId)
                .orElseThrow(() -> new RuntimeException("Comment Not Found"));

        Opinion opinion = opinionRepository.findById(questionId)
                .orElseThrow(() -> new OpinionNotFoundException(OPINION_NOT_FOUND));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to delete this comment");
        }

        commentRepository.delete(comment);
        opinion.subCommentCount();
    }

    //////////////////////////////////////////////티클노트///////////////////////////////////////////////////////////////

    public List<NoteResponse> getMyNotes(Long userId,Pageable pageable) {
        Page<Memo> memos = memoRepository.findByUserId(userId,pageable);
        PageInfo pageInfo = PageInfo.from(memos);
        return memos.stream()
                .map(memo -> NoteResponse.toDto(memo, pageInfo))
                .collect(Collectors.toList());
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
