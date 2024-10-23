package org.algaworks.algafood.domain.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "tb_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(name = "tb_group_permission",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    public void attachPermission(Permission permission){
        this.permissions.add(permission);
    }

    public void detachPermission(Permission permission){
        this.permissions.remove(permission);
    }
}
