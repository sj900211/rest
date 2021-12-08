package run.freshr.domain.blog.enumeration;

import static java.util.Arrays.stream;
import static java.util.List.of;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import run.freshr.mapper.EnumModel;

@Slf4j
public enum PostPermission implements EnumModel {

  B111111("111111", 0B111111,
      of(true, true, true, true, true, true),
      of(true, true, true, true, true)),
  B111110("111110", 0B111110,
      of(true, true, true, true, true, false),
      of(true, true, true, true, false)),
  B111100("111100", 0B111100,
      of(true, true, true, true, false, false),
      of(true, true, true, false, false)),
  B111000("111000", 0B111000,
      of(true, true, true, false, false, false),
      of(true, true, false, false, false)),
  B110000("110000", 0B110000,
      of(true, true, false, false, false, false),
      of(true, false, false, false, false)),
  B100000("100000", 0B100000,
      of(true, false, false, false, false, false),
      of(false, false, false, false, false));

  private final String value;
  private final Integer permission;
  private final List<Boolean> flagList;
  private final List<Boolean> postFlagList;

  PostPermission(String value, Integer permission,
      List<Boolean> flagList, List<Boolean> postFlagList) {
    this.value = value;
    this.permission = permission;
    this.flagList = flagList;
    this.postFlagList = postFlagList;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

  public Integer getPermission() {
    return permission;
  }

  public List<Boolean> getFlagList() {
    return flagList;
  }

  public List<Boolean> getPostFlagList() {
    return postFlagList;
  }

  public static PostPermission find(String key) {
    log.info("PostPermission.find");

    return stream(PostPermission.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(null);
  }

  public static PostPermission find(List<Boolean> flagList) {
    log.info("PostPermission.find");

    return stream(PostPermission.values())
        .filter(item -> item.getFlagList().equals(flagList))
        .findAny()
        .orElse(null);
  }

}
