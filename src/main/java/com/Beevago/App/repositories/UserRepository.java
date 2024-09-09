package com.Beevago.App.repositories;

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
    UserDetails findByUserEmail(String userEmail);

    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE USEREMAIL = :userEmail AND USERPASSWORD = :userPassword")
    public UserModel findLogin(String userEmail, String userPassword);    

    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE ID = :id")
    public UserModel findUserById(UUID id);

    @Query(nativeQuery = true, value = "SELECT USERROLE FROM user WHERE ID = :id")
    public Byte findRoleById(UUID id);

    @Query(nativeQuery = true, value = "SELECT USEREMAIL FROM user WHERE ID = :id")
    public String findEmailById(UUID id);

    @Query(nativeQuery = true, value = "SELECT USERPASSWORD FROM user WHERE ID = :id")
    public String findPasswordById(UUID id);
    
    @Query(nativeQuery = true, value = "SELECT ID FROM user WHERE USEREMAIL = :userEmail")
    public UUID findIdByEmail(String userEmail);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM user u WHERE u.ID = :id")
    public void deleteByUserId(UUID id);

}
