package com.ticle.server.post.dto;

import com.ticle.server.post.repository.PostRepository;
import lombok.*;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeminiResponse {

    private List<Candidate> candidates;
    private PromptFeedback promptFeedback;
    private PostRepository postRepository;

    @Data
    public static class Candidate {
        private Content content;
        private String finishReason;
        private int index;
        private List<SafetyRating> safetyRatings;
    }

    @Data
    public static class Content {
        private List<Parts> parts;
        private String role;
    }

    @Data
    public static class Parts {
        private String text;
    }

    @Data
    public static class SafetyRating {
        private String category;
        private String probability;
    }

    @Data
    public static class PromptFeedback {
        private List<SafetyRating> safetyRatings;
    }

    // Gemini 리턴
    // postDetail의 recommenPosts 결과를 List 형태로 만들어주는 함수
    public List<Map<String, Object>> formatRecommendPost() {
        List<Map<String, Object>> recommendPosts = new ArrayList<>();

        if (candidates != null) {
            for (Candidate candidate : candidates) {
                Content content = candidate.getContent();
                if (content != null && content.getParts() != null && content.getParts().size() > 0) {
                    String combinedString = content.getParts().get(0).getText();

                    String[] parts = combinedString.split("postTitle=");

                    if (parts.length == 2) {
                        String postId = parts[0].substring(parts[0].indexOf("[") + 1, parts[0].indexOf("]"));
                        String postTitle = parts[1].substring(parts[1].indexOf("[") + 1, parts[1].indexOf("]"));

                        //postid, posttitle를 각각 list화
                        String[] postIds = postId.split(", ");
                        String[] postTitles = postTitle.split(", ");

                        for (int i = 0; i < postIds.length; i++) {
                            Map<String, Object> postMap = new LinkedHashMap<>();
                            postMap.put("postId", Long.parseLong(postIds[i]));
                            postMap.put("postTitle", postTitles[i].replaceAll("'", ""));
                            recommendPosts.add(postMap);
                        }
                    }
                }
            }
        }

        return recommendPosts;
    }


    // quiz 생성 데이터 format
    public List<QuizResponse> formatQuiz(String postTitle) {
        List<QuizResponse> quizzes = new ArrayList<>();
        if (candidates != null) {
            System.out.println("Candidates found: " + candidates.size());
            int quizNo = 1;

            for (Candidate candidate : candidates) {
                if (candidate.getContent() != null && candidate.getContent().getParts() != null) {
                    List<Parts> partsList = candidate.getContent().getParts();

                    StringBuilder sb = new StringBuilder();
                    for (Parts part : partsList) {
                        sb.append(part.getText()).append("\n\n");
                    }

                    String[] questions = sb.toString().split("\n\n");

                    for (String question : questions) {
                        String[] lines = question.split("\n");
                        if (lines.length < 5) {
                            continue;
                        }

                        String questionNumber = lines[0].split(": ")[1].trim(); //문제 번호
                        String questionContent = lines[1].split(": ")[1].trim(); // 문제

                        Map<String, String> multipleChoice = new LinkedHashMap<>();
                        for (int i = 2; i < lines.length - 1; i++) {
                            String[] choiceParts = lines[i].split(": "); //보기의 알파벳과 문자 나눔
                            multipleChoice.put(choiceParts[0], String.join(" ", Arrays.copyOfRange(choiceParts, 1, choiceParts.length)));
                        }

                        String answer = lines[lines.length - 1].split(": ")[1].trim();

                        QuizResponse quizResponse = new QuizResponse((long) quizNo++, postTitle, questionContent, multipleChoice, answer);
                        quizzes.add(quizResponse);
                    }
                }
            }
        }
        System.out.println("Formatted quizzes: " + quizzes);
        return quizzes;
    }

    public String getCommonTitle() {
        String text = "";

        if (candidates != null) {
            for (Candidate candidate : candidates) {
                Content content = candidate.getContent();
                if (content != null && content.getParts() != null && !content.getParts().isEmpty()) {
                    text = content.getParts().get(0).getText();
                }
            }
        }

        return text;
    }
}