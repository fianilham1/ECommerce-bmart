package com.tes.buana.common.converter;

import com.tes.buana.common.util.MapperUtil;
import com.tes.buana.dto.role.RoleDto;
import com.tes.buana.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleConverter {
    public RoleDto toDto(Role role){
        return MapperUtil.parse(role, RoleDto.class);
    }

    public Role toEntity(RoleDto roleDto){
        return MapperUtil.parse(roleDto, Role.class);
    }
}
