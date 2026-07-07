package io.savioromario10.quarkussocial.rest.dto;

import io.savioromario10.quarkussocial.domain.model.Follower;
import lombok.Data;

@Data
public class FollowerResponse {

  private Long id;
  private String name;

  public FollowerResponse(){}
  
  public FollowerResponse(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public FollowerResponse(Follower follower){
    this(follower.getFollower().getId(), follower.getFollower().getName());
  }

}