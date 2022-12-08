package com.kts.taxify.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role_permissions")
    @Column(name = "permissions")
    private Collection<Permission> permissions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    @JsonBackReference
    private List<User> users;
}