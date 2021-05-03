package run.freshr.domain.user;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static java.util.Optional.ofNullable;
import static run.freshr.domain.auth.entity.QAccount.account;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;

import java.util.List;
import run.freshr.common.util.PrintUtil;
import run.freshr.domain.common.ResponseDocs;
import run.freshr.common.snippet.PopupFieldsSnippet;
import run.freshr.domain.user.vo.EUserSearch;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

public class AccountDocs {

  public static class Request {

    public static List<FieldDescriptor> createAccount() {
      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .field(account.username, account.password, account.name)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> getAccountPage() {
      String linkGroupName = LOWER_CAMEL.to(LOWER_HYPHEN, "getAccountPage");

      return PrintUtil
          .builder()

          .parameter(EUserSearch.page, EUserSearch.cpp)

          .prefixOptional()

          .parameter(EUserSearch.word)

          .linkParameter(linkGroupName, EUserSearch.key)
          .linkParameter(linkGroupName, EUserSearch.otarget)
          .linkParameter(linkGroupName, EUserSearch.otype)

          .parameter(EUserSearch.sdt, "등록 날짜 시간 기간 조회 by 시작")
          .parameter(EUserSearch.edt, "등록 날짜 시간 기간 조회 by 종료")

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getAccount() {
      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .parameter(account.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> removeAccount() {
      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .parameter(account.id)

          .build()
          .getParameterList();
    }
  }

  public static class Response {

    public static List<FieldDescriptor> createAccount() {
      return ResponseDocs
          .Response
          .data()

          .prefixDescription("사용자")

          .field(account.id)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccountPage() {
      return ResponseDocs
          .Response
          .page()

          .prefixDescription("사용자")

          .field(account.id, account.username, account.name, account.createDt, account.updateDt)

          .prefixOptional()

          .field(account.signDt)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccount() {
      return ResponseDocs
          .Response
          .data()

          .prefixDescription("사용자")

          .field(account.id, account.username, account.name, account.createDt, account.updateDt)

          .prefixOptional()

          .field(account.signDt)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {

    public static PopupFieldsSnippet[] getAccountPage() {
      String prefixTitle = "account-";

      return PrintUtil
          .builder()

          .popupPage(prefixTitle + EUserSearch.key.get("name"), PrintUtil
              .builder()
              .prefixOptional()
              .prefixDescription("사용자")
              .field(account.username, account.name)
              .build()
              .getFieldList())

          .popupPage(prefixTitle + EUserSearch.otarget.get("name"), PrintUtil
              .builder()
              .prefixOptional()
              .prefixDescription("사용자")
              .field(account.username, account.name)
              .field("create", "등록일", STRING, true)
              .build()
              .getFieldList())

          .popupPage(prefixTitle + ofNullable(EUserSearch.otype.get("name")).orElse(""), PrintUtil
              .builder()
              .field("asc", "오름차순", STRING, true)
              .field("desc", "내림차순", STRING, true)
              .build()
              .getFieldList())

          .build()
          .getPopupList()
          .toArray(PopupFieldsSnippet[]::new);
    }
  }

}
