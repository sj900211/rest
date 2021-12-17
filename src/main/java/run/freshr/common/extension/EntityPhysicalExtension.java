package run.freshr.common.extension;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.freshr.annotation.ColumnComment;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityPhysicalExtension {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @ColumnComment("일련 번호")
  protected Long id;

  @CreatedDate
  @ColumnComment("등록 날짜")
  protected LocalDateTime createDt;

}
