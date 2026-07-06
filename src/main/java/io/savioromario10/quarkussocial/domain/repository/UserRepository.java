package io.savioromario10.quarkussocial.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.savioromario10.quarkussocial.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}