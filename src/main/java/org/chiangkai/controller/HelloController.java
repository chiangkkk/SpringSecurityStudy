package org.chiangkai.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ChiangKai
 * @date 2022/9/8 10:26
 */
@RestController
@AllArgsConstructor
public class HelloController {

    final PasswordEncoder passwordEncoder;

    @RequestMapping("/test")
    public String hello() {

        return "hello";
    }

    @RequestMapping("/login")
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

}
