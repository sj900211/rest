package run.freshr.domain.common.entity;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.annotation.TableComment;
import run.freshr.common.extension.EntityPhysicalExtension;
import run.freshr.domain.mapping.entity.AccountHashtagMapping;

@Slf4j
@Entity
@Table(name = "TB_COM_HASHTAG")
@TableComment(value = "공통 관리 > 해시태그 관리", extend = "EntityPhysicalExtension")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Hashtag extends EntityPhysicalExtension<String> {

  @OneToMany(fetch = LAZY, mappedBy = "hashtag")
  private List<AccountHashtagMapping> accountList;

  private Hashtag(String id) {
    log.info("Hashtag.Constructor");

    this.id = id;
  }

  public static Hashtag createEntity(String id) {
    log.info("Hashtag.createEntity");

    return new Hashtag(id);
  }

}
