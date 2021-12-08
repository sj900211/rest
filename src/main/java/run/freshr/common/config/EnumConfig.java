package run.freshr.common.config;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static java.util.List.of;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import run.freshr.domain.auth.enumeration.Privilege;
import run.freshr.domain.blog.enumeration.PostPageKeys;
import run.freshr.domain.common.enumeration.Gender;
import run.freshr.mapper.EnumMapper;
import run.freshr.mapper.EnumModel;

@Configuration
public class EnumConfig {

  private final List<Class<? extends EnumModel>> enums = new ArrayList<>(of(
      Gender.class,
      Privilege.class,
      PostPageKeys.class
  ));

  @Bean
  public EnumMapper enumMapper() {
    return setMapper(enums);
  }

  private EnumMapper setMapper(List<Class<? extends EnumModel>> classes) {
    EnumMapper enumMapper = new EnumMapper();

    classes.forEach(enumClass -> enumMapper
        .put(UPPER_CAMEL.to(LOWER_HYPHEN, enumClass.getSimpleName()), enumClass));

    return enumMapper;
  }

}
