package com.example.Lab4.Aspects;

import com.example.Lab4.Annotations.Cache;
import com.example.Lab4.Redis.RedisClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Aspect
@Component
public class CachingAspect {

    private static final Logger log = LoggerFactory.getLogger(CachingAspect.class);

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Around("@annotation(cache)")
    public Object getFromCache(ProceedingJoinPoint joinPoint, Cache cache) throws Throwable {
        String cacheKey = cache.cacheKey();
        Duration cacheTTL = Duration.of(cache.cacheTTL(), cache.timeUnit().toChronoUnit());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class returnType = signature.getReturnType();

        // 1. Try fetching from cache
        try {
            String cachedJson = redisClient.get(cacheKey);
            if (cachedJson != null) {
                // Deserialize JSON string back to Objects
                log.info("Cache hit for key: {}", cacheKey);
                return objectMapper.readValue(cachedJson, objectMapper.constructType(returnType));
            }
        } catch (JsonProcessingException e) {
            log.error("Error deserializing cached values from Redis for key {}: {}", cacheKey, e.getMessage());
            // Proceed to fetch from DB if deserialization fails
        } catch (Exception e) {
            log.error("Error accessing Redis for key {}: {}", cacheKey, e.getMessage());
            // Proceed to fetch from DB if Redis access fails
        }

        // 2. If cache miss or error, fetch from database by proceeding with the original method
        log.info("Cache miss for key: {}. Fetching from database.", cacheKey);
        Object result = joinPoint.proceed();

        // 3. Store the result in cache
        if (result != null) {
            try {
                String resultJson = objectMapper.writeValueAsString(result);
                redisClient.set(cacheKey, resultJson, cacheTTL); // Use set with TTL
                log.info("Stored result in cache for key: {}", cacheKey);
            } catch (JsonProcessingException e) {
                log.error("Error serializing result for caching for key {}: {}", cacheKey, e.getMessage());
            } catch (Exception e) {
                log.error("Error storing data in Redis for key {}: {}", cacheKey, e.getMessage());
            }
        }
        return result;
    }
}