package com.bsuir_finder.entity;

import com.bsuir_finder.dto.enums.FormStatus;
import com.bsuir_finder.dto.enums.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "profiles")
@Entity
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "profile")
    private UserEntity user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "city")
    private String city;

    @Column(length = 1000, name = "about_me")
    private String aboutMe;

    @Column(name = "main_photo_url")
    private String mainPhotoUrl;

    @Column(name = "telegram_username")
    private String telegramUsername;

    @Column(name = "instagram_username")
    private String instagramUsername;

    @Column(name = "form_status")
    @Enumerated(EnumType.STRING)
    private FormStatus formStatus;

    @Column(name = "is_visible")
    private boolean isVisible = false;

    public ProfileEntity() {
    }

    public ProfileEntity(Long id, UserEntity user, String firstName, String lastName, LocalDate birthDate, Gender gender, String city, String aboutMe, String mainPhotoUrl, String telegramUsername, String instagramUsername, FormStatus formStatus, boolean isVisible) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.city = city;
        this.aboutMe = aboutMe;
        this.mainPhotoUrl = mainPhotoUrl;
        this.telegramUsername = telegramUsername;
        this.instagramUsername = instagramUsername;
        this.formStatus = formStatus;
        this.isVisible = isVisible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public void setTelegramUsername(String telegramUsername) {
        this.telegramUsername = telegramUsername;
    }

    public String getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public FormStatus getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatus formStatus) {
        this.formStatus = formStatus;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProfileEntity that = (ProfileEntity) o;
        return isVisible == that.isVisible && Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthDate, that.birthDate) && gender == that.gender && Objects.equals(city, that.city) && Objects.equals(aboutMe, that.aboutMe) && Objects.equals(mainPhotoUrl, that.mainPhotoUrl) && Objects.equals(telegramUsername, that.telegramUsername) && Objects.equals(instagramUsername, that.instagramUsername) && formStatus == that.formStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, firstName, lastName, birthDate, gender, city, aboutMe, mainPhotoUrl, telegramUsername, instagramUsername, formStatus, isVisible);
    }
}