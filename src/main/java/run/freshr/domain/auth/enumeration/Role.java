package run.freshr.domain.auth.enumeration;

import java.util.Arrays;
import run.freshr.mapper.EnumModel;

/**
 * 전체 권한 데이터
 */
public enum Role implements EnumModel {

  ROLE_ANONYMOUS("게스트", "anonymous"),
  ROLE_SUPER("수퍼 관리자", "super manager"),
  ROLE_MANAGER("관리자", "manager"),
  ROLE_USER("사용자", "user");

  /**
   * 한글 설명
   */
  private final String value;
  /**
   * 테스트 코드에서 사용되는 값
   */
  private final String username;

  /**
   * Instantiates a new Role.
   *
   * @param value    the value
   * @param username the username
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:17:42
   */
  Role(String value, String username) {
    this.value = value;
    this.username = username;
  }

  /**
   * Find role.
   *
   * @param key the key
   * @return the role
   * @author [류성재]
   * @implNote name 값이 같은 데이터 조회
   * @since 2021. 3. 16. 오후 2:17:42
   */
  public static Role find(String key) {
    return Arrays.stream(Role.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(null);
  }

  /**
   * Gets key.
   *
   * @return the key
   * @author [류성재]
   * @implNote Key Getter
   * @since 2021. 3. 16. 오후 2:17:42
   */
  @Override
  public String getKey() {
    return name();
  }

  /**
   * Gets value.
   *
   * @return the value
   * @author [류성재]
   * @implNote Value Getter
   * @since 2021. 3. 16. 오후 2:17:42
   */
  @Override
  public String getValue() {
    return value;
  }

  /**
   * Gets username.
   *
   * @return the username
   * @author [류성재]
   * @implNote Username Getter
   * @since 2021. 3. 16. 오후 2:17:42
   */
  public String getUsername() {
    return username;
  }

  /**
   * The Class Secured.
   *
   * @author [류성재]
   * @implNote Controller 에서 사용하기 위한 Static 클래스
   * @since 2021. 3. 16. 오후 2:17:42
   */
  public static class Secured {

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String SUPER = "ROLE_SUPER";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String USER = "ROLE_USER";

  }

}
