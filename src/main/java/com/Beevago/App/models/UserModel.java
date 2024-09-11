package com.Beevago.App.models;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Beevago.App.enums.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user")
public class UserModel implements UserDetails {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @NotEmpty
    @Size(min = 3, max = 32, message = "Usuário deve conter entre 3 e 120 caracteres.")
    @Column(name = "USERNAME")
    private String userName;

    @NotEmpty
    @Column(name = "USERCPF", unique = true)
    private String userCpf;

    @NotEmpty
    @Column(name = "USEREMAIL", unique = true, nullable = false)
    @Email
    private String userEmail;

    @NotEmpty
    @Column(name = "USERPASSWORD")
    private String userPassword;
    
    @Transient
    private String userConfirmedPassword;

    @NotNull
    @Column(name = "USERBIRTHDAY")
    private Date userBirthday;

    @NotNull
    @Column(name = "USERCREATEDDATE")
    private Date userCreatedDate;
    
    @Column(name = "USERUPDATEDDATE")
    private Date userUpdatedDate;

    @Enumerated
    @NotNull
    @Column(name = "USERROLE")
    private ERole userRole;    

    // Construtores:
    
    public UserModel() {
        Date currentDate = new Date(System.currentTimeMillis());
        this.userCreatedDate = currentDate;
        this.userUpdatedDate = currentDate;
        if (this.userRole == null) this.userRole = ERole.ROLE_USER; // (Temporário)               
    }

    public UserModel(String name, String email, String cpf, Date birthday, String password, String confirmedpassword, ERole role) {
        this.userName = name;
        this.userEmail = email;
        this.userCpf = cpf;
        this.userBirthday = birthday;
        this.userPassword = password;
        this.userConfirmedPassword = confirmedpassword;
        this.userRole = role;        
        if (role == null) this.userRole = ERole.ROLE_USER;

        Date currentDate = new Date(System.currentTimeMillis());
        this.userCreatedDate = currentDate;
        this.userUpdatedDate = currentDate;
    }
    
    // GetterSetters:

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCpf() {
        return userCpf;
    }

    public void setUserCpf(String userCpf) {
        this.userCpf = userCpf;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserConfirmedPassword() {
        return userConfirmedPassword;
    }

    public void setUserConfirmedPassword(String userConfirmedPassword) {
        this.userConfirmedPassword = userConfirmedPassword;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public Date getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(Date userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public Date getUserUpdatedDate() {
        return userUpdatedDate;
    }

    public void setUserUpdatedDate(Date userUpdatedDate) {
        this.userUpdatedDate = userUpdatedDate;
    }

    public ERole getUserRole() {
        return userRole;
    }

    public void setUserRole(ERole userRole) {
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }   

}
