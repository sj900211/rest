package run.freshr.domain.blog.functional;

import com.querydsl.core.types.dsl.BooleanExpression;
import run.freshr.domain.auth.enumeration.Role;

@FunctionalInterface
public interface CheckPostPermissionFunctional {

  BooleanExpression checkPermission(Role role);

}
