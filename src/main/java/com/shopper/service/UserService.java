package com.shopper.service;

import com.shopper.config.TokenProvider;
import com.shopper.dao.Role;
import com.shopper.dao.UserAccount;
import com.shopper.dto.UserDTO;
import com.shopper.exceptions.InvalidInputException;
import com.shopper.repository.RoleRepository;
import com.shopper.repository.UserRepository;
import com.shopper.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.shopper.config.Config.*;

@Service(value = "userService")
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    @Lazy
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private Set<SimpleGrantedAuthority> getAuthority(UserAccount user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    public UserAccount suspendUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        user.setSuspended(true);
        userRepository.save(user);
        return user;
    }

    public UserAccount reinstateUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        user.setSuspended(false);
        userRepository.save(user);
        return user;
    }

    public UserAccount save(UserDTO user) {
        UserAccount nUser = adaptToDao(user);
        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Role role = roleRepository.findRoleByName(USER_ROLE);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        if(!ValidationUtils.isValidEmail(nUser.getEmail())) {
            log.error("Invalid Email for user {}", user);
            throw new InvalidInputException("BAD EMAIL");
        }
        if(nUser.getEmail().split(EMAIL_SEPERATOR)[1].equals(ADMIN_DOMAIN)){
            role = roleRepository.findRoleByName(ADMIN_ROLE);
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userRepository.save(nUser);
    }


    private UserAccount adaptToDao(UserDTO user) {
        return UserAccount.builder().username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail()).build();
    }

    public UserAccount findOne(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserAccount> fetchAllUserAccounts() {
        return  userRepository.findAll();
    }

}
