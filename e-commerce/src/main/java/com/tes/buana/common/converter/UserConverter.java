package com.tes.buana.common.converter;

import com.tes.buana.common.util.MapperUtil;
import com.tes.buana.dto.user.UserDto;
import com.tes.buana.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {
    public UserDto toDto(Users users, String token){
        return UserDto.builder()
                .name(users.getName())
                .username(users.getUsername())
                .role(users.getRole().getName())
                .mobilePhone(users.getMobilePhone())
                .token(token)
                .build();
    }

    public UserDto toDto(Users users){
        return UserDto.builder()
                .name(users.getName())
                .username(users.getUsername())
                .role(users.getRole().getName())
                .mobilePhone(users.getMobilePhone())
                .build();
    }

    public Users toEntity(Object userDto){
        return MapperUtil.parse(userDto, Users.class);
    }
}
