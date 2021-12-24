package com.tes.buana.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGoogleDto {
    private String email;
    private String name;
    private String picture;
    private String locale;
}
