package run.freshr.domain.common.entity;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import run.freshr.domain.mapping.entity.AccountHashtagMapping;
import run.freshr.domain.mapping.entity.PostHashtagMapping;

@Slf4j
@Entity
@Table(name = "TB_COM_HASHTAG")
@TableComment(value = "공통 관리 > 해시태그 관리")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Hashtag {

  @Id
  @ColumnComment("일련 번호")
  private String id;

  @CreatedDate
  @ColumnComment("등록 날짜")
  private LocalDateTime createDt;

  @OneToMany(fetch = LAZY, mappedBy = "hashtag")
  private List<AccountHashtagMapping> accountList;

  @OneToMany(fetch = LAZY, mappedBy = "hashtag")
  private List<PostHashtagMapping> postList;

  private Hashtag(String id) {
    log.info("Hashtag.Constructor");

    this.id = id;
  }

  public static Hashtag createEntity(String id) {
    log.info("Hashtag.createEntity");

    return new Hashtag(id);
  }

}
