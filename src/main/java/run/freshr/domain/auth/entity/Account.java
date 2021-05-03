package run.freshr.domain.auth.entity;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import run.freshr.domain.auth.enumeration.SignPrivilege;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TB_AUTH_ACCOUNT")
@TableComment(value = "권한 관리 > 사용자 계정 관리", extend = "Sign")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Account extends Sign {

  @Column(nullable = false)
  @ColumnComment("이름")
  private String name;

  /**
   * Instantiates a new Account.
   *
   * @param privilege the privilege
   * @param username  the username
   * @param password  the password
   * @param name      the name
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:14:12
   */
  private Account(SignPrivilege privilege, String username, String password, String name) {
    this.privilege = privilege;
    this.username = username;
    this.password = password;
    this.name = name;
  }

  /**
   * Create entity account.
   *
   * @param username the username
   * @param password the password
   * @param name     the name
   * @return the account
   * @author [류성재]
   * @implNote 생성 메서드
   * @since 2021. 3. 16. 오후 2:14:12
   */
  public static Account createEntity(String username, String password, String name) {
    return new Account(SignPrivilege.USER, username, password, name);
  }

  /**
   * Update entity.
   *
   * @param name the name
   * @author [류성재]
   * @implNote 수정 메서드
   * @since 2021. 3. 16. 오후 2:14:12
   */
  public void updateEntity(String name) {
    this.name = name;
  }

  /**
   * Remove entity.
   *
   * @author [류성재]
   * @implNote 삭제 메서드
   * @since 2021. 3. 16. 오후 2:14:12
   */
  public void removeEntity() {
    removeSign();
  }

}
