package run.freshr.domain.auth;

import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.auth.entity.QAccount.account;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;

@Slf4j
public class AccountDocs {

  public static class Request {

    public static List<FieldDescriptor> signIn() {
      log.info("AccountDocs.Request.signIn");

      return PrintUtil
          .builder()

          .prefixDescription("관리자")

          .field(account.username, "고유 아이디 [BASE64 인코딩]")
          .field(account.password, "비밀번호 [BASE64 인코딩]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> updatePassword() {
      log.info("AccountDocs.Request.updatePassword");

      return PrintUtil
          .builder()

          .field("originPassword", "원래 비밀번호 [BASE64 인코딩]", STRING)
          .field(account.password, "변경 비밀번호 [BASE64 인코딩]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> updateInfo() {
      log.info("AccountDocs.Request.updateInfo");

      return PrintUtil
          .builder()

          .field(account.name, "이름 [BASE64 인코딩]")

          .prefixOptional()

          .field(account.introduce)
          .field(account.profile, "프로필 이미지 객체", OBJECT)
          .field(account.profile.id)

          .build()
          .getFieldList();
    }
  }

  public static class Response {

    public static List<FieldDescriptor> refreshToken() {
      log.info("AccountDocs.Response.refreshToken");

      return ResponseDocs
          .Response
          .data()

          .field("accessToken", "접속 토큰", STRING)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> signIn() {
      log.info("AccountDocs.Response.signIn");

      return ResponseDocs
          .Response
          .data()

          .field("accessToken", "접속 토큰", STRING)
          .field("refreshToken", "갱신 토큰", STRING)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getInfo() {
      log.info("AccountDocs.Response.getInfo");

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
