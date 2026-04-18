package com.bookmyshow.entity;

import com.bookmyshow.enums.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "roles") public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private UserRole name;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public UserRole getName() { return this.name; }

    public void setId(Long id) { this.id = id; }
    public void setName(UserRole name) { this.name = name; }

    public Role() {}

    public Role(Long id, UserRole name) {
        this.id = id;
        this.name = name;
    }

    public static  RoleBuilder builder() { return new RoleBuilder(); }

    public static class RoleBuilder {
        private Long id;
        private UserRole name;

        public RoleBuilder id(Long id) { this.id = id; return this; }
        public RoleBuilder name(UserRole name) { this.name = name; return this; }

        public Role build() { return new Role(id, name); }
    }

}
