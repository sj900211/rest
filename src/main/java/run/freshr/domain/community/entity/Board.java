package run.freshr.domain.community.entity;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.hasLength;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import run.freshr.common.config.DefaultColumnConfig;
import run.freshr.common.extension.EntityExtension;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.mapping.entity.BoardAttachMapping;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TB_CMNT_BOARD")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@TableComment(value = "커뮤니티 관리 > 게시글 관리", extend = "EntityExtension")
public class Board extends EntityExtension {

  @Column(
      nullable = false,
      length = 50
  )
  @ColumnComment("제목")
  private String title;

  @Lob
  @Column(nullable = false)
  @ColumnComment("내용")
  private String content;

  @ColumnDefault(DefaultColumnConfig.ZERO)
  @ColumnComment("조회수")
  private Integer hit;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "creatorId")
  @ColumnComment("작성자")
  private Account creator;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "updaterId")
  @ColumnComment("마지막 수정자")
  private Account updater;

  @OneToMany(fetch = LAZY, mappedBy = "board")
  private List<BoardAttachMapping> attachList;

  /**
   * Instantiates a new Board.
   *
   * @param title   the title
   * @param content the content
   * @param creator the creator
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:45:26
   */
  private Board(String title, String content, Account creator) {
    this.title = title;
    this.content = content;
    this.creator = creator;
    this.updater = creator;
  }

  /**
   * Create entity board.
   *
   * @param title   the title
   * @param content the content
   * @param account the account
   * @return the board
   * @author [류성재]
   * @implNote 생성 메서드
   * @since 2021. 3. 16. 오후 2:45:26
   */
  public static Board createEntity(String title, String content, Account account) {
    return new Board(title, content, account);
  }

  /**
   * Update entity.
   *
   * @param title   the title
   * @param content the content
   * @param updater the updater
   * @author [류성재]
   * @implNote 수정 메서드
   * @since 2021. 3. 16. 오후 2:45:27
   */
  public void updateEntity(String title, String content, Account updater) {
    if (hasLength(title)) {
      this.title = title;
    }

    if (hasLength(content)) {
      this.content = content;
    }

    this.updater = updater;
  }

  /**
   * Add hit.
   *
   * @author [류성재]
   * @implNote 조회수 증가 메서드
   * @since 2021. 3. 16. 오후 2:45:27
   */
  public void addHit() {
    this.hit++;
  }

  /**
   * Remove entity.
   *
   * @author [류성재]
   * @implNote 삭제 메서드
   * @since 2021. 3. 16. 오후 2:45:27
   */
  public void removeEntity() {
    remove();
  }

  /**
   * Owner boolean.
   *
   * @param signed the signed
   * @return the boolean
   * @author [류성재]
   * @implNote 작성자인지 체크
   * @since 2021. 3. 16. 오후 2:45:27
   */
  public boolean owner(Account signed) {
    return signed.getId().equals(this.creator.getId());
  }

}
