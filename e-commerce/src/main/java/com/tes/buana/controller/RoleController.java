package com.tes.buana.controller;

import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.tes.buana.common.converter.RoleConverter;
import com.tes.buana.common.exception.NotFoundException;
import com.tes.buana.dto.role.RoleDto;
import com.tes.buana.dto.role.RoleListDto;
import com.tes.buana.entity.Role;
import com.tes.buana.entity.web_query.RoleParamsQuery;
import com.tes.buana.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter converter;

    /**
     * ROLE_ADMIN -----------------
     * */

    @PostMapping
    public Response<RoleListDto> addRole(@RequestBody RoleListDto roleListRequest) {
        try {
            List<Role> roleList = new ArrayList<>();
            for (RoleDto roleRequest : roleListRequest.getRoles()) {
                Optional<Role> role = roleRepository.findByNameAndMarkForDeleteIsFalse(roleRequest.getName());
                if (role.isPresent()) throw new Exception("role already exist");
                roleList.add(converter.toEntity(roleRequest));
            }
            roleRepository.saveAll(roleList);
            return ResponseHelper.ok(RoleListDto.builder()
                    .roles(roleList.stream().map(converter::toDto).collect(Collectors.toList()))
                    .build());
        } catch (Throwable e) {
            log.error("error create role : ",e);
            return (Response<RoleListDto>) showResponseError(e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<RoleDto> updateRole(@PathVariable("id") String roleId, @RequestParam("new_name") String newName) {
        try {
            if(ObjectUtils.isEmpty(newName)){
                throw new Exception("new name is empty");
            }
            Role role = roleRepository.findByIdAndMarkForDeleteIsFalse(roleId).orElseThrow(()->new NotFoundException("role is not found"));
            role.setName(newName);
            roleRepository.save(role);
            return ResponseHelper.ok(converter.toDto(role));
        } catch (Exception e) {
            log.error("error update role : ",e);
            return (Response<RoleDto>) showResponseError(e);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<Page<RoleDto>> getAllRole(RoleParamsQuery queryRequest) {
        try {
            Page<RoleDto> roleList = roleRepository.findAllByMarkForDeleteIsFalse(PageRequest.of(queryRequest.getPage(),queryRequest.getSize(), Sort.by(queryRequest.getSort_by()).ascending()))
                    .map(converter::toDto);
            return ResponseHelper.ok(roleList);

        } catch (Exception e) {
            log.error("error get all role : ",e);
            return (Response<Page<RoleDto>>) showResponseError(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<String> deleteRole(@PathVariable("id") String roleId) {
        try {
            Role role = roleRepository.findByIdAndMarkForDeleteIsFalse(roleId).orElseThrow(()->new NotFoundException("role is not found"));
            role.setMarkForDelete(true);
            role.setDeletedDate(new Date());
            roleRepository.save(role);
            return ResponseHelper.ok("delete role with "+roleId+" is success");

        } catch (Exception e) {
            log.error("error delete role : ",e);
            return (Response<String>) showResponseError(e);
        }
    }
}
