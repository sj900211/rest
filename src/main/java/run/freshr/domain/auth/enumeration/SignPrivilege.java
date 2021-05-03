package run.freshr.domain.auth.enumeration;

import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_SUPER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_USER;

import java.util.Arrays;
import run.freshr.mapper.EnumModel;

/**
 * 계정 권한 데이터
 */
public enum SignPrivilege implements EnumModel {

  SUPER("수퍼 관리자", ROLE_SUPER),
  MANAGER("관리자", ROLE_MANAGER),
  USER("사용자", ROLE_USER);

  /**
   * 한글 설명
   */
  private final String value;
  /**
   * 권한
   */
  private final Role role;

  /**
   * Instantiates a new Sign privilege.
   *
   * @param value the value
   * @param role  the role
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:20:06
   */
  SignPrivilege(String value, Role role) {
    this.value = value;
    this.role = role;
  }

  /**
   * Find sign privilege.
   *
   * @param key the key
   * @return the sign privilege
   * @author [류성재]
   * @implNote name 값이 같은 데이터 조회
   * @since 2021. 3. 16. 오후 2:20:06
   */
  public static SignPrivilege find(String key) {
    return Arrays.stream(SignPrivilege.values())
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
   * @since 2021. 3. 16. 오후 2:20:06
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
   * @since 2021. 3. 16. 오후 2:20:06
   */
  @Override
  public String getValue() {
    return this.value;
  }

  /**
   * Gets role.
   *
   * @return the role
   * @author [류성재]
   * @implNote Role Getter
   * @since 2021. 3. 16. 오후 2:20:06
   */
  public Role getRole() {
    return this.role;
  }

}
