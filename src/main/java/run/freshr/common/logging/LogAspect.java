package run.freshr.common.logging;

import static java.util.Optional.ofNullable;
import static run.freshr.common.security.SecurityUtil.signedId;
import static run.freshr.common.security.SecurityUtil.signedRole;
import static run.freshr.domain.auth.enumeration.Role.ROLE_ANONYMOUS;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

  @Around("execution(* run.freshr.controller..*.*(..))")
  public Object controllerLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    log.info("**** LOG START");
    log.info(
        "**** " + proceedingJoinPoint.getSignature().getDeclaringTypeName()
            + "." + proceedingJoinPoint.getSignature().getName()
    );
    log.info("**** Role: " + ofNullable(signedRole.get()).orElse(ROLE_ANONYMOUS).getKey());
    log.info("**** Id: " + ofNullable(signedId.get()).orElse(0L));

    Object result = proceedingJoinPoint.proceed();

    log.info("**** LOG FINISH");

    return result;
  }

}
