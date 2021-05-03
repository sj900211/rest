package run.freshr.domain.auth;

import static run.freshr.domain.auth.entity.QAccount.account;
import static run.freshr.domain.auth.entity.QManager.manager;
import static run.freshr.domain.auth.entity.QSign.sign;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;

import java.util.List;
import run.freshr.common.util.PrintUtil;
import run.freshr.domain.common.ResponseDocs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;

@Slf4j
public class SignDocs {

  public static class Request {

    public static List<FieldDescriptor> signIn() {
      return PrintUtil
          .builder()

          .prefixDescription("관리자")

          .field(sign.username, "고유 아이디 [BASE64 인코딩]")
          .field(sign.password, "비밀번호 [BASE64 인코딩]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> updatePassword() {
      return PrintUtil
          .builder()

          .field("originPassword", "원래 비밀번호 [BASE64 인코딩]", STRING)
          .field(sign.password, "변경 비밀번호 [BASE64 인코딩]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> updateInfoByManager() {
      return PrintUtil
          .builder()

          .field(manager.name, "이름 [BASE64 인코딩]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> updateInfoByAccount() {
      return PrintUtil
          .builder()

          .field(account.name, "이름 [BASE64 인코딩]")

          .build()
          .getFieldList();
    }
  }

  public static class Response {

    public static List<FieldDescriptor> refreshToken() {
      return ResponseDocs
          .Response
          .data()

          .field("accessToken", "접속 토큰", STRING)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> signIn() {
      return ResponseDocs
          .Response
          .data()

          .field("accessToken", "접속 토큰", STRING)
          .field("refreshToken", "갱신 토큰", STRING)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getInfoByManager() {
      return ResponseDocs
          .Response
          .data()

          .field(manager.id, manager.privilege, manager.username, manager.name, manager.createDt,
              manager.updateDt)

          .prefixOptional()
          .field(manager.signDt)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getInfoByAccount() {
      return ResponseDocs
          .Response
          .data()

          .field(account.id, account.username, account.name, account.createDt, account.updateDt)

          .prefixOptional()
          .field(account.signDt)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {

  }

}
