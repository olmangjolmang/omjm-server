package com.ticle.server.home.service;

import com.ticle.server.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class PostTopicCache {

    private static final Map<String, List<Post>> postTopicCache = new ConcurrentHashMap<>();

    public void saveToCache(String commonTitle, List<Post> posts) {
        postTopicCache.put(commonTitle, posts);
    }

    public List<Post> getPostsFromCache(String commonTitle) {
        return postTopicCache.get(commonTitle);
    }

    public void clearCache() {
        postTopicCache.clear();
    }

    public Map<String, List<Post>> getRandomPosts(int count) {
        List<Map.Entry<String, List<Post>>> allEntries = new ArrayList<>(postTopicCache.entrySet());
        Collections.shuffle(allEntries);

        Map<String, List<Post>> result = new HashMap<>();

        for (int i = 0; i < count; i++) {
            Map.Entry<String, List<Post>> entry = allEntries.get(i);
            result.put(entry.getKey(), new ArrayList<>(entry.getValue().subList(0, Math.min(count, entry.getValue().size()))));
        }

        return result;
    }
}