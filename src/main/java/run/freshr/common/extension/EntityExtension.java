package run.freshr.common.extension;

import static javax.persistence.GenerationType.IDENTITY;
import static run.freshr.common.config.DefaultColumnConfig.FALSE;
import static run.freshr.common.config.DefaultColumnConfig.INSERT_TIMESTAMP;
import static run.freshr.common.config.DefaultColumnConfig.TRUE;
import static run.freshr.common.config.DefaultColumnConfig.UPDATE_TIMESTAMP;

import java.time.LocalDateTime;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import run.freshr.annotation.ColumnComment;

/**
 * The Class Entity extension.
 *
 * @author [류성재]
 * @implNote Entity Class 에서 상속받는 MappedSuperclass
 * @since 2021. 2. 24. 오후 5:50:37
 */
@Getter
@MappedSuperclass
public class EntityExtension {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @ColumnComment("일련 번호")
  protected Long id;

  @ColumnDefault(TRUE)
  @ColumnComment("사용 여부")
  protected Boolean useFlag;

  @ColumnDefault(FALSE)
  @ColumnComment("삭제 여부")
  protected Boolean delFlag;

  @ColumnDefault(INSERT_TIMESTAMP)
  @ColumnComment("등록 날짜")
  protected LocalDateTime createDt;

  @ColumnDefault(UPDATE_TIMESTAMP)
  @ColumnComment("마지막 수정 날짜")
  protected LocalDateTime updateDt;

  /**
   * Remove.
   *
   * @author [류성재]
   * @implNote 논리 삭제
   * @since 2021. 2. 24. 오후 5:50:37
   */
  public void remove() {
    useFlag = false;
    delFlag = true;
  }

}
