package run.freshr.common.util;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static java.text.MessageFormat.format;
import static java.util.Optional.ofNullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import run.freshr.common.config.CustomConfig;
import run.freshr.common.security.SecurityUtil;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.service.AccountUnit;
import run.freshr.domain.auth.service.ManagerUnit;
import run.freshr.common.model.ResponseModel;
import run.freshr.response.ExceptionsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Class Rest util.
 *
 * @author [류성재]
 * @implNote Rest 유틸
 * @since 2021. 2. 25. 오후 3:56:25 TODO: 모듈로 제작 예정
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestUtil {

  /**
   * The Object mapper
   */
  private static final ObjectMapper objectMapper;

  /**
   * The Environment bean
   */
  private static Environment environment;
  /**
   * The constant customConfig
   */
  private static CustomConfig customConfig;
  /**
   * The constant exceptionsResponse
   */
  private static ExceptionsResponse exceptionsResponse;

  /**
   * The constant accountService
   */
  private static AccountUnit accountUnit;
  /**
   * The constant managerService
   */
  private static ManagerUnit managerUnit;

  static {
    objectMapper = new ObjectMapper();
  }

  /**
   * Instantiates a new Rest util.
   *
   * @param environment        the environment
   * @param customConfig       the custom config
   * @param exceptionsResponse the exceptions response
   * @param managerUnit     the manager service
   * @param accountUnit     the account service
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:29
   */
  @Autowired
  public RestUtil(
      Environment environment,
      CustomConfig customConfig,
      ExceptionsResponse exceptionsResponse,
      ManagerUnit managerUnit,
      AccountUnit accountUnit) {
    RestUtil.environment = environment;
    RestUtil.customConfig = customConfig;
    RestUtil.exceptionsResponse = exceptionsResponse;
    RestUtil.managerUnit = managerUnit;
    RestUtil.accountUnit = accountUnit;
  }

  /**
   * View model and view.
   *
   * @param modelAndView the model and view
   * @param prefix       the prefix
   * @param paths        the paths
   * @return the model and view
   * @author [류성재]
   * @implNote View 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ModelAndView view(final ModelAndView modelAndView, final String prefix,
      final String... paths) {
    log.info("RestUtil.view");

    StringBuilder uri = new StringBuilder(prefix);

    for (String path : paths) {
      uri.append("/").append(path);
    }

    modelAndView.setViewName(uri.toString());

    return modelAndView;
  }

  /**
   * Ok response entity.
   *
   * @return the response entity
   * @author [류성재]
   * @implNote 성공 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> ok() {
    log.info("RestUtil.ok");

    return ok(exceptionsResponse.getSuccess());
  }

  /**
   * Ok response entity.
   *
   * @param exceptions the exceptions
   * @return the response entity
   * @author [류성재]
   * @implNote 성공 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> ok(final ExceptionsResponse.Exceptions exceptions) {
    log.info("RestUtil.ok");

    return ok(exceptions.getMessage());
  }

  /**
   * Ok response entity.
   *
   * @param message the message
   * @return the response entity
   * @author [류성재]
   * @implNote 성공 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> ok(final String message) {
    log.info("RestUtil.ok");

    return ok(ResponseModel
        .builder()
        .message(ofNullable(message).orElse(exceptionsResponse.getSuccess().getMessage()))
        .build());
  }

  /**
   * Ok response entity.
   *
   * @param data the data
   * @return the response entity
   * @author [류성재]
   * @implNote 성공 반환
   * @since 2021. 2. 25. 오후 5:04:28
   */
  public static <T> ResponseEntity<?> ok(final T data) {
    log.info("RestUtil.ok");

    return ok(ResponseModel
        .builder()
        .message(exceptionsResponse.getSuccess().getMessage())
        .data(data)
        .build());
  }

  /**
   * Ok response entity.
   *
   * @param list the list
   * @return the response entity
   * @author [류성재]
   * @implNote 성공 반환
   * @since 2021. 2. 25. 오후 5:04:28
   */
  public static <T> ResponseEntity<?> ok(final List<T> list) {
    log.info("RestUtil.ok");

    return ok(ResponseModel
        .builder()
        .message(exceptionsResponse.getSuccess().getMessage())
        .list(list)
        .build());
  }

  /**
   * Ok response entity.
   *
   * @param page the page
   * @return the response entity
   * @author [류성재]
   * @implNote 성공 반환
   * @since 2021. 2. 25. 오후 5:04:28
   */
  public static <T> ResponseEntity<?> ok(final Page<T> page) {
    log.info("RestUtil.ok");

    return ok(ResponseModel
        .builder()
        .message(exceptionsResponse.getSuccess().getMessage())
        .page(page)
        .build());
  }

  /**
   * Ok response entity.
   *
   * @param body the body
   * @return the response entity
   * @author [류성재]
   * @implNote 성공 반환
   * @since 2021. 2. 25. 오후 5:04:28
   */
  public static ResponseEntity<?> ok(final ResponseModel body) {
    log.info("RestUtil.ok");

    return ResponseEntity
        .ok()
        .body(objectMapper.valueToTree(body));
  }

  /**
   * Error response entity.
   *
   * @return the response entity
   * @author [류성재]
   * @implNote 에러 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> error() {
    log.info("RestUtil.error");

    return error(exceptionsResponse.getError());
  }

  /**
   * Error response entity.
   *
   * @param message the message
   * @return the response entity
   * @author [류성재]
   * @implNote 에러 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> error(final String message) {
    log.info("RestUtil.error");

    return error(message, null);
  }

  /**
   * Error response entity.
   *
   * @param httpStatus the http status
   * @param message    the message
   * @return the response entity
   * @author [류성재]
   * @implNote 에러 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> error(final HttpStatus httpStatus, final String message) {
    log.info("RestUtil.error");

    return error(
        httpStatus,
        null,
        null,
        ofNullable(message).orElse(exceptionsResponse.getError().getMessage())
    );
  }

  /**
   * Error response entity.
   *
   * @param message the message
   * @param args    the args
   * @return the response entity
   * @author [류성재]
   * @implNote 에러 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> error(final String message, final Object[] args) {
    log.info("RestUtil.error");

    return error(
        exceptionsResponse.getError().getHttpStatus(),
        exceptionsResponse.getError().getHttpStatus().name(),
        null,
        format(ofNullable(message).orElse(exceptionsResponse.getError().getMessage()), args)
    );
  }

  /**
   * Error response entity.
   *
   * @param exceptions the exceptions
   * @return the response entity
   * @author [류성재]
   * @implNote 에러 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> error(final ExceptionsResponse.Exceptions exceptions) {
    log.info("RestUtil.error");

    return error(exceptions, null, null);
  }

  /**
   * Error response entity.
   *
   * @param exceptions the exceptions
   * @param message    the message
   * @return the response entity
   * @author [류성재]
   * @implNote 에러 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> error(final ExceptionsResponse.Exceptions exceptions,
      final String message) {
    log.info("RestUtil.error");

    return error(exceptions, message, null);
  }

  /**
   * Error response entity.
   *
   * @param exceptions the exceptions
   * @param message    the message
   * @param args       the args
   * @return the response entity
   * @author [류성재]
   * @implNote 에러 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> error(final ExceptionsResponse.Exceptions exceptions,
      final String message, final Object[] args) {
    log.info("RestUtil.error");

    return error(
        exceptions.getHttpStatus(),
        UPPER_UNDERSCORE.to(LOWER_HYPHEN, exceptions.getHttpStatus().name()),
        exceptions.getCode(),
        format(ofNullable(message).orElse(exceptions.getMessage()), args)
    );
  }

  /**
   * Error response entity.
   *
   * @param httpStatus the http status
   * @param name       the name
   * @param code       the code
   * @param message    the message
   * @return the response entity
   * @author [류성재]
   * @implNote 에러 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ResponseEntity<?> error(final HttpStatus httpStatus, final String name,
      final String code, final String message) {
    log.info("RestUtil.error");

    return ResponseEntity
        .status(httpStatus)
        .body(objectMapper.valueToTree(
            ResponseModel
                .builder()
                .name(name)
                .code(code)
                .message(message)
                .build()
        ));
  }

  /**
   * Error response entity.
   *
   * @param bindingResult the binding result
   * @return the response entity
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static ResponseEntity<?> error(final BindingResult bindingResult) {
    log.info("RestUtil.error");

    return ResponseEntity
        .badRequest()
        .body(bindingResult);
  }

  /**
   * Error response entity.
   *
   * @param errors the errors
   * @return the response entity
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static ResponseEntity<?> error(final Errors errors) {
    log.info("RestUtil.error");

    return ResponseEntity
        .badRequest()
        .body(errors);
  }

  /**
   * Reject wrong.
   *
   * @param name          the name
   * @param bindingResult the binding result
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectWrong(final String name, BindingResult bindingResult) {
    bindingResult.rejectValue(name, "wrong value");
  }

  /**
   * Reject wrong.
   *
   * @param name          the name
   * @param description   the description
   * @param bindingResult the binding result
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectWrong(final String name, final String description,
      BindingResult bindingResult) {
    bindingResult.rejectValue(name, "wrong value", description);
  }

  /**
   * Reject required.
   *
   * @param bindingResult the binding result
   * @param names         the names
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectRequired(BindingResult bindingResult, final String... names) {
    Arrays.stream(names).forEach(name -> rejectRequired(name, bindingResult));
  }

  /**
   * Reject required.
   *
   * @param name          the name
   * @param bindingResult the binding result
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectRequired(final String name, BindingResult bindingResult) {
    bindingResult.rejectValue(name, "required value");
  }

  /**
   * Reject auth.
   *
   * @param bindingResult the binding result
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectAuth(BindingResult bindingResult) {
    bindingResult.rejectValue("UnAuthenticated", "permission denied");
  }

  /**
   * Reject wrong.
   *
   * @param name          the name
   * @param errors the errors
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectWrong(final String name, Errors errors) {
    errors.rejectValue(name, "wrong value");
  }

  /**
   * Reject wrong.
   *
   * @param name          the name
   * @param description   the description
   * @param errors the errors
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectWrong(final String name, final String description,
      Errors errors) {
    errors.rejectValue(name, "wrong value", description);
  }

  /**
   * Reject required.
   *
   * @param errors the errors
   * @param names         the names
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectRequired(Errors errors, final String... names) {
    Arrays.stream(names).forEach(name -> rejectRequired(name, errors));
  }

  /**
   * Reject required.
   *
   * @param name          the name
   * @param errors the errors
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectRequired(final String name, Errors errors) {
    errors.rejectValue(name, "required value");
  }

  /**
   * Reject auth.
   *
   * @param errors the errors
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static void rejectAuth(Errors errors) {
    errors.rejectValue("UnAuthenticated", "permission denied");
  }

  /**
   * Gets exceptions.
   *
   * @return the exceptions
   * @author [류성재]
   * @implNote Exception 객체 반환
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static ExceptionsResponse getExceptions() {
    return RestUtil.exceptionsResponse;
  }

  /**
   * Check profile boolean.
   *
   * @param profile the profile
   * @return the boolean
   * @author [류성재]
   * @implNote 실행된 서비스의 Profile 체크
   * @since 2021. 2. 25. 오후 3:56:25
   */
  public static boolean checkProfile(final String profile) {
    log.info("RestUtil.checkProfile");

    return Arrays
        .stream(environment.getActiveProfiles())
        .anyMatch(active -> active.equalsIgnoreCase(profile));
  }

  /**
   * Gets config.
   *
   * @return the config
   * @author [류성재]
   * @implNote Custom 설정 조회
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static CustomConfig getConfig() {
    return customConfig;
  }

  /**
   * Gets signed id.
   *
   * @return the signed id
   * @author [류성재]
   * @implNote 로그인한 계정의 일련 번호 조회
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static Long getSignedId() {
    return SecurityUtil.signedId.get();
  }

  /**
   * Gets signed role.
   *
   * @return the signed role
   * @author [류성재]
   * @implNote 로그인한 계정의 권한 조회
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static Role getSignedRole() {
    return SecurityUtil.signedRole.get();
  }

  /**
   * Gets signed account.
   *
   * @return the signed account
   * @author [류성재]
   * @implNote 로그인한 계정 정보 조회
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static Account getSignedAccount() {
    return accountUnit.get(getSignedId());
  }

  /**
   * Gets signed manager.
   *
   * @return the signed manager
   * @author [류성재]
   * @implNote 로그인한 계정 정보 조회
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static Manager getSignedManager() {
    return managerUnit.get(getSignedId());
  }

  /**
   * Check user boolean.
   *
   * @return the boolean
   * @author [류성재]
   * @implNote ROLE_USER 권한인지 체크
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static boolean checkUser() {
    return getSignedRole().equals(Role.ROLE_USER);
  }

  /**
   * Check manager boolean.
   *
   * @return the boolean
   * @author [류성재]
   * @implNote ROLE_MANAGER 권한인지 체크
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static boolean checkManager() {
    return getSignedRole().equals(Role.ROLE_MANAGER);
  }

  /**
   * Check super boolean.
   *
   * @return the boolean
   * @author [류성재]
   * @implNote ROLE_SUPER 권한인지 체크
   * @since 2021. 3. 16. 오후 12:08:30
   */
  public static boolean checkSuper() {
    return getSignedRole().equals(Role.ROLE_SUPER);
  }

}
