package com.ticle.server.mypage.service;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.mypage.dto.MyNoteDto;
import com.ticle.server.mypage.dto.MyQuestionDto;
import com.ticle.server.mypage.dto.SavedTicleDto;
import com.ticle.server.mypage.repository.MemoRepository;
import com.ticle.server.mypage.repository.QuestionRepository;
import com.ticle.server.mypage.repository.ScrapRepository;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.talk.domain.Talk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class MyPageService {
    private ScrapRepository scrapRepository;
    private QuestionRepository questionRepository;
    private MemoRepository memoRepository;
    private PostRepository postRepository;
    @Autowired
    public MyPageService(ScrapRepository scrapRepository, QuestionRepository questionRepository, MemoRepository memoRepository, PostRepository postRepository) {
        this.scrapRepository = scrapRepository;
        this.questionRepository = questionRepository;
        this.memoRepository = memoRepository;
        this.postRepository = postRepository;
    }
    public List<SavedTicleDto> getSavedArticles(Long userId) {
        List<Scrapped> scraps = scrapRepository.findByUserId(userId);
        List<SavedTicleDto> savedArticles = new ArrayList<>();
        for (Scrapped scrap : scraps) {
            Post post = postRepository.findById(scrap.getPost().getPostId()).orElse(null);
            if (post != null) {
                SavedTicleDto dto = new SavedTicleDto();
                dto.setPostId(post.getPostId());
                dto.setTitle(post.getTitle());
                dto.setContent(post.getContent());
                dto.setAuthor(post.getAuthor());
                dto.setCreateDate(post.getCreatedDate());
                dto.setPostCategory(post.getCategory());
                dto.setImage(post.getImage());
                savedArticles.add(dto);
            }
        }
        return savedArticles;
    }

    public List<MyQuestionDto> getMyQuestions(Long userId) {
        List<Talk> questions = questionRepository.findByUserId(userId);
        return questions.stream().map(question -> {
            MyQuestionDto dto = new MyQuestionDto();
            dto.setQuestionId(question.getTalkId());
            dto.setQuestion(question.getQuestion());
            dto.setView(question.getView());
            dto.setCommentCount(question.getTalkId());
            return dto;
        }).collect(toList());
    }

    public List<MyNoteDto> getMyNotes(Long userId) {
        List<Memo> memos = memoRepository.findByUserId(userId);
        List<MyNoteDto> myNotes = new ArrayList<>();
        for (Memo memo : memos) {
            Post post = postRepository.findById(memo.getMemoId()).orElse(null);
            if (post != null) {
                MyNoteDto dto = new MyNoteDto();
                dto.setMemoId(memo.getMemoId());
                dto.setContent(memo.getContent());
                dto.setMemoDate(memo.getMemoDate());
                dto.setPostId(post.getPostId());
                dto.setPostTitle(post.getTitle());
                myNotes.add(dto);
            }
        }
        return myNotes;
    }





}
