package run.freshr.common.config;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import run.freshr.domain.auth.enumeration.ManagerPrivilege;
import run.freshr.domain.common.enumeration.Gender;
import run.freshr.mapper.EnumMapper;
import run.freshr.mapper.EnumModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class Enum config.
 *
 * @author [류성재]
 * @implNote Enum 설정
 * @since 2021. 2. 24. 오후 5:38:16
 */
@Configuration
public class EnumConfig {

  /**
   * The Enums API 로 제공할 Enum 목록
   */
  private final List<Class<? extends EnumModel>> enums = new ArrayList<>(Arrays.asList(
      Gender.class,
      ManagerPrivilege.class
  ));

  /**
   * Enum mapper enum mapper.
   *
   * @return the enum mapper
   * @author [류성재]
   * @implNote EnumMapper Bean 등록
   * @since 2021. 2. 24. 오후 5:38:16
   */
  @Bean
  public EnumMapper enumMapper() {
    return setMapper(enums);
  }

  /**
   * Sets mapper.
   *
   * @param classes the classes
   * @return the mapper
   * @author [류성재]
   * @implNote 등록된 Enum 클래스를 lower-hyphen 문자열로 변환해서 key 값으로 설정
   * @since 2021. 2. 24. 오후 5:38:16
   */
  private EnumMapper setMapper(List<Class<? extends EnumModel>> classes) {
    EnumMapper enumMapper = new EnumMapper();

    classes.forEach(enumClass -> enumMapper
        .put(UPPER_CAMEL.to(LOWER_HYPHEN, enumClass.getSimpleName()), enumClass));

    return enumMapper;
  }

}
