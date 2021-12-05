package run.freshr.domain.blog.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import run.freshr.common.extension.EntityAuditLogicalExtension;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.mapping.entity.PostHashtagMapping;

@Slf4j
@Entity
@Table(
    name = "TB_BLOG_POST",
    indexes = {@Index(name = "IDX_BLOG_POST_FLAG", columnList = "useFlag, delFlag")}
)
@TableComment(value = "블로그 관리 > 포스팅 관리", extend = "EntityAuditLogicalExtension")
@Getter
@DynamicInsert
@DynamicUpdate
@Inheritance(strategy = JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = PROTECTED)
public class Post extends EntityAuditLogicalExtension<Long> {

  @Column(nullable = false, length = 100)
  @ColumnComment("제목")
  private String title;

  @Lob
  @Column(nullable = false)
  @ColumnComment("내용")
  private String contents;

  @OneToMany(fetch = LAZY, mappedBy = "post")
  private List<PostHashtagMapping> hashtagList;

  private Post(String title, String contents, Account creator) {
    this.title = title;
    this.contents = contents;
    this.creator = creator;
    this.updater = creator;
  }

  public static Post createEntity(String title, String contents, Account creator) {
    return new Post(title, contents, creator);
  }

  public void removeEntity(Account updater) {
    this.updater = updater;

    remove();
  }

}
