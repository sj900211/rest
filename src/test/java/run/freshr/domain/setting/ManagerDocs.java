package run.freshr.domain.setting;

import static run.freshr.domain.auth.entity.QManager.manager;

import java.util.List;
import run.freshr.common.util.PrintUtil;
import run.freshr.domain.common.ResponseDocs;
import run.freshr.domain.setting.vo.ESettingSearch;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

public class ManagerDocs {

  public static class Request {

    public static List<FieldDescriptor> createManager() {
      return PrintUtil
          .builder()

          .prefixDescription("관리자")

          .field(manager.privilege, manager.username, manager.password, manager.name)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> getManagerPage() {
      return PrintUtil
          .builder()

          .parameter(ESettingSearch.page, ESettingSearch.cpp)

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getManager() {
      return PrintUtil
          .builder()

          .prefixDescription("관리자")

          .parameter(manager.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> updateManagerPath() {
      return PrintUtil
          .builder()

          .prefixDescription("관리자")

          .parameter(manager.id)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> updateManager() {
      return PrintUtil
          .builder()

          .prefixDescription("관리자")

          .field(manager.privilege, manager.name)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> removeManager() {
      return PrintUtil
          .builder()

          .prefixDescription("관리자")

          .parameter(manager.id)

          .build()
          .getParameterList();
    }
  }

  public static class Response {

    public static List<FieldDescriptor> createManager() {
      return ResponseDocs
          .Response
          .data()

          .prefixDescription("관리자")

          .field(manager.id)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getManagerPage() {
      return ResponseDocs
          .Response
          .page()

          .prefixDescription("관리자")

          .field(manager.id, manager.privilege, manager.username, manager.name, manager.createDt,
              manager.updateDt)
          .linkField(manager.privilege)

          .prefixOptional()

          .field(manager.signDt)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getManager() {
      return ResponseDocs
          .Response
          .data()

          .prefixDescription("관리자")

          .field(manager.id, manager.privilege, manager.username, manager.name, manager.createDt,
              manager.updateDt)
          .linkField(manager.privilege)

          .prefixOptional()

          .field(manager.signDt)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {

  }

}
