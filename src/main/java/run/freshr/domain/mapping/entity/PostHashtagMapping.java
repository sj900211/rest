package run.freshr.domain.mapping.entity;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.embedded.PostHashtagMappingEmbeddedId;

@Slf4j
@Entity
@Table(name = "TB_MAP_POST_HASHTAG")
@TableComment(value = "공통 관리 > 첨부파일 관리")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PostHashtagMapping {

  @EmbeddedId
  private PostHashtagMappingEmbeddedId id;

  @CreatedDate
  @ColumnComment("등록 날짜")
  private LocalDateTime createDt;

  @MapsId("postId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_POST_HASHTAG_MAPPING_POST"))
  @ColumnComment("포스팅 일련 번호")
  private Post post;

  @MapsId("hashtagId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "hashtag_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_POST_HASHTAG_MAPPING_HASHTAG"))
  @ColumnComment("해시태그")
  private Hashtag hashtag;

  private PostHashtagMapping(Post post, Hashtag hashtag) {
    log.info("PostHashtagMapping.Constructor");

    this.id = PostHashtagMappingEmbeddedId.createId(post.getId(), hashtag.getId());
    this.post = post;
    this.hashtag = hashtag;
  }

  public static PostHashtagMapping createEntity(Post post, Hashtag hashtag) {
    log.info("PostHashtagMapping.createEntity");

    return new PostHashtagMapping(post, hashtag);
  }

}
