package com.tes.buana.controller;

import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.tes.buana.common.constant.CommonMessage;
import com.tes.buana.common.converter.UserConverter;
import com.tes.buana.config.JwtUtil;
import com.tes.buana.dto.user.CreateUserDto;
import com.tes.buana.dto.user.ForgotPasswordDto;
import com.tes.buana.dto.user.LoginUserDto;
import com.tes.buana.dto.user.LoginUserGoogleDto;
import com.tes.buana.dto.user.UpdateUserDto;
import com.tes.buana.dto.user.UserDto;
import com.tes.buana.dto.user.UserGoogleDto;
import com.tes.buana.entity.Role;
import com.tes.buana.entity.Users;
import com.tes.buana.repository.RoleRepository;
import com.tes.buana.service.UserService;
import com.tes.buana.service.impl.GoogleApiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private GoogleApiServices googleApiServices;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserConverter converter;

    /**
     * PUBLIC -----------------
     * */

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to BetaMart";
    }

    @PostMapping("/signin")
    public Response<UserDto> signIn(@RequestBody LoginUserDto request) {
        String cek = request.getPassword();
        try {
            Users users = userService.login(Users.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .build());
            String token = jwtUtil.generateToken(users.getUsername());
            return ResponseHelper.ok(converter.toDto(users,token));
        } catch (Throwable e) {
            log.error("error signin user : ",e);
            return (Response<UserDto>) showResponseError(e);
        }
    }

    @PostMapping("/google/signin")
    public Response<UserDto> googleSignIn(@RequestBody LoginUserGoogleDto request) {
        try {
            UserGoogleDto userGoogle = googleApiServices.decodeToken(request.getTokenId());
            Role role = roleRepository.findByNameAndMarkForDeleteIsFalse("customer").orElseThrow(()-> new Exception(CommonMessage.ROLE_UNAVAILABLE));
            Users users = userService.loginGoogle(Users.builder()
                    .username(userGoogle.getEmail())
                    .name(userGoogle.getName())
                    .image(userGoogle.getPicture())
                    .role(role)
                    .build());
            log.debug("google "+users);
            String token = jwtUtil.generateToken(users.getUsername());
            return ResponseHelper.ok(converter.toDto(users,token));
        } catch (Throwable e) {
            log.error("error signin user google : ",e);
            return (Response<UserDto>) showResponseError(e);
        }
    }

    @PostMapping("/register")
    public Response<UserDto> register(@RequestBody CreateUserDto request) {
        try {
            Role role = roleRepository.findByIdAndMarkForDeleteIsFalse(request.getRoleId()).orElseThrow(()-> new Exception(CommonMessage.ROLE_UNAVAILABLE));
            Users users = userService.create(Users.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .name(request.getName())
                    .image(request.getImage())
                    .mobilePhone(request.getMobilePhone())
                    .role(role)
                    .build());
            new Role();
            return ResponseHelper.ok(converter.toDto(users));
        } catch (Throwable e) {
            log.error("error create user : ",e);
            return (Response<UserDto>) showResponseError(e);
        }
    }

    @PutMapping
    public Response<UserDto> update(@RequestBody UpdateUserDto request) {
        try {
            Users loggedUsers = userService.getUser(getUsername());
            loggedUsers.setName(request.getName());
            loggedUsers.setMobilePhone(request.getMobilePhone());
            loggedUsers.setImage(request.getImage());
            loggedUsers.setPassword(request.getPassword());
            Users users = userService.update(loggedUsers);
            new Role();
            return ResponseHelper.ok(converter.toDto(users));
        } catch (Throwable e) {
            log.error("error update user : ",e);
            return (Response<UserDto>) showResponseError(e);
        }
    }

    @PostMapping("/forgot-password")
    public Response<UserDto> forgotPassword(@RequestParam ForgotPasswordDto request){
        try {
            userService.forgotPassword(request.getUsername());
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseHelper.ok(converter.toDto(Users.builder()
                    .name(request.getUsername()).build(),token));
        } catch (Throwable e) {
            log.error("error create user : ",e);
            return (Response<UserDto>) showResponseError(e);
        }
    }

    /**
     * ROLE_CUSTOMER -----------------
     * */

    @PutMapping("/reset-password")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Response<String> resetPassword(@RequestBody CreateUserDto updatedUser){
        try {
            userService.resetPassword(Users.builder()
                    .username(updatedUser.getUsername())
                    .password(updatedUser.getPassword())
                    .build());

            return ResponseHelper.ok("reset password is success");
        } catch (Throwable e) {
            log.error("error create user : ",e);
            return (Response<String>) showResponseError(e);
        }
    }


}
