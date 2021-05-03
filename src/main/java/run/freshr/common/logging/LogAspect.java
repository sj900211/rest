package run.freshr.common.logging;

import static java.util.Optional.ofNullable;

import run.freshr.common.security.SecurityUtil;
import run.freshr.domain.auth.enumeration.Role;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * The Class Log aspect.
 *
 * @author [류성재]
 * @implNote 전역에서 로그를 출력하는 Class
 * @since 2021. 2. 24. 오후 5:55:56
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

  /**
   * Controller logging object.
   *
   * @param proceedingJoinPoint the proceeding join point
   * @return the object
   * @throws Throwable the throwable
   * @author [류성재]
   * @implNote 모든 Controller 를 대상으로 Log 를 출력
   * @since 2021. 2. 24. 오후 5:56:04
   */
  @Around("execution(* run.freshr.controller..*.*(..))")
  public Object controllerLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    log.info("**** LOG START");
    log.info(
        "**** "
            + proceedingJoinPoint.getSignature().getDeclaringTypeName()
            + "."
            + proceedingJoinPoint.getSignature().getName()
    );
    log.info("**** Role: " + ofNullable(SecurityUtil.signedRole.get()).orElse(Role.ROLE_ANONYMOUS).getKey());
    log.info("**** Id: " + ofNullable(SecurityUtil.signedId.get()).orElse(0L));

    Object result = proceedingJoinPoint.proceed();

    log.info("**** LOG FINISH");

    return result;
  }

}
