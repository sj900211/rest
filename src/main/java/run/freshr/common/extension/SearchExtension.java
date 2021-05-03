package run.freshr.common.extension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import run.freshr.annotation.SearchClass;
import run.freshr.annotation.SearchComment;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class Search extension.
 *
 * @author [류성재]
 * @implNote 검색 VO 에서 상속받는 Base VO
 * @since 2021. 2. 24. 오후 5:54:27
 */
@Data
@SearchClass(base = true, extend = false)
public class SearchExtension {

  @SearchComment("페이지 수 [0 부터 시작]")
  protected Integer page; // 0 부터 시작
  @SearchComment("페이지에 보여질 컨텐츠 수")
  protected Integer cpp; // Count Per Page

  @SearchComment("일련 번호")
  protected Long id;
  @SearchComment("검색에서 제외할 일련 번호 목록")
  protected List<Long> not;

  @SearchComment("작성자 일련 번호")
  protected Long creator;
  @SearchComment("마지막 수정자 일련 번호")
  protected Long updater;

  @SearchComment("사용 여부")
  protected Boolean use;

  @SearchComment("검색 유형")
  protected String stype; // search type

  @SearchComment("자연어 검색 유형")
  protected String ktype; // keyword search type
  @SearchComment("자연어 검색 대상")
  protected String key;
  @SearchComment("자연어 검색어")
  protected String word;

  @SearchComment("정렬 대상")
  protected String otarget; // order target
  @SearchComment("정렬 유형")
  protected String otype; // order type

  @SearchComment("날짜 검색 유형")
  protected String dtype; // date search type
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @SearchComment("시작 날짜")
  protected LocalDate sd; // start date
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @SearchComment("종료 날짜")
  protected LocalDate ed; // end date
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @SearchComment("시작 날짜 시간")
  protected LocalDateTime sdt; // start date time
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @SearchComment("종료 날짜 시간")
  protected LocalDateTime edt; // end date time

}
