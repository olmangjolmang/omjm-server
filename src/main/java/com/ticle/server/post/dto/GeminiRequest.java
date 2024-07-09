package com.ticle.server.post.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class GeminiRequest {
    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Getter
    @Setter
    public static class Content {
        private Parts parts;
    }

    @Getter
    @Setter
    public static class Parts {
        private String text;

    }

    @Getter
    @Setter
    public static class GenerationConfig {
        private int candidate_count;
        private int max_output_tokens;
        private double temperature;

    }

    public GeminiRequest(String prompt) {
        this.contents = new ArrayList<>();
        Content content = new Content();
        Parts parts = new Parts();

        parts.setText(prompt);
        content.setParts(parts);

        this.contents.add(content);
        this.generationConfig = new GenerationConfig();
        this.generationConfig.setCandidate_count(1);
        this.generationConfig.setMax_output_tokens(1000);
        this.generationConfig.setTemperature(0.7);
    }
}

