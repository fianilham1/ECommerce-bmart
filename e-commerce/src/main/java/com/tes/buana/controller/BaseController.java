package com.tes.buana.controller;

import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.tes.buana.common.exception.BusinessException;
import com.tes.buana.common.exception.NotFoundException;
import com.tes.buana.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BaseController extends ExceptionHandlerController {

    @Autowired HttpServletRequest httpServletRequest;

    @ModelAttribute("attributeRequest")
    public UserDto getAuthInfo() {
        Map<String, String> map = (Map<String, String>) httpServletRequest.getAttribute("attributeRequest");
        if(map != null){
            return UserDto.builder()
                    .username(map.get("username"))
                    .role(map.get("role"))
                    .build();
        }
        return null;
    }

    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "system";
        }
        return authentication.getName();
    }

    protected Response<?> showResponseError(Throwable e) {
        Response response;
        if (e instanceof BusinessException || e.getCause() instanceof BusinessException){
            response = ResponseHelper.status(HttpStatus.UNPROCESSABLE_ENTITY);
        }else if(e instanceof NotFoundException || e.getCause() instanceof NotFoundException) {
            response = ResponseHelper.status(HttpStatus.NOT_FOUND);
        } else {
            response = ResponseHelper.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (e instanceof BusinessException) {
            response.setErrors(showErrorMaps(e.getMessage()));
        } else {
            response.setErrors(showErrorMaps(e.getCause() == null ? e.getMessage():e.getCause().getMessage()));
        }
        return response;
    }

    protected Map<String, List<String>> showErrorMaps(String message) {
        return new HashMap() {{
            put("error", Arrays.asList(message));
        }};
    }
}
