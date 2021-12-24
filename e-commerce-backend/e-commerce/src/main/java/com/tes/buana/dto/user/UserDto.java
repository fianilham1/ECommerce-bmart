package com.tes.buana.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    String name;
    String username;
    String role;
    String roleId;
    String mobilePhone;
    String token;
    String image;

}
