package com.glos.api.authservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.glos.api.authservice.validation.OnCreate;
import com.glos.api.authservice.validation.OnUpdate;
import com.glos.api.authservice.validation.PasswordConfirm;
import com.glos.api.authservice.validation.UserUniqueField;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@PasswordConfirm(groups = OnCreate.class)
public class SignUpRequest {

    @NotBlank(message = "Username can not be empty.",
            groups = OnCreate.class)
    @Length(max = 20,
            message = "Username must be 20 characters or less.",
            groups = OnCreate.class)
    @UserUniqueField(name = "username", message = "Username is already taken",
                     groups = OnCreate.class)
    @Pattern(regexp = "^[A-Za-z0-9]{5,}$", message = "Invalid format")
    private String username;

    @NotBlank(message = "Password can not be empty.", groups = OnCreate.class)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!?@#$%^&*(),\\.<>\\[\\]{}\"'|\\\\:;`~+\\-*\\/]).{8,}$",
            message = "Invalid password format.",
            groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Password confirm can not be null.",
            groups = OnCreate.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

    @NotBlank(message = "Email can not be null.",
            groups = OnCreate.class)
    @Email(message = "Incorrect email.",
            groups = OnCreate.class)
    @Length(max = 50,
            message = "Email must be 50 characters or less.",
            groups = {OnCreate.class, OnUpdate.class})
    @UserUniqueField(name = "email", message = "Email is already taken",
                    groups = OnCreate.class)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Invalid format")
    private String email;

    @NotBlank(message = "Phone number can not be null.",
            groups = OnCreate.class)
    @Pattern(
            regexp = "(\\+\\d{1,4}[-.\\s]?)(\\(\\d{1,}\\)[-\\s]?|\\d{1,}[-.\\s]?){1,}[0-9\\s]",
            message = "Incorrect phone number."
    )
    @Length(max = 15,
            message = "Incorrect phone number.",
            groups = {OnCreate.class, OnUpdate.class})
    @UserUniqueField(name = "phoneNumber",
                    message = "Phone number is already taken",
            groups = OnCreate.class)
    private String phoneNumber;

    @NotBlank(message = "Firstname can not be null.",
            groups = OnCreate.class)
    @Length(max = 20,
            message = "Firstname must be 20 characters or less.",
            groups = {OnCreate.class, OnUpdate.class})
    private String gender;

    @NotBlank(message = "Firstname can not be null.",
            groups = OnCreate.class)
    @Length(max = 20,
            message = "Firstname must be 20 characters or less.",
            groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "^[A-Za-z]{2,}$",
            message = "Invalid format")
    private String firstName;

    @NotBlank(message = "Lastname can not be null.",
            groups = OnCreate.class)
    @Length(max = 20,
            message = "Lastname must be 20 characters or less.",
            groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "^[A-Za-z]{2,}$",
            message = "Invalid format")
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    public SignUpRequest() {}

    public SignUpRequest(String username, String password, String confirmPassword, String email, String phoneNumber, String gender, String firstName, String lastName, LocalDate birthdate) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignUpRequest that = (SignUpRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(confirmPassword, that.confirmPassword) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(gender, that.gender) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, confirmPassword, email, phoneNumber, gender, firstName, lastName, birthdate);
    }
}
