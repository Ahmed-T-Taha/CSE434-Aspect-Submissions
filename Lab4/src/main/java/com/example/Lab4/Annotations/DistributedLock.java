package com.example.Lab4.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    // The base key prefix for the lock in Redis
    String keyPrefix();

    // SpEL expression to extract the unique identifier part of the lock key
    String keyIdentifierExpression();

    // How long the lock is held before automatically expiring if the holder crashes
    long leaseTime() default 30; // Default 30 seconds

    // Time unit for the lease time
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}