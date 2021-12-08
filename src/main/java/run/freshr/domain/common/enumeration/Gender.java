package run.freshr.domain.common.enumeration;

import static java.util.Arrays.stream;

import lombok.extern.slf4j.Slf4j;
import run.freshr.mapper.EnumModel;

@Slf4j
public enum Gender implements EnumModel {

  MALE("남자"),
  FEMALE("여자");

  private final String value;

  Gender(String value) {
    this.value = value;
  }

  public static Gender find(String key) {
    log.info("Gender.find");

    return stream(Gender.values())
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

}
