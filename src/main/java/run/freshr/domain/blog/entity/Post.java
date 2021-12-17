package run.freshr.domain.blog.entity;

import static java.lang.Byte.parseByte;
import static java.lang.Integer.toBinaryString;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.common.config.DefaultColumnConfig.ZERO;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import run.freshr.common.extension.EntityAuditLogicalExtension;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.blog.enumeration.PostPermission;
import run.freshr.domain.mapping.entity.PostHashtagMapping;

@Slf4j
@Entity
@Table(
    name = "TB_BLOG_POST",
    indexes = {@Index(name = "IDX_BLOG_POST_FLAG", columnList = "useFlag, delFlag")}
)
@SequenceGenerator(
    name="SEQUENCE_GENERATOR",
    sequenceName="SEQ_BLOG_POST"
)
@TableComment(value = "블로그 관리 > 포스팅 관리", extend = "EntityAuditLogicalExtension")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Post extends EntityAuditLogicalExtension {

  @Column(nullable = false, length = 100)
  @ColumnComment("제목")
  private String title;

  @Lob
  @Column(nullable = false)
  @ColumnComment("내용")
  private String contents;

  @Enumerated(STRING)
  @Column(nullable = false)
  @ColumnComment("접근 권한")
  private PostPermission permission;

  @ColumnDefault(ZERO)
  @ColumnComment("조회수")
  private Integer hits;

  @OneToMany(fetch = LAZY, mappedBy = "post")
  private List<PostHashtagMapping> hashtagList;

  private Post(String title, String contents, PostPermission permission, Account creator) {
    this.title = title;
    this.contents = contents;
    this.permission = permission;
    this.creator = creator;
    this.updater = creator;
  }

  public static Post createEntity(String title, String contents,
      PostPermission permission, Account creator) {
    return new Post(title, contents, permission, creator);
  }

  public void addHits() {
    this.hits++;
  }

  public void updateEntity(String title, String contents,
      PostPermission permission, Account updater) {
    this.title = title;
    this.contents = contents;
    this.permission = permission;
    this.updater = updater;
  }

  public boolean checkPermission(Integer permission) {
    return parseByte(toBinaryString(this.permission.getPermission() & permission), 2) > 0;
  }

  public void removeEntity(Account updater) {
    this.updater = updater;

    remove();
  }

}
