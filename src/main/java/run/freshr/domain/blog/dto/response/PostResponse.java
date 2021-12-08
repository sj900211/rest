package run.freshr.domain.blog.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extension.ResponseAuditExtension;
import run.freshr.domain.mapping.dto.response.PostHashtagMappingForPostResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse extends ResponseAuditExtension<Long> {

  private String title;

  private String contents;

  private Boolean managerGrant;

  private Boolean leaderGrant;

  private Boolean coachGrant;

  private Boolean userGrant;

  private Boolean anonymousGrant;

  private Integer hits;

  private List<PostHashtagMappingForPostResponse> hashtagList;

  public void setPermission(List<Boolean> permissionList) {
    this.managerGrant = permissionList.get(0);
    this.leaderGrant = permissionList.get(1);
    this.coachGrant = permissionList.get(2);
    this.userGrant = permissionList.get(3);
    this.anonymousGrant = permissionList.get(4);
  }

}
