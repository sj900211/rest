package run.freshr.domain.blog.elasticsearch;

import static lombok.AccessLevel.PROTECTED;
import static org.springframework.data.elasticsearch.annotations.DateFormat.basic_date_time;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import run.freshr.domain.auth.document.AuditDocument;
import run.freshr.domain.mapping.document.PostHashtagMappingForPostDocument;

@Document(indexName = "doc_post")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostSearch implements Persistable<Long> {

  @Id
  private Long id;

  private String title;

  private Integer hits;

  private Boolean managerGrant;

  private Boolean leaderGrant;

  private Boolean coachGrant;

  private Boolean userGrant;

  private Boolean anonymousGrant;

  @CreatedDate
  @Field(type = FieldType.Date, format = basic_date_time)
  private LocalDateTime createDt;

  @LastModifiedDate
  @Field(type = FieldType.Date, format = basic_date_time)
  private LocalDateTime updateDt;

  private AuditDocument creator;

  private AuditDocument updater;

  @Field(type = FieldType.Nested)
  private List<PostHashtagMappingForPostDocument> hashtagList;

  private PostSearch(Long id, String title, Boolean managerGrant, Boolean leaderGrant,
      Boolean coachGrant, Boolean userGrant, Boolean anonymousGrant,
      AuditDocument creator, List<PostHashtagMappingForPostDocument> hashtagList) {
    this.id = id;
    this.title = title;
    this.hits = 0;
    this.managerGrant = managerGrant;
    this.leaderGrant = leaderGrant;
    this.coachGrant = coachGrant;
    this.userGrant = userGrant;
    this.anonymousGrant = anonymousGrant;
    this.creator = creator;
    this.updater = creator;
    this.hashtagList = hashtagList;
  }

  public static PostSearch createDocument(Long id, String title, Boolean managerGrant,
      Boolean leaderGrant, Boolean coachGrant, Boolean userGrant, Boolean anonymousGrant,
      AuditDocument creator, List<PostHashtagMappingForPostDocument> hashtagList) {
    return new PostSearch(id, title,
        managerGrant, leaderGrant, coachGrant, userGrant, anonymousGrant,
        creator, hashtagList);
  }

  public void addHits() {
    this.hits++;
  }

  public void updateDocument(String title, Boolean managerGrant,
      Boolean leaderGrant, Boolean coachGrant, Boolean userGrant, Boolean anonymousGrant,
      AuditDocument updater, List<PostHashtagMappingForPostDocument> hashtagList) {
    this.title = title;
    this.managerGrant = managerGrant;
    this.leaderGrant = leaderGrant;
    this.coachGrant = coachGrant;
    this.userGrant = userGrant;
    this.anonymousGrant = anonymousGrant;
    this.updater = updater;
    this.hashtagList = hashtagList;
  }

  @Override
  public boolean isNew() {
    return this.id == null || this.createDt == null;
  }

}
