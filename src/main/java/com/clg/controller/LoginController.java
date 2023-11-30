package com.clg.controller;

import com.clg.dto.AuthRequest;
import com.clg.model.UserInfo;
import com.clg.service.JwtService;
import com.clg.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService service;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
        String msg = service.addUser(userInfo);
        if(msg.contains("exists")){
            return new ResponseEntity<String>("email already exists", HttpStatus.FOUND);
        }
        return new ResponseEntity<String>("user created successfully", HttpStatus.OK);
    }
    @GetMapping("/fetch")
    public ResponseEntity<UserInfo> fetchUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return ResponseEntity.ok(service.fetchUser(username));
    }


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        log.info("In Authenticate");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            log.error("In Authenticate failure");
            throw new UsernameNotFoundException("invalid user request !");
        }


    }
}
