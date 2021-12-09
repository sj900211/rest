package run.freshr.common.util;

public class ThreadUtil {

  public static ThreadLocal<String> threadAccess = new ThreadLocal<>();
  public static ThreadLocal<String> threadRefresh = new ThreadLocal<>();
  public static ThreadLocal<String> threadPublicKey = new ThreadLocal<>();

}
