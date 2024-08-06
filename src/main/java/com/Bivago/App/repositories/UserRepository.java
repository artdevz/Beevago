package com.Bivago.App.repositories;

//import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

//import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

//import org.springframework.data.repository.CrudRepository;
import com.Bivago.App.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    
    public UserModel findById(UUID id);
    public UserModel findByUserName(String userName);
    public UserModel findByUserCpf(String userCpf);
    //public UserModel findByUserEmail(String userEmail);

    UserDetails findByUserEmail(String userEmail);

    //Optional<User> findByUserEmail(String userEmail);

    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE USEREMAIL = :userEmail AND USERPASSWORD = :userPassword")
    public UserModel findLogin(String userEmail, String userPassword);    

}
