package com.ticle.server.post.service;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.mypage.repository.MemoRepository;
import com.ticle.server.scrapped.dto.ScrappedDto;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.repository.ScrappedRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ScrappedRepository scrappedRepository;
    private final UserService userService;
    private final MemoRepository memoRepository;

    // 카테고리에 맞는 글 찾기
    public Page<Post> findAllByCategory(String category, Pageable pageable) {
        if (category == null || category.isEmpty()) {
            // 모든 글 조회
            return postRepository.findAll(pageable);
        } else {
            //카테고리에 맞는 글 조회
            return postRepository.findByCategory(Category.valueOf(category), pageable);
        }
    }

    //postId로 조회한 특정 post 정보 리턴
    public Post findById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }


    public Object scrappedById(long id, UserDetails userDetails) {
        // 게시물 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 post 찾을 수 없음 id: " + id));

        // getUsername에는 email이 들어있음. / email로 유저 찾고 id 찾도록 함.
        User user = userService.getLoginUserByEmail(userDetails.getUsername());
        Long userId = user.getId();
//            System.out.println("User ID: " + userId);

        // 이미 스크랩했는지 확인
        Optional<Scrapped> existingScrap = scrappedRepository.findByUserIdAndPost_PostId(userId, post.getPostId());
        if (existingScrap.isPresent()) {
            // 이미 스크랩한 상태라면 스크랩 취소
            existingScrap.get().changeToUnscrapped(); //status를 unscrapped로 변경
            scrappedRepository.delete(existingScrap.get());
            return ScrappedDto.from(existingScrap.get());
        }

        Scrapped scrapped = new Scrapped();
        scrapped.setPost(post);
        scrapped.setUser(user);
        scrapped.changeToScrapped(); //status를 scrapped로 변경

        return scrappedRepository.save(scrapped);
    }

    public Object writeMemo(long id, UserDetails userDetails, String targetText, String content) {

        // getUsername에는 email이 들어있음. / email로 유저 찾고 id 찾도록 함.
        User user = userService.getLoginUserByEmail(userDetails.getUsername());

        // 같은 내용의 targetText-content 세트가 있는지 확인
        Memo existingMemo = memoRepository.findByUserAndTargetTextAndContent(user, targetText, content);

        if (existingMemo != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 동일한 메모가 존재합니다.");
        }

        Memo memo = new Memo();
        memo.setPost(postRepository.findByPostId(id));
        memo.setUser(user);
        memo.setTargetText(targetText);
        memo.setContent(content);
        
        return memoRepository.save(memo);
    }
}
