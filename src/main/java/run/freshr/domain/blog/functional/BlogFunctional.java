package run.freshr.domain.blog.functional;

import static run.freshr.common.functional.GlobalFunctional.getBinaryAnd;
import static run.freshr.domain.blog.entity.QPost.post;
import static run.freshr.domain.blog.enumeration.PostPermission.B100000;
import static run.freshr.domain.blog.enumeration.PostPermission.B110000;
import static run.freshr.domain.blog.enumeration.PostPermission.B111000;
import static run.freshr.domain.blog.enumeration.PostPermission.B111100;
import static run.freshr.domain.blog.enumeration.PostPermission.B111110;
import static run.freshr.domain.blog.enumeration.PostPermission.B111111;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import run.freshr.domain.auth.enumeration.Role;

public class BlogFunctional {

  private static final CheckPostPermissionFunctional checkPostPermissionFunctional = role -> {
    Integer permission = role.getPermission();

    return new CaseBuilder()
        .when(post.permission.eq(B111110))
        .then(getBinaryAnd(B111110.getPermission(), permission))

        .when(post.permission.eq(B111100))
        .then(getBinaryAnd(B111100.getPermission(), permission))

        .when(post.permission.eq(B111000))
        .then(getBinaryAnd(B111000.getPermission(), permission))

        .when(post.permission.eq(B110000))
        .then(getBinaryAnd(B110000.getPermission(), permission))

        .when(post.permission.eq(B100000))
        .then(getBinaryAnd(B100000.getPermission(), permission))

        .otherwise(getBinaryAnd(B111111.getPermission(), permission)).gt(0);
  };

  public static BooleanExpression checkPermission(Role role) {
    return checkPostPermissionFunctional.checkPermission(role);
  }

}
