package org.algaworks.algafood.domain.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "tb_restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private BigDecimal fee = BigDecimal.ZERO;

    @Column(name = "is_active")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "is_open")
    private Boolean isOpen = Boolean.FALSE;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "registration_date")
    private OffsetDateTime registrationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private OffsetDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "kitchen_id")
    private Kitchen kitchen;

    @Embedded
    private Address address;

    @ManyToMany
    @JoinTable(name = "tb_restaurant_responsible",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "responsible_id"))
    private Set<User> responsible = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private Set<PaymentMethod> paymentMethods = new LinkedHashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<Product> products;

    public void activate() {
        this.isActive = true;
    }

    public void inactivate() {
        this.isActive = false;
    }

    public void opening() {
        this.isOpen = true;
    }

    public void closing() {
        this.isOpen = false;
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.add(paymentMethod);
    }

    public void removePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.remove(paymentMethod);
    }

    public void addResponsible(User responsible) {
        this.responsible.add(responsible);
    }

    public void removeResponsible(User responsible) {
        this.responsible.remove(responsible);
    }
}
