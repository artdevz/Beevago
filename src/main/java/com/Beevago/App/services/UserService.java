package com.Beevago.App.services;

import java.util.List;
import java.util.UUID;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Beevago.App.exceptions.AttributeExistsException;
import com.Beevago.App.exceptions.CriptoExistException;
import com.Beevago.App.exceptions.LengthException;
import com.Beevago.App.exceptions.ServicException;
import com.Beevago.App.models.UserModel;
import com.Beevago.App.repositories.UserRepository;
import com.Beevago.App.utils.UtilPassword;

@Service
public class UserService {
    
    private static final int USERPASSWORDMINIMUMLENGTH = 8;
    private static final int USERPASSWORDMAXIMUMLENGTH = 32;
    private static final int USERNAMEMINIMUMLENGTH = 3;
    private static final int USERNAMEMAXIMUMLENGTH = 120;

    @Autowired
    private UserRepository ur;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    public void saveUser(UserModel user) throws Exception {
        
        try {

            if (ur.findByUserCpf(user.getUserCpf()) != null) {
                throw new AttributeExistsException("CPF já cadastrado.");
            }
            
            if (ur.findByUserEmail(user.getUserEmail()) != null) {
                throw new AttributeExistsException("Email já cadastrado.");
            }

            if ( (user.getUserPassword()).length() < USERPASSWORDMINIMUMLENGTH || (user.getUserPassword()).length() > USERPASSWORDMAXIMUMLENGTH ) {
                throw new LengthException("Senha deve conter entre 8 a 32 caracteres.");
            }

            // if ( (user.getUserPassword()) != (user.getUserConfirmedPassword()) ) {                
            //     throw new PasswordConfirmedNotEqualsUserPassword("A Confirmação da Senha deve ser igual à Senha.");
            // }

            user.setUserPassword(UtilPassword.md5(user.getUserPassword()));

        } catch (NoSuchAlgorithmException e) {

            throw new CriptoExistException("Erro na Criptografia da Senha.");
        
        }
        
        ur.save(user);

    }

    public UserModel loginUser(String userEmail, String userPassword) throws ServicException {
        UserModel userLogin = ur.findLogin(userEmail, userPassword);
        return userLogin;
    }    
    
    public UserModel changeUserName(UserModel user, String newUserName) throws LengthException {
        
        if ( newUserName.length() < USERNAMEMINIMUMLENGTH || newUserName.length() > USERNAMEMAXIMUMLENGTH ) {
            throw new LengthException("Novo nome de Usuário também deve conter entre 3 a 120 caracteres.");
        }

        user.setUserName(newUserName);
        ur.save(user);        

        return user;
    }

    public UserModel changeUserPassword(UserModel user, String newUserPassword) throws LengthException, NoSuchAlgorithmException {
        
        if ( newUserPassword.length() < USERPASSWORDMINIMUMLENGTH || newUserPassword.length() > USERPASSWORDMAXIMUMLENGTH ) {
            throw new LengthException("Nova Senha deve conter entre 8 a 32 caracteres.");
        }

        user.setUserPassword(UtilPassword.md5(newUserPassword));
        ur.save(user);

        return user;
    }

    public List<UserModel> findAllUsers() {
        return ur.findAll();
    }

    public void deleteUserById(UUID id) {
        ur.deleteByUserId(id);
    }

}
