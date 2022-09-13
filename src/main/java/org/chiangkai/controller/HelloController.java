package org.chiangkai.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author ChiangKai
 * @date 2022/9/8 10:26
 */
@RestController
@AllArgsConstructor
public class HelloController {

    final PasswordEncoder passwordEncoder;

    final AuthenticationManager authenticationManager;

    @RequestMapping("/test")
    public String hello() {

        return "hello";
    }

    @RequestMapping("/forceLogin")
    public String login(HttpServletRequest request) {
        GrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("role1");
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(simpleGrantedAuthority);
        UserDetails user = new User("user", passwordEncoder.encode("abc"), roles);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, roles);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "1234";
    }

    @GetMapping("/login")
    public Principal login(String username, String password) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        Object principal = authentication.getPrincipal();
        return (Principal) authentication.getPrincipal();
    }

}
