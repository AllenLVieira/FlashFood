package br.com.allen.flashfood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;

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
