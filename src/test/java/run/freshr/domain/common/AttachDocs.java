package run.freshr.domain.common;

import static run.freshr.domain.common.entity.QAttach.attach;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;

import java.util.List;
import run.freshr.common.util.PrintUtil;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestPartDescriptor;

public class AttachDocs {

  public static class Request {

    public static RequestPartDescriptor createAttachFile() {
      return partWithName("files").description("첨부 파일");
    }

    public static List<ParameterDescriptor> createAttach() {
      return PrintUtil
          .builder()

          .prefixDescription("첨부 파일")

          .parameter("directory", "파일이 저장될 디렉토리 이름")

          .prefixOptional()

          .parameter(attach.alt, attach.title)
          .parameter("limit", "파일 용량 제한. 입력한 값이 없으면 10MB로 기본설정. 단위 : MB")
          .parameter("sort", "정렬 순서 기준")

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> existAttach() {
      return PrintUtil
          .builder()

          .prefixDescription("첨부 파일")

          .parameter(attach.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getAttach() {
      return PrintUtil
          .builder()

          .prefixDescription("첨부 파일")

          .parameter(attach.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> removeAttach() {
      return PrintUtil
          .builder()

          .prefixDescription("첨부 파일")

          .parameter(attach.id)

          .build()
          .getParameterList();
    }

    public static RequestPartDescriptor createAttachForEditorCK() {
      return partWithName("upload").description("첨부 파일");
    }

    public static RequestPartDescriptor createAttachForEditorJodit() {
      return partWithName("upload").description("첨부 파일");
    }
  }

  public static class Response {

    public static List<FieldDescriptor> createAttach() {
      return ResponseDocs
          .Response
          .list()

          .prefixDescription("첨부 파일")

          .field(attach.id)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> existAttach() {
      return ResponseDocs
          .Response
          .data()

          .prefixDescription("첨부 파일")

          .field("is", "존재 여부", BOOLEAN)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAttach() {
      return ResponseDocs
          .Response
          .data()

          .prefixDescription("첨부 파일")

          .field("url", "리소스 URL <만료 시간 1 분>", STRING)
          .field(
              attach.id,
              attach.contentType,
              attach.filename, attach.size,
              attach.createDt, attach.updateDt
          )

          .prefixOptional()

          .field(attach.alt, attach.title)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> createAttachForEditorCK() {
      return PrintUtil
          .builder()

          .field("success", "성공 여부", BOOLEAN)

          .prefix("data")

          .field("uri", "업로드 파일 URI", STRING)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {

  }

}
