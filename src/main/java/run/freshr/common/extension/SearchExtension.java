package run.freshr.common.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import run.freshr.annotation.SearchClass;
import run.freshr.annotation.SearchComment;

@Data
@SearchClass(base = true, extend = false)
public class SearchExtension {

  @SearchComment("페이지 수 [0 부터 시작]")
  protected Integer page;
  @SearchComment("페이지 데이터 수")
  protected Integer size;

  @SearchComment("일련 번호")
  protected Long id;

  @SearchComment("작성자 일련 번호")
  protected Long creator;
  @SearchComment("마지막 수정자 일련 번호")
  protected Long updater;

  @SearchComment("사용 여부")
  protected Boolean use;

  @JsonProperty("search-type")
  @SearchComment("검색 유형")
  protected String searchType;

  @SearchComment("자연어 검색 대상")
  protected String key;
  @SearchComment("자연어 검색어")
  protected String word;

  @JsonProperty("oder-type")
  @SearchComment("정렬 대상")
  protected String orderType;
  @JsonProperty("oder-by")
  @SearchComment("정렬 유형")
  protected String orderBy;

  @JsonProperty("date-search-type")
  @SearchComment("날짜 검색 유형")
  protected String dateSearchType;
  @JsonProperty("start-date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @SearchComment("시작 날짜")
  protected LocalDate startDate;
  @JsonProperty("end-date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @SearchComment("종료 날짜")
  protected LocalDate endDate;
  @JsonProperty("start-datetime")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @SearchComment("시작 날짜 시간")
  protected LocalDateTime startDatetime;
  @JsonProperty("end-datetime")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @SearchComment("종료 날짜 시간")
  protected LocalDateTime endDatetime;

}
