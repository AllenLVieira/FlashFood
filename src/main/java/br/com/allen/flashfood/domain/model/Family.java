package br.com.allen.flashfood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Family {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(
      name = "family_permission",
      joinColumns = @JoinColumn(name = "family_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions = new HashSet<>();

  public boolean removePermissions(Permission permission) {
    return this.getPermissions().remove(permission);
  }

  public boolean addPermissions(Permission permission) {
    return this.getPermissions().add(permission);
  }
}
