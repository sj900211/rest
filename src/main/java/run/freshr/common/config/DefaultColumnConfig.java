package run.freshr.common.config;

/**
 * The Class Default Column config.
 *
 * @author [류성재]
 * @implNote URI 설정
 * @since 2021. 2. 24. 오후 5:45:17
 */
public abstract class DefaultColumnConfig {

  /**
   * The constant TRUE
   */
  public static final String TRUE = "true";
  /**
   * The constant FALSE
   */
  public static final String FALSE = "false";
  /**
   * The constant ZERO
   */
  public static final String ZERO = "0";

  public static final String INSERT_TIMESTAMP = "CURRENT_TIMESTAMP";

  public static final String UPDATE_TIMESTAMP = "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP";

}
