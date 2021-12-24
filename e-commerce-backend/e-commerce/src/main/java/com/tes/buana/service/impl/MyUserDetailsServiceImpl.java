package com.tes.buana.service.impl;

import com.tes.buana.common.exception.NotFoundException;
import com.tes.buana.entity.Users;
import com.tes.buana.repository.UsersRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUsernameAndMarkForDeleteIsFalse(username).orElseThrow(()->new NotFoundException("user is not found"));
        Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(users);
        return new User(users.getUsername(), users.getPassword(), grantedAuthorities);
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(Users users){
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(users.getRole().getName().equals("admin")){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        return grantedAuthorities;
    }
}

