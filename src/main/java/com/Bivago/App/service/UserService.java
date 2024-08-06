package com.Bivago.App.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Bivago.App.exceptions.ChangingUserNameLengthException;
import com.Bivago.App.exceptions.CpfExistsException;
import com.Bivago.App.exceptions.CriptoExistException;
import com.Bivago.App.exceptions.EmailExistsException;
//import com.Bivago.App.exceptions.PasswordConfirmedNotEqualsUserPassword;
import com.Bivago.App.exceptions.PasswordLengthException;
import com.Bivago.App.exceptions.ServicException;
import com.Bivago.App.models.UserModel;
import com.Bivago.App.repositories.UserRepository;
import com.Bivago.App.util.UtilPassword;

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
                throw new CpfExistsException("CPF já cadastrado.");
            }
            
            if (ur.findByUserEmail(user.getUserEmail()) != null) {
                throw new EmailExistsException("Email já cadastrado.");
            }

            if ( (user.getUserPassword()).length() < USERPASSWORDMINIMUMLENGTH || (user.getUserPassword()).length() > USERPASSWORDMAXIMUMLENGTH ) {
                throw new PasswordLengthException("Senha deve conter entre 8 a 32 caracteres.");
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
    
    public UserModel changeUserName(UserModel user, String newUserName) throws ChangingUserNameLengthException {
        
        if ( newUserName.length() < USERNAMEMINIMUMLENGTH || newUserName.length() > USERNAMEMAXIMUMLENGTH ) {
            throw new ChangingUserNameLengthException("Novo nome de Usuário também deve conter entre 3 a 120 caracteres.");
        }

        user.setUserName(newUserName);
        ur.save(user);        

        return user;
    }

    public UserModel changeUserPassword(UserModel user, String newUserPassword) throws PasswordLengthException, NoSuchAlgorithmException {
        
        if ( newUserPassword.length() < USERPASSWORDMINIMUMLENGTH || newUserPassword.length() > USERPASSWORDMAXIMUMLENGTH ) {
            throw new PasswordLengthException("Nova Senha deve conter entre 8 a 32 caracteres.");
        }

        user.setUserPassword(UtilPassword.md5(newUserPassword));
        ur.save(user);

        return user;
    }

}
