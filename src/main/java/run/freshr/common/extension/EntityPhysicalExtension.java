package run.freshr.common.extension;

import static javax.persistence.GenerationType.IDENTITY;
import static run.freshr.common.config.DefaultColumnConfig.INSERT_TIMESTAMP;

import java.time.LocalDateTime;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import run.freshr.annotation.ColumnComment;

@Getter
@MappedSuperclass
public class EntityPhysicalExtension {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @ColumnComment("일련 번호")
  protected Long id;

  @ColumnDefault(INSERT_TIMESTAMP)
  @ColumnComment("등록 날짜")
  protected LocalDateTime createDt;

}
