package run.freshr.domain.auth.entity;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import run.freshr.domain.auth.enumeration.ManagerPrivilege;
import run.freshr.domain.auth.enumeration.SignPrivilege;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TB_AUTH_MANAGER")
@TableComment(value = "권한 관리 > 관리자 계정 관리", extend = "Sign")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Manager extends Sign {

  @Column(nullable = false)
  @ColumnComment("이름")
  private String name;

  /**
   * Instantiates a new Manager.
   *
   * @param privilege the privilege
   * @param username  the username
   * @param password  the password
   * @param name      the name
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:14:57
   */
  private Manager(SignPrivilege privilege, String username, String password, String name) {
    this.privilege = privilege;
    this.username = username;
    this.password = password;
    this.name = name;
  }

  /**
   * Create super manager.
   *
   * @param username the username
   * @param password the password
   * @param name     the name
   * @return the manager
   * @author [류성재]
   * @implNote 수퍼 관리자 생성 메서드
   * @since 2021. 3. 16. 오후 2:14:57
   */
  public static Manager createSuper(String username, String password, String name) {
    return createEntity(ManagerPrivilege.SUPER, username, password, name);
  }

  /**
   * Create manager manager.
   *
   * @param username the username
   * @param password the password
   * @param name     the name
   * @return the manager
   * @author [류성재]
   * @implNote 일반 관리자 생성 메서드
   * @since 2021. 3. 16. 오후 2:14:57
   */
  public static Manager createManager(String username, String password, String name) {
    return createEntity(ManagerPrivilege.MANAGER, username, password, name);
  }

  /**
   * Create entity manager.
   *
   * @param privilege the privilege
   * @param username  the username
   * @param password  the password
   * @param name      the name
   * @return the manager
   * @author [류성재]
   * @implNote 생성 메서드
   * @since 2021. 3. 16. 오후 2:14:57
   */
  public static Manager createEntity(ManagerPrivilege privilege, String username, String password,
      String name) {
    return new Manager(privilege.getPrivilege(), username, password, name);
  }

  /**
   * Update self.
   *
   * @param name the name
   * @author [류성재]
   * @implNote 수정 메서드
   * @since 2021. 3. 16. 오후 2:14:57
   */
  public void updateSelf(String name) {
    this.name = name;
  }

  /**
   * Update entity.
   *
   * @param privilege the privilege
   * @param name      the name
   * @author [류성재]
   * @implNote 수정 메서드
   * @since 2021. 3. 16. 오후 2:14:57
   */
  public void updateEntity(ManagerPrivilege privilege, String name) {
    if (!isNull(privilege)) {
      this.privilege = privilege.getPrivilege();
    }

    this.name = name;
  }

  /**
   * Remove entity.
   *
   * @author [류성재]
   * @implNote 논리 삭제 메서드
   * @since 2021. 3. 16. 오후 2:14:57
   */
  public void removeEntity() {
    removeSign();
  }

}
