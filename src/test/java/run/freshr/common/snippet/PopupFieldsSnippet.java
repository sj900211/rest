package run.freshr.common.snippet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

/**
 * The Class Popup fields snippet.
 *
 * @author [류성재]
 * @implNote 팝업 문서 설정
 * @since 2021. 3. 16. 오후 3:11:25
 */
public class PopupFieldsSnippet extends AbstractFieldsSnippet {

  /**
   * Instantiates a new Popup fields snippet.
   *
   * @param type                       the type
   * @param payloadSubsectionExtractor the payload subsection extractor
   * @param fieldDescriptorList        the field descriptor list
   * @param attributes                 the attributes
   * @param ignoreUndocumentedFields   the ignore undocumented fields
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 3:11:25
   */
  public PopupFieldsSnippet(
      String type, PayloadSubsectionExtractor<?> payloadSubsectionExtractor,
      List<FieldDescriptor> fieldDescriptorList, Map<String, Object> attributes,
      boolean ignoreUndocumentedFields
  ) {
    super(type, fieldDescriptorList, attributes, ignoreUndocumentedFields,
        payloadSubsectionExtractor);
  }

  /**
   * Gets content type.
   *
   * @param operation the operation
   * @return the content type
   * @author [류성재]
   * @implNote 컨텐츠 유형 Getter
   * @since 2021. 3. 16. 오후 3:11:25
   */
  @Override
  protected MediaType getContentType(Operation operation) {
    return operation.getResponse().getHeaders().getContentType();
  }

  /**
   * Get content byte [ ].
   *
   * @param operation the operation
   * @return the byte [ ]
   * @throws IOException the io exception
   * @author [류성재]
   * @implNote 컨텐츠 Getter
   * @since 2021. 3. 16. 오후 3:11:25
   */
  @Override
  protected byte[] getContent(Operation operation) throws IOException {
    return operation.getResponse().getContent();
  }

}
