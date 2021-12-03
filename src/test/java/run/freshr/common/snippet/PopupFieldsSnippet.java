package run.freshr.common.snippet;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

@Slf4j
public class PopupFieldsSnippet extends AbstractFieldsSnippet {

  public PopupFieldsSnippet(
      String type, PayloadSubsectionExtractor<?> payloadSubsectionExtractor,
      List<FieldDescriptor> fieldDescriptorList, Map<String, Object> attributes,
      boolean ignoreUndocumentedFields
  ) {
    super(type, fieldDescriptorList, attributes, ignoreUndocumentedFields,
        payloadSubsectionExtractor);
  }

  @Override
  protected MediaType getContentType(Operation operation) {
    log.info("PopupFieldsSnippet.getContentType");

    return operation.getResponse().getHeaders().getContentType();
  }

  @Override
  protected byte[] getContent(Operation operation) {
    log.info("PopupFieldsSnippet.getContent");

    return operation.getResponse().getContent();
  }

}
