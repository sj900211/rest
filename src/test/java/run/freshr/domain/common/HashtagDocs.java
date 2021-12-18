package run.freshr.domain.common;

import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static run.freshr.domain.common.entity.QHashtag.hashtag;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;

@Slf4j
public class HashtagDocs {

  public static class Request {

    public static List<FieldDescriptor> createHashtag() {
      log.info("HashtagDocs.Request.createHashtag");

      return PrintUtil
          .builder()

          .field(hashtag.id, "해시태그")

          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> deleteHashtag() {
      log.info("HashtagDocs.Request.deleteHashtag");

      return PrintUtil
          .builder()

          .parameter("id", "해시태그")

          .build()
          .getParameterList();
    }

  }

  public static class Response {

    public static List<FieldDescriptor> getHashtagAll() {
      log.info("HashtagDocs.Response.getHashtagAll");

      return ResponseDocs
          .Response
          .list()

          .field(hashtag.id, "해시태그")

          .build()
          .getFieldList();
    }

  public static List<FieldDescriptor> getHashtagList() {
    log.info("HashtagDocs.Response.getHashtagList");

    return ResponseDocs
        .Response
        .list()

        .field(hashtag.id, "해시태그")
        .field("count", "포스팅 수", NUMBER)

        .build()
        .getFieldList();
  }

}

  public static class Docs {
  }

}
