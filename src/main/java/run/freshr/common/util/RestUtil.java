package run.freshr.common.util;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static java.text.MessageFormat.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static run.freshr.common.security.SecurityUtil.signedId;
import static run.freshr.common.security.SecurityUtil.signedRole;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
import run.freshr.common.config.CustomConfig;
import run.freshr.common.model.ResponseModel;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.unit.AccountUnitImpl;
import run.freshr.domain.common.dto.response.IdResponse;
import run.freshr.domain.common.dto.response.IdResponse.IdResponseBuilder;
import run.freshr.response.ExceptionsResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestUtil {

  private static final ObjectMapper objectMapper;

  private static Environment environment;
  private static CustomConfig customConfig;
  private static ExceptionsResponse exceptionsResponse;

  private static AccountUnitImpl accountUnit;

  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  static {
    objectMapper = new ObjectMapper();

    JavaTimeModule javaTimeModule = new JavaTimeModule();

    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(ofPattern(DATE_FORMAT)));
    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(ofPattern(DATE_FORMAT)));
    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ofPattern(DATE_TIME_FORMAT)));
    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ofPattern(DATE_TIME_FORMAT)));

    objectMapper.registerModule(javaTimeModule);
  }

  @Autowired
  public RestUtil(
      Environment environment,
      CustomConfig customConfig,
      ExceptionsResponse exceptionsResponse,
      AccountUnitImpl accountUnit) {
    RestUtil.environment = environment;
    RestUtil.customConfig = customConfig;
    RestUtil.exceptionsResponse = exceptionsResponse;
    RestUtil.accountUnit = accountUnit;
  }

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

  public static ResponseEntity<?> ok() {
    log.info("RestUtil.ok");

    return ok(exceptionsResponse.getSuccess());
  }

  public static ResponseEntity<?> ok(final ExceptionsResponse.Exceptions exceptions) {
    log.info("RestUtil.ok");

    return ok(exceptions.getMessage());
  }

  public static ResponseEntity<?> ok(final String message) {
    log.info("RestUtil.ok");

    return ok(ResponseModel
        .builder()
        .message(ofNullable(message).orElse(exceptionsResponse.getSuccess().getMessage()))
        .build());
  }

  public static <T> ResponseEntity<?> ok(final T data) {
    log.info("RestUtil.ok");

    return ok(ResponseModel
        .builder()
        .message(exceptionsResponse.getSuccess().getMessage())
        .data(data)
        .build());
  }

  public static <T> ResponseEntity<?> ok(final List<T> list) {
    log.info("RestUtil.ok");

    return ok(ResponseModel
        .builder()
        .message(exceptionsResponse.getSuccess().getMessage())
        .list(list)
        .build());
  }

  public static <T> ResponseEntity<?> ok(final Page<T> page) {
    log.info("RestUtil.ok");

    return ok(ResponseModel
        .builder()
        .message(exceptionsResponse.getSuccess().getMessage())
        .page(page)
        .build());
  }

  public static ResponseEntity<?> ok(final ResponseModel body) {
    log.info("RestUtil.ok");

    return ResponseEntity
        .ok()
        .body(objectMapper.valueToTree(body));
  }

  public static ResponseEntity<?> error() {
    log.info("RestUtil.error");

    return error(exceptionsResponse.getError());
  }

  public static ResponseEntity<?> error(final String message) {
    log.info("RestUtil.error");

    return error(message, null);
  }

  public static ResponseEntity<?> error(final HttpStatus httpStatus, final String message) {
    log.info("RestUtil.error");

    return error(
        httpStatus,
        null,
        null,
        ofNullable(message).orElse(exceptionsResponse.getError().getMessage())
    );
  }

  public static ResponseEntity<?> error(final String message, final Object[] args) {
    log.info("RestUtil.error");

    return error(
        exceptionsResponse.getError().getHttpStatus(),
        exceptionsResponse.getError().getHttpStatus().name(),
        null,
        format(ofNullable(message).orElse(exceptionsResponse.getError().getMessage()), args)
    );
  }

  public static ResponseEntity<?> error(final ExceptionsResponse.Exceptions exceptions) {
    log.info("RestUtil.error");

    return error(exceptions, null, null);
  }

  public static ResponseEntity<?> error(final ExceptionsResponse.Exceptions exceptions,
      final String message) {
    log.info("RestUtil.error");

    return error(exceptions, message, null);
  }

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

  public static ResponseEntity<?> error(final BindingResult bindingResult) {
    log.info("RestUtil.error");

    return ResponseEntity
        .badRequest()
        .body(bindingResult);
  }

  public static ResponseEntity<?> error(final Errors errors) {
    log.info("RestUtil.error");

    return ResponseEntity
        .badRequest()
        .body(errors);
  }

  public static void rejectWrong(final String name, BindingResult bindingResult) {
    log.info("RestUtil.rejectWrong");

    bindingResult.rejectValue(name, "wrong value");
  }

  public static void rejectWrong(final String name, final String description,
      BindingResult bindingResult) {
    log.info("RestUtil.rejectWrong");

    bindingResult.rejectValue(name, "wrong value", description);
  }

  public static void rejectRequired(BindingResult bindingResult, final String... names) {
    log.info("RestUtil.rejectRequired");

    stream(names).forEach(name -> rejectRequired(name, bindingResult));
  }

  public static void rejectRequired(final String name, BindingResult bindingResult) {
    log.info("RestUtil.rejectRequired");

    bindingResult.rejectValue(name, "required value");
  }

  public static void rejectAuth(BindingResult bindingResult) {
    log.info("RestUtil.rejectAuth");

    bindingResult.rejectValue("UnAuthenticated", "permission denied");
  }

  public static void rejectWrong(final String name, Errors errors) {
    log.info("RestUtil.rejectWrong");

    errors.rejectValue(name, "wrong value");
  }

  public static void rejectWrong(final String name, final String description,
      Errors errors) {
    log.info("RestUtil.rejectWrong");

    errors.rejectValue(name, "wrong value", description);
  }

  public static void rejectRequired(Errors errors, final String... names) {
    log.info("RestUtil.rejectRequired");

    stream(names).forEach(name -> rejectRequired(name, errors));
  }

  public static void rejectRequired(final String name, Errors errors) {
    log.info("RestUtil.rejectRequired");

    errors.rejectValue(name, "required value");
  }

  public static void rejectAuth(Errors errors) {
    log.info("RestUtil.rejectAuth");

    errors.rejectValue("UnAuthenticated", "permission denied");
  }

  public static ExceptionsResponse getExceptions() {
    log.info("RestUtil.getExceptions");

    return RestUtil.exceptionsResponse;
  }

  public static boolean checkProfile(final String profile) {
    log.info("RestUtil.checkProfile");

    return stream(environment.getActiveProfiles())
        .anyMatch(active -> active.equalsIgnoreCase(profile));
  }

  public static CustomConfig getConfig() {
    log.info("RestUtil.getConfig");

    return customConfig;
  }

  public static <ID> IdResponse<ID> buildId(ID id) {
    IdResponseBuilder<ID> builder = IdResponse.builder();

    return builder.id(id).build();
  }

  public static Long getSignedId() {
    log.info("RestUtil.getSignedId");

    return signedId.get();
  }

  public static Role getSignedRole() {
    log.info("RestUtil.getSignedRole");

    return signedRole.get();
  }

  public static Account getSignedAccount() {
    log.info("RestUtil.getSignedAccount");

    return accountUnit.get(getSignedId());
  }

  public static boolean checkRole(Role role) {
    log.info("RestUtil.checkRole");

    return getSignedRole().equals(role);
  }

}
