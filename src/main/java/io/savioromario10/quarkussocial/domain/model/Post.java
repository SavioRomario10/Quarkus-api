package io.savioromario10.quarkussocial.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "post_test", length = 150, nullable = false)
  private String text;
  @Column(name = "dateTime", nullable = false)
  private LocalDateTime dateTime;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @PrePersist
  public void prePersist(){
    setDateTime(LocalDateTime.now());
  }
}