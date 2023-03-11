package com.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Admin_id")
    private Long Admin_id;

    @Size(min = 3, max = 10, message = "Invalid First Name!(3 - 10 characters)")
    private String firstName;

    @Size(min = 3, max = 10, message = "Invalid Last Name!(3 - 10 characters)")
    private String lastName;

    @Size(min = 5, max = 20, message = "Invalid Password!(5 - 20 characters)")
    private String password;

    private String email;

    private String repeatPassword;

    private boolean AccountStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Roles roles;

    public Admin(String firstName, String lastName, String password, String email, String repeatPassword, boolean accountStatus, Roles roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.repeatPassword = repeatPassword;
        AccountStatus = accountStatus;
        this.roles = roles;
    }
}
