package pt.galina.chap_18_googlecloud.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RedisService {

    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    @Autowired
    public RedisService(ReactiveRedisTemplate<String, Object> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public Mono<Object> getValue(String key) {
        return reactiveRedisTemplate.opsForValue().get(key);
    }

    public Mono<Boolean> setValue(String key, Object value) {
        return reactiveRedisTemplate.opsForValue().set(key, value);
    }
}
