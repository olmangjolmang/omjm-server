package com.ticle.server.post.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeminiResponse {

    private List<Candidate> candidates;
    private PromptFeedback promptFeedback;

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

}
