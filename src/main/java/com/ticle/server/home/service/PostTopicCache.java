package com.ticle.server.home.service;

import com.ticle.server.home.dto.response.PostSetsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class PostTopicCache {

    private static final Map<String, List<PostSetsResponse>> postTopicCache = new ConcurrentHashMap<>();

    public void saveToCache(String commonTitle, List<PostSetsResponse> posts) {
        postTopicCache.put(commonTitle, posts);
    }

    public List<PostSetsResponse> getPostsFromCache(String commonTitle) {
        return postTopicCache.get(commonTitle);
    }

    public void clearCache() {
        postTopicCache.clear();
    }

    public Map<String, List<PostSetsResponse>> getRandomPosts(int count) {
        List<Map.Entry<String, List<PostSetsResponse>>> allEntries = new ArrayList<>(postTopicCache.entrySet());
        Collections.shuffle(allEntries);

        Map<String, List<PostSetsResponse>> result = new HashMap<>();

        for (int i = 0; i < count; i++) {
            Map.Entry<String, List<PostSetsResponse>> entry = allEntries.get(i);

            int resultSize = Math.min(count, entry.getValue().size());

            result.put(entry.getKey(), new ArrayList<>(entry.getValue().subList(0, resultSize)));
        }

        return result;
    }
}