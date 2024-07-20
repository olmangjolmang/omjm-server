package com.ticle.server.user.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisDao {

    private final RedisTemplate<String, String> redisTemplate;

    public void setRefreshToken(String email, String refreshToken, long refreshTokenTime) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(refreshToken.getClass())); // 리프레쉬 토큰을 직렬화 하는 코드 ( 데이터 압축효과도 있음 )
        redisTemplate.opsForValue().set(email, refreshToken,refreshTokenTime, TimeUnit.MINUTES);
        //opsForValue(): RedisTemplate에서 ValueOperations를 가져옵니다.
        //ValueOperations 객체를 통해 Redis의 값을 저장, 조회, 삭제하는 작업을 수행합니다.
    }


    /**
     * 키로 값을 조회
     * @param key : email
     * @return 해당 리프레쉬토큰
     */
    public String getRefreshToken(String key) {
        return  redisTemplate.opsForValue().get(key);
    }

    /**
     *키로 값을 삭제
     * @param key : email
     */
    public void deleteRefreshToken(String key) {
        redisTemplate.delete(key);
    }


    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setBlackList(String accessToken, String msg, Long minutes) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(msg.getClass()));
        redisTemplate.opsForValue().set(accessToken, msg, minutes, TimeUnit.MINUTES);
    }

    public String getBlackList(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /***
     * 레디스에 있는 모든 데이터를 삭제
     */
    public void flushAll(){
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }
    //serverCommands().flushAll()을 호출하여 Redis 서버의 flushAll() 명령을 실행하여 모든 데이터를 삭제합니다.
}
