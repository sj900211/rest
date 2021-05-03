package run.freshr.domain.auth.entity;

import static javax.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import run.freshr.domain.auth.enumeration.SignPrivilege;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import run.freshr.common.extension.EntityExtension;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TB_AUTH_SIGN")
@TableComment(value = "권한 관리 > 계정 관리", extend = "EntityExtension")
@Getter
@DynamicInsert
@DynamicUpdate
@Inheritance(strategy = JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = PROTECTED)
public class Sign extends EntityExtension {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @ColumnComment("계정 유형")
  protected SignPrivilege privilege;

  @Column(
      nullable = false,
      length = 100,
      unique = true
  )
  @ColumnComment("아이디")
  protected String username;

  @Column(nullable = false)
  @ColumnComment("비밀번호")
  protected String password;

  @ColumnComment("최근 접속 날짜 시간")
  protected LocalDateTime signDt;

  @ColumnComment("탈퇴 날짜")
  protected LocalDateTime removeDt;

  /**
   * Update password.
   *
   * @param password the password
   * @author [류성재]
   * @implNote 비밀번호 변경
   * @since 2021. 3. 16. 오후 2:16:53
   */
  public void updatePassword(String password) {
    this.password = password;
  }

  /**
   * Update sign dt.
   *
   * @author [류성재]
   * @implNote 최근 접속 날짜 시간 갱신
   * @since 2021. 3. 16. 오후 2:16:53
   */
  public void updateSignDt() {
    this.signDt = LocalDateTime.now();
  }

  /**
   * Remove sign.
   *
   * @author [류성재]
   * @implNote 삭제 메서드
   * @since 2021. 3. 16. 오후 2:16:53
   */
  public void removeSign() {
    this.username = this.username + "-" + LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    this.removeDt = LocalDateTime.now();

    remove();
  }

}
