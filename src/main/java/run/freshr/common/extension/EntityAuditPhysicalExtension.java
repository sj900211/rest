package run.freshr.common.extension;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static run.freshr.common.config.DefaultColumnConfig.FALSE;
import static run.freshr.common.config.DefaultColumnConfig.TRUE;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.freshr.annotation.ColumnComment;
import run.freshr.domain.auth.entity.Account;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityAuditPhysicalExtension {

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

  @CreatedDate
  @ColumnComment("등록 날짜")
  protected LocalDateTime createDt;

  @LastModifiedDate
  @ColumnComment("마지막 수정 날짜")
  protected LocalDateTime updateDt;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "creator_id")
  @ColumnComment("등록자 일련 번호")
  protected Account creator;

  public boolean checkOwner(Account entity) {
    return creator.equals(entity);
  }

  public void remove() {
    useFlag = false;
    delFlag = true;
  }

}
