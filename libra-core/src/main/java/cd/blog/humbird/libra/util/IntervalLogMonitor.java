package cd.blog.humbird.libra.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by david on 2018/7/20.
 */
@Aspect
@Component
public class IntervalLogMonitor {

    private static final Logger logger = LoggerFactory.getLogger(IntervalLogMonitor.class);

    @Around(value = "@annotation(cd.blog.humbird.libra.util.LogMonitor)")
    public void doAround(ProceedingJoinPoint pjp) throws Throwable {
        final long begin = System.currentTimeMillis();
        try {
            pjp.proceed();
        } finally {
            logMethodInfo(pjp, begin);
        }
    }

    private void logMethodInfo(ProceedingJoinPoint point, long begin) {
        final long timeUsed = System.currentTimeMillis() - begin;
        logger.warn("{}|method:{}|cost:{}ms", timeUsed > 300 ? "TimeOut" : "",
                point.toLongString(), timeUsed);
    }

}
