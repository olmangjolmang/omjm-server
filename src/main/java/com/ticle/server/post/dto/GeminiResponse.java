package com.ticle.server.post.dto;

import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import lombok.*;
import org.springframework.util.CollectionUtils;

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

//    // postDetail의 recommenPosts 결과를 List 형태로 만들어주는 함수
//    public List<Post> formatRecommendPost(GeminiResponse response) {
//        List<Post> recommendPosts = new ArrayList<>();
//
//        if (response.getCandidates() != null) {
//            for (GeminiResponse.Candidate candidate : response.getCandidates()) {
//                GeminiResponse.Content content = candidate.getContent();
//                if (content != null && content.getParts() != null && !content.getParts().isEmpty()) {
//                    String combinedString = content.getParts().get(0).getText();
//                    String[] parts = combinedString.split("결과 =");
//
//                    if (parts.length == 2) {
//                        String postIdPart = parts[1].trim();
//                        // Extracting the numbers between the brackets
//                        int startIndex = postIdPart.indexOf('[') + 1;
//                        int endIndex = postIdPart.indexOf(']');
//                        if (startIndex > 0 && endIndex > startIndex) {
//                            postIdPart = postIdPart.substring(startIndex, endIndex);
//                            String[] postIdStrings = postIdPart.split(",\\s*");
//
//                            for (String postIdString : postIdStrings) {
//                                try {
//                                    Long postId = Long.parseLong(postIdString.trim());
//                                    postRepository.findById(postId).ifPresent(recommendPosts::add);
//                                } catch (NumberFormatException e) {
//                                    // Handle the case where the ID is not a valid number
//                                    System.out.println("Invalid postId format: " + postIdString);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return recommendPosts;
//    }


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

                if (content != null && !CollectionUtils.isEmpty(content.getParts())) {
                    text = content.getParts().get(0).getText();
                }
            }
        }

        return text;
    }
}