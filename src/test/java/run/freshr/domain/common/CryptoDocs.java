package run.freshr.domain.common;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.ResponseDocs;

@Slf4j
public class CryptoDocs {

  public static class Request {
  }

  public static class Response {

    public static List<FieldDescriptor> getPublicKey() {
      log.info("CryptoDocs.Response.getPublicKey");

      return ResponseDocs
          .Response
          .data()

          .field("key", "RSA Public Key", STRING)

          .build()
          .getFieldList();
    }

  }

  public static class Docs {
  }

}
