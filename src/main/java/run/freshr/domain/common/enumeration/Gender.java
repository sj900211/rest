package run.freshr.domain.common.enumeration;

import java.util.Arrays;
import run.freshr.mapper.EnumModel;

/**
 * 성별 데이터
 */
public enum Gender implements EnumModel {

  MALE("남자"),
  FEMALE("여자");

  /**
   * 한글 설명
   */
  private final String value;

  /**
   * Instantiates a new Gender.
   *
   * @param value the value
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:39:51
   */
  Gender(String value) {
    this.value = value;
  }

  /**
   * Find gender.
   *
   * @param key the key
   * @return the gender
   * @author [류성재]
   * @implNote name 값이 같은 데이터 조회
   * @since 2021. 3. 16. 오후 2:39:51
   */
  public static Gender find(String key) {
    return Arrays.stream(Gender.values())
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
   * @since 2021. 3. 16. 오후 2:39:51
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
   * @since 2021. 3. 16. 오후 2:39:51
   */
  @Override
  public String getValue() {
    return value;
  }

}
