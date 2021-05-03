package run.freshr.domain.common;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;

import run.freshr.common.util.PrintUtil;
import run.freshr.common.util.PrintUtil.Builder;

/**
 * The Class Response docs.
 *
 * @author [류성재]
 * @implNote 공통 문서 객체
 * @since 2021. 3. 16. 오후 3:23:05
 */
public class ResponseDocs {

  /**
   * The Class Request.
   *
   * @author [류성재]
   * @implNote Request 문서
   * @since 2021. 3. 16. 오후 3:23:05
   */
  public static class Request {

  }

  /**
   * The Class Response.
   *
   * @author [류성재]
   * @implNote Response 문서
   * @since 2021. 3. 16. 오후 3:23:05
   */
  public static class Response {

    /**
     * Data print util . builder.
     *
     * @return the print util . builder
     * @author [류성재]
     * @implNote Data 기본 반환 객체
     * @since 2021. 3. 16. 오후 3:23:05
     */
    public static Builder data() {
      return PrintUtil
          .builder()

          .prefixOptional()
          .field("message", "결과 메시지", STRING)
          .field("exist", "메시지 팝업 노출 여부", BOOLEAN)
          .field("data", "반환 데이터 객체", OBJECT)
          .clearOptional()

          .prefix("data");
    }

    /**
     * List print util . builder.
     *
     * @return the print util . builder
     * @author [류성재]
     * @implNote List 기본 반환 객체
     * @since 2021. 3. 16. 오후 3:23:05
     */
    public static PrintUtil.Builder list() {
      return PrintUtil
          .builder()

          .prefixOptional()
          .field("message", "결과 메시지", STRING)
          .field("exist", "메시지 팝업 노출 여부", BOOLEAN)
          .field("list", "반환 데이터 목록", ARRAY)
          .clearOptional()

          .prefix("list[]");
    }

    /**
     * Page print util . builder.
     *
     * @return the print util . builder
     * @author [류성재]
     * @implNote Page 기본 반환 객체
     * @since 2021. 3. 16. 오후 3:23:05
     */
    public static PrintUtil.Builder page() {
      return PrintUtil
          .builder()

          .prefixOptional()
          .field("message", "결과 메시지", STRING)
          .field("exist", "메시지 팝업 노출 여부", BOOLEAN)
          .field("page", "반환 데이터 객체", OBJECT)
          .clearOptional()

          .prefix("page")

          .field("content", "페이지 데이터 목록", ARRAY)
          .field("totalElements", "총 데이터 수", NUMBER)
          .field("last", "마지막 페이지 여부", BOOLEAN)
          .field("totalPages", "총 페이지 수", NUMBER)
          .field("size", "요청한 페이지 데이터 수", NUMBER)
          .field("number", "요청한 페이지 번호", NUMBER)
          .field("sort", "정렬 관련 데이터 객체", OBJECT)
          .field("sort.sorted", "정렬 관련 데이터 객체", BOOLEAN)
          .field("sort.unsorted", "정렬 관련 데이터 객체", BOOLEAN)
          .field("sort.empty", "정렬 관련 데이터 객체", BOOLEAN)
          .field("numberOfElements", "요청한 페이지의 데이터 수", NUMBER)
          .field("first", "첫 페이지 여부", BOOLEAN)
          .field("empty", "데이터가 비어있는지 여부", BOOLEAN)
          .field("pageable", "페이징 관련 데이터 객체", OBJECT)

          .prefix("page.pageable")

          .field("sort", "페이징 관련 데이터 객체", OBJECT)
          .field("sort.sorted", "페이징 관련 데이터 객체", BOOLEAN)
          .field("sort.unsorted", "페이징 관련 데이터 객체", BOOLEAN)
          .field("sort.empty", "페이징 관련 데이터 객체", BOOLEAN)
          .field("offset", "페이징 관련 데이터 객체", NUMBER)
          .field("pageSize", "페이징 관련 데이터 객체", NUMBER)
          .field("pageNumber", "페이징 관련 데이터 객체", NUMBER)
          .field("unpaged", "페이징 관련 데이터 객체", BOOLEAN)
          .field("paged", "페이징 관련 데이터 객체", BOOLEAN)

          .prefix("page.content[]");
    }
  }

  /**
   * The Class Docs.
   *
   * @author [류성재]
   * @implNote 그 외 문서
   * @since 2021. 3. 16. 오후 3:23:05
   */
  public static class Docs {

  }

}
