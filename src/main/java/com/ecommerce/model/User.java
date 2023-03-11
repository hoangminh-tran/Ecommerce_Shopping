package com.ecommerce.model;

import com.ecommerce.repository.UserRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "Email"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    private Long User_id;

    @Size(min = 3, max = 10, message = "Invalid First Name!(3 - 10 characters)")
    private String firstName;

    @Size(min = 3, max = 10, message = "Invalid Last Name!(3 - 10 characters)")
    private String lastName;

    @Size(min = 5, max = 20, message = "Invalid Password(5-20 characters)!!!!!")
    private String password;

    private String confirmPassword;

    private String  email;

    private String phone;

    private boolean AccountStatus;

    public User(String firstName, String lastName, String password, String confirmPassword, String email, String phone, boolean accountStatus, Roles roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.phone = phone;
        AccountStatus = accountStatus;
        this.roles = roles;
    }

    public User(String firstName, String lastName, String password, String confirmPassword, String email, String phone, Roles roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.phone = phone;
        AccountStatus = true;
        this.roles = roles;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Roles roles;

    @OneToMany(mappedBy = "user")
    private List<Orders> ordersList;
}
