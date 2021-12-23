package com.tes.buana.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    String username;
    String password;
    String roleId;
    String name;
    String mobilePhone;
    String image;
}
