package br.com.allen.flashfood.domain.model;

import br.com.allen.flashfood.api.validationgroups.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Valid
    @ConvertGroup(from = Default.class, to = Groups.StateId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private State state;
}
