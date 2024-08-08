package com.Bivago.App.models;

import java.io.Serializable;
import java.sql.Date;
//import java.util.HashSet;
//import java.util.Collection;
import java.util.List;
//import java.util.Set;
import java.util.UUID;

import com.Bivago.App.enums.ERole;

//import com.Bivago.App.dto.UserDTO;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user")
//public class UserModel implements UserDetails {
public class UserModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @NotEmpty
    @Size(min = 3, max = 120, message = "Usuário deve conter entre 3 e 120 caracteres.")
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
    //@Size(min = 8, max = 32, message = "Senha deve conter entre 8 e 32 caracteres.")
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

    @OneToMany(mappedBy = "userReservation", cascade = CascadeType.REMOVE)
    @Column(name = "USERRESERVATIONS")
    private List<ReservaModel>reservas;

    @NotNull
    @Column(name = "USERROLE")
    private ERole userRole;    

    // Construtores:
    public UserModel() {
        Date currentDate = new Date(System.currentTimeMillis());
        this.userCreatedDate = currentDate;
        this.userUpdatedDate = currentDate;
        this.userRole = ERole.ROLE_USER;               
    }

    // public UserModel(UserDTO data) {
    //     this.userRole = data.userRole();
    // }
    
    // GetterSetters:

    public UUID getId() {
        return id;
    }

    public void setCode(UUID id) {
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

    // public UserRole getUserRole() {
    //     return userRole;
    // }

    // public void setUserRole(UserRole userRole) {
    //     this.userRole = userRole;
    // }

    public List<ReservaModel> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaModel> reservas) {
        this.reservas = reservas;
    } 
 
    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {        
    //     if (this.userRole == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_HOTEL"), new SimpleGrantedAuthority("ROLE_USER"));
    //     if (this.userRole == UserRole.HOTEL) return List.of(new SimpleGrantedAuthority("ROLE_HOTEL"), new SimpleGrantedAuthority("ROLE_USER"));
    //     return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    // }

    // @Override
    // public String getPassword() {        
    //     throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    // }

    // @Override
    // public String getUsername() {        
    //     throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    // }        

}
