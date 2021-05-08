package com.coldrain.database;

import com.coldrain.database.annotations.CrTask;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class CrDatabaseAspect {

    @Around("@annotation(crTask)")
    public Object metric(ProceedingJoinPoint joinPoint, CrTask crTask) throws Throwable {
        String name = crTask.value();
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long t = System.currentTimeMillis() - start;
            // 写入日志或发送至JMX:
            System.err.println("[Metrics] " + name + ": " + t + "ms");
        }
    }


}
