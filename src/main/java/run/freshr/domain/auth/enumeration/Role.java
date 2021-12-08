package run.freshr.domain.auth.enumeration;

import static java.util.Arrays.stream;

import lombok.extern.slf4j.Slf4j;
import run.freshr.mapper.EnumModel;

@Slf4j
public enum Role implements EnumModel {

  ROLE_SUPER("수퍼 관리자", "super manager", 0B100000),
  ROLE_MANAGER("관리자", "manager", 0B010000),
  ROLE_LEADER("리더", "leader", 0B001000),
  ROLE_COACH("코치", "coach", 0B000100),
  ROLE_USER("사용자", "user", 0B000010),
  ROLE_ANONYMOUS("게스트", "anonymous", 0B000001);

  private final String value;
  private final String username;
  private final Integer permission;

  Role(String value, String username, Integer permission) {
    this.value = value;
    this.username = username;
    this.permission = permission;
  }

  public static Role find(String key) {
    log.info("Role.find");

    return stream(Role.values())
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
    return value;
  }

  public String getUsername() {
    return username;
  }

  public Integer getPermission() {
    return permission;
  }

  public static class Secured {

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String SUPER = "ROLE_SUPER";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String LEADER = "ROLE_LEADER";
    public static final String COACH = "ROLE_COACH";
    public static final String USER = "ROLE_USER";

  }

}
