package run.freshr.common.advice;

import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.getExceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.SQLSyntaxErrorException;
import javax.persistence.EntityNotFoundException;
import run.freshr.exception.DuplicateException;
import run.freshr.exception.ParameterException;
import run.freshr.exception.UnAuthenticatedException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The Class Advice controller.
 *
 * @author [류성재]
 * @implNote Advice Controller
 * @since 2021. 2. 25. 오후 4:41:26
 */
@RestControllerAdvice
public class AdviceController {

  //   ______  __    __       _______.___________.  ______   .___  ___.
  //  /      ||  |  |  |     /       |           | /  __  \  |   \/   |
  // |  ,----'|  |  |  |    |   (----`---|  |----`|  |  |  | |  \  /  |
  // |  |     |  |  |  |     \   \       |  |     |  |  |  | |  |\/|  |
  // |  `----.|  `--'  | .----)   |      |  |     |  `--'  | |  |  |  |
  //  \______| \______/  |_______/       |__|      \______/  |__|  |__|

  /**
   * Parameter exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote 파라미터 오류 - ParameterException
   * @since 2021. 2. 25. 오후 4:41:26
   */
  @ExceptionHandler(value = {ParameterException.class})
  protected ResponseEntity<?> parameterException(ParameterException e) {
    return error(getExceptions().getParameter(), e.getMessage());
  }

  /**
   * Duplicate exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote 중복 데이터 오류 - DuplicateException
   * @since 2021. 2. 25. 오후 4:41:26
   */
  @ExceptionHandler(value = {DuplicateException.class})
  protected ResponseEntity<?> duplicateException(DuplicateException e) {
    return error(getExceptions().getDuplicate(), e.getMessage());
  }

  /**
   * Un authenticated exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote 인증 오류 - UnAuthenticatedException
   * @since 2021. 2. 25. 오후 4:41:26
   */
  @ExceptionHandler(value = {UnAuthenticatedException.class})
  protected ResponseEntity<?> unAuthenticatedException(UnAuthenticatedException e) {
    return error(getExceptions().getUnAuthenticated(), e.getMessage());
  }

  //  _______   _______  _______    ___      __    __   __      .___________.
  // |       \ |   ____||   ____|  /   \    |  |  |  | |  |     |           |
  // |  .--.  ||  |__   |  |__    /  ^  \   |  |  |  | |  |     `---|  |----`
  // |  |  |  ||   __|  |   __|  /  /_\  \  |  |  |  | |  |         |  |
  // |  '--'  ||  |____ |  |    /  _____  \ |  `--'  | |  `----.    |  |
  // |_______/ |_______||__|   /__/     \__\ \______/  |_______|    |__|

  /**
   * Null pointer exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote NullPointerException
   * @since 2021. 2. 25. 오후 4:41:26
   */
  @ExceptionHandler(value = {NullPointerException.class})
  protected ResponseEntity<?> nullPointerException(NullPointerException e) {
    return error(getExceptions().getNullPointer(), e.getMessage());
  }

  /**
   * Entity not found exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote EntityNotFoundException
   * @since 2021. 2. 25. 오후 4:41:26
   */
  @ExceptionHandler(value = {EntityNotFoundException.class})
  protected ResponseEntity<?> entityNotFoundException(EntityNotFoundException e) {
    return error(getExceptions().getEntityNotFound(), e.getMessage());
  }

  /**
   * Io exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote IOException
   * @since 2021. 2. 25. 오후 4:41:26
   */
  @ExceptionHandler(value = {IOException.class})
  protected ResponseEntity<?> ioException(IOException e) {
    return error(getExceptions().getIo(), e.getMessage());
  }

  /**
   * File size limit exceeded exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote FileSizeLimitExceededException
   * @since 2021. 2. 25. 오후 4:41:26
   */
  @ExceptionHandler(value = {FileSizeLimitExceededException.class})
  protected ResponseEntity<?> fileSizeLimitExceededException(
      FileSizeLimitExceededException e) {
    return error(getExceptions().getFileSizeLimitExceeded(), e.getMessage());
  }

