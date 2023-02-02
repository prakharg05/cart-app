package com.shopper.controller;

import com.shopper.config.TokenProvider;
import com.shopper.dao.UserAccount;
import com.shopper.dto.UserDTO;
import com.shopper.dto.auth.AuthToken;
import com.shopper.dto.auth.LoginPayload;
import com.shopper.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller to route Account related requests
 */
@Slf4j
@RestController
@RequestMapping("/api/account")
public class UserController {


    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    protected UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public UserAccount getAccount(Principal user) {
        return userService.findOne(user.getName());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody LoginPayload loginRequest) {
        log.info("Login Payload {}", loginRequest);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping("/register")
    public UserAccount registerUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ping")
    public String userPing(){
        return "TEST PING FOR ALL USERS";
    }

    @PostMapping("/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    public UserAccount suspendUser(@RequestBody UserDTO userDTO) { return userService.suspendUserByUsername(userDTO.getUsername());}


    @PostMapping("/reinstate")
    @PreAuthorize("hasRole('ADMIN')")
    public UserAccount reinstateUser(@RequestBody UserDTO userDTO) { return userService.reinstateUserByUsername(userDTO.getUsername());}


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserAccount> getAllAccounts() {
        return userService.fetchAllUserAccounts();
    }


}
