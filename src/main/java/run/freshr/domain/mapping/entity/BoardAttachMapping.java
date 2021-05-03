package run.freshr.domain.mapping.entity;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import run.freshr.common.config.DefaultColumnConfig;
import run.freshr.common.extension.EntityExtension;
import run.freshr.domain.common.entity.Attach;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import run.freshr.domain.community.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TB_MAP_BOARD_ATTACH")
@TableComment(value = "연관 관계 관리 > 게시글 첨부 파일 관리", extend = "EntityExtension")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class BoardAttachMapping extends EntityExtension {

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "boardId")
  @ColumnComment("게시글")
  private Board board;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "attachId")
  @ColumnComment("첨부파일")
  private Attach attach;

  @ColumnDefault(DefaultColumnConfig.ZERO)
  @ColumnComment("정렬 순서")
  private Integer sort;

  /**
   * Instantiates a new Board attach mapping.
   *
   * @param board  the board
   * @param attach the attach
   * @param sort   the sort
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:48:07
   */
  private BoardAttachMapping(Board board, Attach attach, Integer sort) {
    this.board = board;
    this.attach = attach;
    this.sort = sort;
  }

  /**
   * Create entity board attach mapping.
   *
   * @param board  the board
   * @param attach the attach
   * @param sort   the sort
   * @return the board attach mapping
   * @author [류성재]
   * @implNote 생성 메서드
   * @since 2021. 3. 16. 오후 2:48:07
   */
  public static BoardAttachMapping createEntity(Board board, Attach attach, Integer sort) {
    return new BoardAttachMapping(board, attach, sort);
  }

}