  /**
   * Json processing exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote JsonProcessingException
   * @since 2021. 2. 25. 오후 4:41:26
   */
  @ExceptionHandler(value = {JsonProcessingException.class})
  protected ResponseEntity<?> jsonProcessingException(JsonProcessingException e) {
    return error(getExceptions().getJsonProcessing(), e.getMessage());
  }

  /**
   * Sql syntax error exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote SQLSyntaxErrorException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {SQLSyntaxErrorException.class})
  protected ResponseEntity<?> sqlSyntaxErrorException(SQLSyntaxErrorException e) {
    return error(getExceptions().getSqlSyntaxError(), e.getMessage());
  }

  /**
   * Invalid data access resource usage exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote InvalidDataAccessResourceUsageException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {InvalidDataAccessResourceUsageException.class})
  protected ResponseEntity<?> invalidDataAccessResourceUsageException(
      InvalidDataAccessResourceUsageException e) {
    if (e.getCause() instanceof SQLGrammarException) {
      if (e.getCause().getCause() instanceof SQLSyntaxErrorException) {
        return error(getExceptions().getSqlGrammar(), e.getCause().getCause().getMessage());
      } else {
        return error(getExceptions().getSqlSyntaxError(), e.getCause().getMessage());
      }
    }

    return error(getExceptions().getInvalidDataAccessResourceUsage(), e.getMessage());
  }

  /**
   * Data integrity violation exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote DataIntegrityViolationException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {DataIntegrityViolationException.class})
  protected ResponseEntity<?> dataIntegrityViolationException(
      DataIntegrityViolationException e) {
    if (e.getCause() instanceof ConstraintViolationException) {
      if (e.getCause().getCause() instanceof BatchUpdateException) {
        return error(getExceptions().getBatchUpdate(), e.getCause().getCause().getMessage());
      } else {
        return error(getExceptions().getConstraintViolation(), e.getCause().getMessage());
      }
    } else if (e.getCause() instanceof DataException) {
      if (e.getCause().getCause() instanceof BatchUpdateException) {
        return error(getExceptions().getBatchUpdate(), e.getCause().getCause().getMessage());
      } else {
        return error(getExceptions().getData(), e.getCause().getMessage());
      }
    }

    return error(getExceptions().getDataIntegrityViolation(), e.getMessage());
  }

  /**
   * Batch update exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote BatchUpdateException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {BatchUpdateException.class})
  protected ResponseEntity<?> batchUpdateException(BatchUpdateException e) {
    return error(getExceptions().getBatchUpdate(), e.getMessage());
  }

  /**
   * Sql grammar exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote SQLGrammarException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {SQLGrammarException.class})
  protected ResponseEntity<?> sqlGrammarException(SQLGrammarException e) {
    return error(getExceptions().getSqlGrammar(), e.getMessage());
  }

  /**
   * Constraint violation exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote ConstraintViolationException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
    return error(getExceptions().getConstraintViolation(), e.getMessage());
  }

  /**
   * Data exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote DataException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {DataException.class})
  protected ResponseEntity<?> dataException(DataException e) {
    return error(getExceptions().getData(), e.getMessage());
  }

  /**
   * Access denied exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote AccessDeniedException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {AccessDeniedException.class})
  protected ResponseEntity<?> accessDeniedException(AccessDeniedException e) {
    return error(getExceptions().getAccessDenied(), e.getMessage());
  }

  /**
   * Illegal state exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote IllegalStateException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {IllegalStateException.class})
  protected ResponseEntity<?> illegalStateException(IllegalStateException e) {
    return error(getExceptions().getIllegalState(), e.getMessage());
  }

  /**
   * Illegal argument exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote IllegalStateException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {IllegalArgumentException.class})
  protected ResponseEntity<?> illegalArgumentException(IllegalArgumentException e) {
    return error(getExceptions().getIllegalArgument(), e.getMessage());
  }

  /**
   * Expired jwt exception response entity.
   *
   * @param e the e
   * @return the response entity
   * @author [류성재]
   * @implNote ExpiredJwtException
   * @since 2021. 2. 25. 오후 4:41:27
   */
  @ExceptionHandler(value = {ExpiredJwtException.class})
  protected ResponseEntity<?> expiredJwtException(ExpiredJwtException e) {
    return error(getExceptions().getExpiredJwt(), e.getMessage());
  }

}
