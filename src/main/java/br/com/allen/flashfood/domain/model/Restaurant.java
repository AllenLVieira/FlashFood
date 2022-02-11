package br.com.allen.flashfood.domain.model;

import br.com.allen.flashfood.api.validationgroups.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(groups = Groups.RestaurantRegistrationGroup.class)
    private String name;

    @PositiveOrZero(groups = Groups.RestaurantRegistrationGroup.class)
    @Column(nullable = false)
    private BigDecimal freightRate;

    @Embedded
    @JsonIgnore
    private Address address;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    @JsonIgnore
    private LocalDateTime registrationDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    @JsonIgnore
    private LocalDateTime updateDate;

    @Valid
    @NotNull(groups = Groups.RestaurantRegistrationGroup.class)
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cuisine cuisine;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    @JsonIgnore
    private List<PaymentMethod> paymentMethod = new ArrayList<>();
}
