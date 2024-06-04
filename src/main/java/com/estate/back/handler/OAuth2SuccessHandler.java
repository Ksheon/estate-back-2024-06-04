package com.estate.back.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.estate.back.common.object.CustomAuth2User;
import com.estate.back.provider.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Value("${front.host}") 
    private String FRONT_HOST;
    private final JwtProvider jwtProvider;
    
    @Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

        // authenticaion 안에 OAuth2User정보 존재
        CustomAuth2User oAuth2User = (CustomAuth2User) authentication.getPrincipal();
        String userId = oAuth2User.getName();

        String token = jwtProvider.create(userId);

        response.sendRedirect(FRONT_HOST + "/sns/" + token + "/36000");

	}

}