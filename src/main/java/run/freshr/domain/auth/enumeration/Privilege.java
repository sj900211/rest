package run.freshr.domain.auth.enumeration;

import static java.util.Arrays.stream;
import static run.freshr.domain.auth.enumeration.Role.ROLE_COACH;
import static run.freshr.domain.auth.enumeration.Role.ROLE_LEADER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_SUPER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_USER;

import lombok.extern.slf4j.Slf4j;
import run.freshr.mapper.EnumModel;

@Slf4j
public enum Privilege implements EnumModel {

  SUPER("수퍼 관리자", ROLE_SUPER),
  MANAGER("관리자", ROLE_MANAGER),
  LEADER("리더", ROLE_LEADER),
  COACH("관리자", ROLE_COACH),
  USER("사용자", ROLE_USER);

  private final String value;
  private final Role role;

  Privilege(String value, Role role) {
    this.value = value;
    this.role = role;
  }

  public static Privilege find(String key) {
    log.info("Privilege.find");

    return stream(Privilege.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(null);
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return this.value;
  }

  public Role getRole() {
    return this.role;
  }

}
