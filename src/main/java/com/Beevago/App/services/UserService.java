package com.Beevago.App.services;

import java.util.List;
import java.util.UUID;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Beevago.App.enums.ERole;
import com.Beevago.App.exceptions.AttributeExistsException;
import com.Beevago.App.exceptions.CPFInvalidException;
import com.Beevago.App.exceptions.CriptoExistException;
import com.Beevago.App.exceptions.LengthException;
import com.Beevago.App.exceptions.MinimumAgeException;
import com.Beevago.App.exceptions.NewPasswordEqualsException;
import com.Beevago.App.exceptions.PasswordConfirmationException;
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
    private static final int MINIMUMAGE = 18;

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
            if ( !(user.getUserPassword()).equals(user.getUserConfirmedPassword()) ) {                
                throw new PasswordConfirmationException("A Confirmação da Senha deve ser igual à Senha.");
            }

            if (user.getUserBirthday().after(minimumDate())) {
                throw new MinimumAgeException("Deve ter pelo menos 18 anos para criar uma conta.");
            }

            if (!(isCPFValid(user.getUserCpf()))) {
                throw new CPFInvalidException("CPF deve ser válido.");
            }

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
    
    public void changeUserName(UUID userId, String newUserName) throws LengthException {
        
        if ( newUserName.length() < USERNAMEMINIMUMLENGTH || newUserName.length() > USERNAMEMAXIMUMLENGTH ) {
            throw new LengthException("Novo nome de Usuário também deve conter entre 3 a 120 caracteres.");
        }

        UserModel user = findUserById(userId);
        user.setUserName(newUserName);
        user.setUserUpdatedDate(new Date(System.currentTimeMillis()));
        ur.save(user);

    }

    public void changeUserPassword(UUID userId, String newUserPassword) throws LengthException, NoSuchAlgorithmException, NewPasswordEqualsException {
        
        if ( newUserPassword.length() < USERPASSWORDMINIMUMLENGTH || newUserPassword.length() > USERPASSWORDMAXIMUMLENGTH ) {
            throw new LengthException("Nova Senha deve conter entre 8 a 32 caracteres.");
        }

        if ( (UtilPassword.md5(newUserPassword)).equals(findPasswordById(userId)) ) {
            System.out.println("Senha Igual a Anterior");
            throw new NewPasswordEqualsException("Senha igual a anterior.");
        }

        UserModel user = findUserById(userId);
        user.setUserPassword(UtilPassword.md5(newUserPassword));
        user.setUserUpdatedDate(new Date(System.currentTimeMillis()));
        ur.save(user);

    }

    public Date minimumDate() {

        LocalDateTime ldate = LocalDateTime.now().minusYears(MINIMUMAGE);
        java.util.Date date = new java.util.Date();
        date = Date.from(ldate.atZone(ZoneId.systemDefault()).toInstant());
        return new java.sql.Date(date.getTime());

    }

    public boolean isCPFValid(String cpf) {
        return true;
    }

    public List<UserModel> findAllUsers() {
        return ur.findAll();
    }

    public UserModel findUserById(UUID id) {
        return ur.findUserById(id);
    }

    public ERole findRoleById(UUID id) {
        return ERole.values()[ur.findRoleById(id)];
    }

    public String findEmailById(UUID id) {
        return ur.findEmailById(id);
    }

    public String findPasswordById(UUID id) {
        return ur.findPasswordById(id);
    }

    public void deleteUserById(UUID id) {
        ur.deleteByUserId(id);
    }

}
