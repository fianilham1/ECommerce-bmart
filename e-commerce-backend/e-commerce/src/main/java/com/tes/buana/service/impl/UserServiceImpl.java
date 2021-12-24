package com.tes.buana.service.impl;

import com.tes.buana.common.constant.CommonMessage;
import com.tes.buana.common.util.HashSaltPasswordUtil;
import com.tes.buana.common.util.RegexUtil;
import com.tes.buana.entity.Cart;
import com.tes.buana.entity.Users;
import com.tes.buana.repository.CartRepository;
import com.tes.buana.repository.RoleRepository;
import com.tes.buana.repository.UsersRepository;
import com.tes.buana.service.UserService;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HashSaltPasswordUtil hashSaltPasswordUtil;

    @SneakyThrows
    @Transactional
    @Override
    public Users create(Users users){
        boolean emailTrue = RegexUtil.isValidEmail(users.getUsername());
        boolean passwordTrue = RegexUtil.isValidPassword(users.getPassword());

        if(!emailTrue) throw new Exception(CommonMessage.USERNAME_INVALID_FORMAT);
        if(!passwordTrue) throw new Exception(CommonMessage.PASSWORD_INVALID_FORMAT);

        Optional<Users> existedUser = usersRepository.findByUsernameAndMarkForDeleteIsFalse(users.getUsername());
        if (existedUser.isPresent()){
            throw new Exception(CommonMessage.USERNAME_UNAVAILABLE);
        }
        String encryptedPassword = hashSaltPasswordUtil.generateStrongPasswordHash(users.getPassword());
        users.setPassword(encryptedPassword);
        usersRepository.save(users);
        cartRepository.save(Cart.builder().users(users).build());
        return users;
    }

    @SneakyThrows
    @Transactional
    @Override
    public Users update(Users users){
        boolean passwordTrue = RegexUtil.isValidPassword(users.getPassword());

        if(!passwordTrue) throw new Exception(CommonMessage.PASSWORD_INVALID_FORMAT);

        Users existedUser = usersRepository.findByUsernameAndMarkForDeleteIsFalse(users.getUsername())
                .orElseThrow(()->new Exception(CommonMessage.USERNAME_NOT_FOUND));

        String encryptedPassword = hashSaltPasswordUtil.generateStrongPasswordHash(users.getPassword());
        existedUser.setPassword(encryptedPassword);
        existedUser.setName(users.getName());
        existedUser.setImage(users.getImage());
        existedUser.setMobilePhone(users.getMobilePhone());
        usersRepository.save(existedUser);
        return users;
    }

    @SneakyThrows
    @Transactional
    @Override
    public Users login(Users users){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        users.getUsername(),
                        users.getPassword())
        );
         return getUser(users);
    }

    @SneakyThrows
    @Transactional
    @Override
    public Users loginGoogle(Users users){
        Optional<Users> existingUsers = usersRepository.findByUsernameAndMarkForDeleteIsFalse(users.getUsername());
        if(existingUsers.isPresent()){
            existingUsers.get().setUsername(users.getUsername());
            existingUsers.get().setName(users.getName());
            existingUsers.get().setImage(users.getImage());
            usersRepository.save(existingUsers.get());
            return existingUsers.get();
        }else{
            String randomGooglePassword = UUID.randomUUID().toString();
            String encryptedPassword = hashSaltPasswordUtil.generateStrongPasswordHash(randomGooglePassword);
            users.setPassword(encryptedPassword);
            usersRepository.save(users);
            cartRepository.save(Cart.builder().users(users).build());
            return users;
        }
    }

    @SneakyThrows
    @Transactional
    @Override
    public String forgotPassword(String username){
        Users users = getUser(Users.builder().name(username).build());
        return users.getUsername();
    }

    @SneakyThrows
    @Transactional
    @Override
    public void resetPassword(Users users) {
        Users existingUsers = getUser(users);
        existingUsers.setPassword(users.getPassword());
        usersRepository.save(existingUsers);
    }

    @SneakyThrows
    @Transactional
    @Override
    public Users getUser(Users users) {
        return usersRepository.findByUsernameAndMarkForDeleteIsFalse(users.getUsername()).orElseThrow(() -> new NotFoundException(CommonMessage.USERNAME_NOT_FOUND));
    }

    @SneakyThrows
    @Transactional
    @Override
    public Users getUser(String username) {
        return usersRepository.findByUsernameAndMarkForDeleteIsFalse(username).orElseThrow(() -> new NotFoundException(CommonMessage.USERNAME_NOT_FOUND));
    }


}
