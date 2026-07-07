package io.savioromario10.quarkussocial.domain.repository;

import java.util.List;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.savioromario10.quarkussocial.domain.model.Follower;
import io.savioromario10.quarkussocial.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

  public boolean follows(User follower, User user) {
    var params = Parameters.with("follower", follower).and("user", user);

    PanacheQuery<Follower> query = find("follower = :follower and user = :user", params);

    Optional<Follower> followerOptional = query.firstResultOptional();

    return followerOptional.isPresent();
  }

  public List<Follower> findFollowers(Long userId){
    
    PanacheQuery<Follower> query = find("user.id", userId);

    return query.list();
  }

  public void deleteByFollowerAndUser(Long followerId, Long userId){
    var params = Parameters
      .with("userId", userId)
      .and("followerId", followerId)
      .map();

    delete("follower.id = :followerId and user = :userId", params);
  }
}