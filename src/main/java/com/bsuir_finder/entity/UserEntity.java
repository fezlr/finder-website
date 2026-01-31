package com.bsuir_finder.entity;

import com.bsuir_finder.dto.enums.Role;
import com.bsuir_finder.dto.enums.UserStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "username",
            nullable = false
    )
    private String username;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "role",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(
            name = "enabled"
    )
    private boolean enabled;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDate createdAt;

    @Column(name = "userStatus")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    public UserEntity() {
    }

    public UserEntity(
            Long id,
            String email,
            String username,
            String password,
            Role role,
            LocalDate createdAt,
            boolean enabled,
            UserStatus userStatus,
            ProfileEntity profile
    ) {
        this.profile = profile;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.role = role;
        this.password = password;
        this.username = username;
        this.email = email;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return enabled == that.enabled && Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && role == that.role && Objects.equals(createdAt, that.createdAt) && userStatus == that.userStatus && Objects.equals(profile, that.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, role, enabled, createdAt, userStatus, profile);
    }
}