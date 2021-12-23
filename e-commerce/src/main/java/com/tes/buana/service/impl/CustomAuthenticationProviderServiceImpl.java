package com.tes.buana.service.impl;

import com.tes.buana.common.exception.NotFoundException;
import com.tes.buana.common.util.HashSaltPasswordUtil;
import com.tes.buana.entity.Users;
import com.tes.buana.repository.UsersRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomAuthenticationProviderServiceImpl implements AuthenticationProvider {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private HashSaltPasswordUtil hashSaltPasswordUtil;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = null;

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Users users = usersRepository.findByUsernameAndMarkForDeleteIsFalse(username).orElseThrow(()->new NotFoundException("user is not found"));
        try{
            if (username.equals(users.getUsername()) && hashSaltPasswordUtil.validatePassword(password, users.getPassword())){
                Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(users);
                authenticationToken = new UsernamePasswordAuthenticationToken(
                        new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities),password,grantedAuthorities);
            }
        }catch (Exception ex){
            System.out.println("error authenticate : "+ex);
        }
        return authenticationToken;
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(Users users){
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(users.getRole().getName().equals("admin")){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

