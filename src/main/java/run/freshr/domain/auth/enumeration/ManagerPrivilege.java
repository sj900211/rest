package run.freshr.domain.auth.enumeration;

import java.util.Arrays;
import run.freshr.mapper.EnumModel;

/**
 * 관리자 권한 데이터
 */
public enum ManagerPrivilege implements EnumModel {

  SUPER("수퍼 관리자", SignPrivilege.SUPER),
  MANAGER("관리자", SignPrivilege.MANAGER);

  /**
   * 한글 설명
   */
  private final String value;
  /**
   * 계정 권한
   */
  private final SignPrivilege privilege;

  /**
   * Instantiates a new Manager privilege.
   *
   * @param value     the value
   * @param privilege the privilege
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:51:02
   */
  ManagerPrivilege(String value, SignPrivilege privilege) {
    this.value = value;
    this.privilege = privilege;
  }

  /**
   * Find manager privilege.
   *
   * @param key the key
   * @return the manager privilege
   * @author [류성재]
   * @implNote name 값이 같은 데이터 조회
   * @since 2021. 3. 16. 오후 2:51:02
   */
  public static ManagerPrivilege find(String key) {
    return Arrays.stream(ManagerPrivilege.values())
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
   * @since 2021. 3. 16. 오후 2:51:02
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
   * @since 2021. 3. 16. 오후 2:51:03
   */
  @Override
  public String getValue() {
    return this.value;
  }

  /**
   * Gets privilege.
   *
   * @return the privilege
   * @author [류성재]
   * @implNote Privilege Getter
   * @since 2021. 3. 16. 오후 6:39:56
   */
  public SignPrivilege getPrivilege() {
    return privilege;
  }
}
