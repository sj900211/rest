package run.freshr.domain.auth.entity;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.isNull;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.domain.auth.enumeration.Privilege.USER;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import run.freshr.common.extension.EntityLogicalExtension;
import run.freshr.domain.auth.enumeration.Privilege;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.mapping.entity.AccountHashtagMapping;

@Slf4j
@Entity
@Table(
    name = "TB_AUTH_ACCOUNT",
    uniqueConstraints = @UniqueConstraint(name = "UK_ACCOUNT_USERNAME", columnNames = {"username"}),
    indexes = {
        @Index(name = "IDX_AUTH_ACCOUNT_PRIVILEGE", columnList = "privilege"),
        @Index(name = "IDX_AUTH_ACCOUNT_FLAG", columnList = "useFlag, delFlag")
    }
)
@SequenceGenerator(
    name="SEQUENCE_GENERATOR",
    sequenceName="SEQ_AUTH_ACCOUNT"
)
@TableComment(value = "권한 관리 > 계정 관리", extend = "EntityLogicalExtension")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Account extends EntityLogicalExtension {

  @Enumerated(STRING)
  @Column(nullable = false)
  @ColumnComment("계정 유형")
  protected Privilege privilege;

  @Column(nullable = false, length = 100)
  @ColumnComment("아이디")
  protected String username;

  @Column(nullable = false)
  @ColumnComment("비밀번호")
  protected String password;

  @ColumnComment("최근 접속 날짜 시간")
  protected LocalDateTime signDt;

  @ColumnComment("탈퇴 날짜")
  protected LocalDateTime removeDt;

  @Column(nullable = false)
  @ColumnComment("이름")
  private String name;

  @Column(length = 100)
  @ColumnComment("한 줄 소개")
  private String introduce;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "profile_id", foreignKey = @ForeignKey(name = "FK_ACCOUNT_ATTACH_PROFILE"))
  @ColumnComment("프로필 이미지 일련 번호")
  private Attach profile;

  @OneToMany(fetch = LAZY, mappedBy = "account")
  private List<AccountHashtagMapping> hashtagList;

  private Account(Privilege privilege, String username, String password, String name,
      String introduce, Attach profile, Boolean useFlag) {
    log.info("Account.Constructor");

    this.privilege = privilege;
    this.username = username;
    this.password = password;
    this.name = name;
    this.introduce = introduce;
    this.profile = profile;
    this.useFlag = useFlag;
  }

  public static Account createEntity(String username, String password, String name) {
    log.info("Account.createEntity");

    return new Account(USER, username, password, name, null, null, false);
  }

  public static Account createEntity(Privilege privilege, String username, String password,
      String name) {
    log.info("Account.createEntity");

    return new Account(privilege, username, password, name, null, null, true);
  }

  public void updateEntity(String name, String introduce, Attach profile) {
    log.info("Account.updateEntity");

    this.name = hasLength(name) ? name : this.name;
    this.introduce = introduce;
    this.profile = profile;
  }

  public void updateEntity(Privilege privilege, Boolean useFlag) {
    log.info("Account.updateEntity");

    this.privilege = !isNull(privilege) ? privilege : this.privilege;
    this.useFlag = !isNull(useFlag) ? useFlag : this.useFlag;
  }

  public void updatePassword(String password) {
    log.info("Account.updatePassword");

    this.password = password;
  }

  public void updateSignDt() {
    log.info("Account.updateSignDt");

    this.signDt = now();
  }

  public void removeEntity() {
    log.info("Account.removeEntity");

    this.username = this.username + "-" + now().format(ofPattern("yyyyMMddHHmmss"));
    this.removeDt = now();

    remove();
  }

}
