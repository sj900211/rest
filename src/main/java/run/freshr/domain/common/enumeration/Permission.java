package run.freshr.domain.common.enumeration;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import run.freshr.mapper.EnumModel;

@Slf4j
public enum Permission implements EnumModel {

  PASS("허용"),
  DENIED("거부");

  private final String value;

  Permission(String value) {
    this.value = value;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

  public static Permission find(String key) {
    log.info("Permission.find");

    return Arrays.stream(Permission.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(null);
  }

}
