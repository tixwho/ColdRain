package com.coldrain.ncm;

import com.coldrain.ncm.annotations.NcmTask;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class NcmAspect {

    @Around("@annotation(ncmTask)")
    public Object metric(ProceedingJoinPoint joinPoint, NcmTask ncmTask) throws Throwable {
        String name = ncmTask.value();
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
