package run.freshr.domain.common;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.snippet.PopupFieldsSnippet;
import run.freshr.common.util.PrintUtil;
import run.freshr.mapper.EnumValue;

@Slf4j
public class EnumDocs {

  public static class Request {

    public static List<ParameterDescriptor> getEnum() {
      log.info("EnumDocs.Request.getEnum");

      return PrintUtil
          .builder()

          .parameter("pick", "그룹 이름")

          .build()
          .getParameterList();
    }
  }

  public static class Response {

    public static PopupFieldsSnippet[] getEnumList(Map<String, List<EnumValue>> enumMap) {
      log.info("EnumDocs.Response.getEnumList");

      PrintUtil.Builder builder = PrintUtil
          .builder();

      enumMap.forEach((key, value) -> builder
          .popupData(key, value
              .stream()
              .map(item -> PrintUtil
                  .builder()
                  .field(item.getKey(), item.getValue(), STRING, true)
                  .build()
                  .getFieldList()
                  .get(0))
              .collect(Collectors.toList())));

      return builder.build().getPopupList().toArray(PopupFieldsSnippet[]::new);
    }
  }

  public static class Docs {

  }

}
