package run.freshr.domain.auth.enumeration;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import run.freshr.mapper.EnumModel;

@Slf4j
public enum Role implements EnumModel {

  ROLE_ANONYMOUS("게스트", "anonymous"),
  ROLE_SUPER("수퍼 관리자", "super manager"),
  ROLE_MANAGER("관리자", "manager"),
  ROLE_LEADER("리더", "leader"),
  ROLE_COACH("코치", "coach"),
  ROLE_USER("사용자", "user");

  private final String value;
  private final String username;

  Role(String value, String username) {
    this.value = value;
    this.username = username;
  }

  public static Role find(String key) {
    log.info("Role.find");

    return Arrays.stream(Role.values())
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

  public static class Secured {

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String SUPER = "ROLE_SUPER";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String LEADER = "ROLE_LEADER";
    public static final String COACH = "ROLE_COACH";
    public static final String USER = "ROLE_USER";

  }

}
