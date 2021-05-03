package run.freshr.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class Sign util.
 *
 * @author [류성재]
 * @implNote Test 로그인 유틸
 * @since 2021. 2. 25. 오후 5:42:51
 */
@Slf4j
public class SignUtil {

  /**
   * The Signed access
   */
  public static ThreadLocal<String> signedAccess = new ThreadLocal<>();
  /**
   * The Signed refresh
   */
  public static ThreadLocal<String> signedRefresh = new ThreadLocal<>();

}
