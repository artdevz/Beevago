package com.Beevago.App.repositories;

//import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.Beevago.App.models.UserModel;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    
    public UserModel findById(UUID id);
    public UserModel findByUserName(String userName);
    public UserModel findByUserCpf(String userCpf);
    //public UserModel findByUserEmail(String userEmail);

    UserDetails findByUserEmail(String userEmail);

    //Optional<User> findByUserEmail(String userEmail);

    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE USEREMAIL = :userEmail AND USERPASSWORD = :userPassword")
    public UserModel findLogin(String userEmail, String userPassword);    

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM user u WHERE u.ID = :id")
    public void deleteByUserId(UUID id);

}
