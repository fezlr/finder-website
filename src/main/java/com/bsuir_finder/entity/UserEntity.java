package com.bsuir_finder.entity;

import com.bsuir_finder.dto.enums.Role;
import com.bsuir_finder.dto.enums.UserStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "bsuir_finder")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private Long email;

    @Column(name = "password")
    private Long password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "createdAt")
    private LocalDate createdAt;

    @Column(name = "userStatus")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public UserEntity(Long id, Long email, Long password, Role role, LocalDate createdAt, UserStatus userStatus) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.userStatus = userStatus;
    }

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public Long getEmail() {
        return email;
    }

    public Long getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(Long email) {
        this.email = email;
    }

    public void setPassword(Long password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && role == that.role && Objects.equals(createdAt, that.createdAt) && userStatus == that.userStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role, createdAt, userStatus);
    }
}