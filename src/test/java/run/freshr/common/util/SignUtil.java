package run.freshr.common.util;

public class SignUtil {

  public static ThreadLocal<String> signedAccess = new ThreadLocal<>();
  public static ThreadLocal<String> signedRefresh = new ThreadLocal<>();

}
