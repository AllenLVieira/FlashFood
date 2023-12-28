package br.com.allen.flashfood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal freightRate;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime registrationDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updateDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cuisine cuisine;

    private Boolean active = Boolean.TRUE;

    private Boolean openStatus = Boolean.FALSE;

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private Set<PaymentMethod> paymentMethod = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurant_user_manager",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> managers = new HashSet<>();

    public void activate() {
        setActive(true);
    }

    public void disable() {
        setActive(false);
    }

    public void openRestaurant() {
        setOpenStatus(true);
    }

    public void closeRestaurant() {
        setOpenStatus(false);
    }

    public void removePaymentMethod(PaymentMethod paymentMethod) {
        getPaymentMethod().remove(paymentMethod);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        getPaymentMethod().add(paymentMethod);
    }

    public boolean addNewManager(User user) {
        return getManagers().add(user);
    }

    public boolean removeManager(User user) {
        return getManagers().remove(user);
    }

    public boolean acceptPaymentMethod(PaymentMethod paymentMethod) {
        return getPaymentMethod().contains(paymentMethod);
    }

    public boolean doesNotAcceptPaymentMethod(PaymentMethod paymentMethod) {
        return !acceptPaymentMethod(paymentMethod);
    }
}
