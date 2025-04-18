package com.example.Lab4.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD) // Apply to methods
@Retention(RetentionPolicy.RUNTIME) // Keep annotation info at runtime
public @interface RateLimit {
    String keyPrefix() default "";

    // The maximum number of requests allowed within the specified time window
    long limit();

    // The duration of the time window
    long duration();

    // The time unit for the duration
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}