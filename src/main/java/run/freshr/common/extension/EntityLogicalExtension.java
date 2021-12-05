package run.freshr.common.extension;

import static javax.persistence.GenerationType.IDENTITY;
import static run.freshr.common.config.DefaultColumnConfig.FALSE;
import static run.freshr.common.config.DefaultColumnConfig.INSERT_TIMESTAMP;
import static run.freshr.common.config.DefaultColumnConfig.TRUE;
import static run.freshr.common.config.DefaultColumnConfig.UPDATE_TIMESTAMP;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import run.freshr.annotation.ColumnComment;

@Getter
@MappedSuperclass
public class EntityLogicalExtension<ID extends Serializable> {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @ColumnComment("일련 번호")
  protected ID id;

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

  public void remove() {
    useFlag = false;
    delFlag = true;
  }

}
