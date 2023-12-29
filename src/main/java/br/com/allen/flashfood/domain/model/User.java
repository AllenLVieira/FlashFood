package br.com.allen.flashfood.domain.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "`user`")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @CreationTimestamp
  @Column(nullable = false, columnDefinition = "datetime")
  private OffsetDateTime registrationDate;

  @ManyToMany
  @JoinTable(
      name = "user_group",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<Family> groups = new HashSet<>();

  public boolean passwordConfirmed(String password) {
    return getPassword().equals(password);
  }

  public boolean passwordNotConfirmed(String password) {
    return !passwordConfirmed(password);
  }

  public boolean addGroup(Family group) {
    return getGroups().add(group);
  }

  public boolean removeGroup(Family group) {
    return getGroups().remove(group);
  }
}
