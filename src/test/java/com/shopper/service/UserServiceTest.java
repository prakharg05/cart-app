package com.shopper.service;

import com.shopper.dao.UserAccount;
import com.shopper.dto.UserDTO;
import com.shopper.repository.RoleRepository;
import com.shopper.repository.UserRepository;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.shopper.dao.Role;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bcryptEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    public void test_save_happy() {
        doReturn("SOMETHING").when(bcryptEncoder).encode(any());
        doReturn(Role.builder().name("USER").id(12l).build()).when(roleRepository).findRoleByName("USER");
        doReturn(Role.builder().name("ADMIN").id(13l).build()).when(roleRepository).findRoleByName("ADMIN");
        userService.save(TestData.userDTO);
        verify(userRepository, times(1)).save(any());
    }


    @Test
    public void test_reinstatiateUser_happy() {
        doReturn(TestData.userAccount).when(userRepository).findByUsername(any());
        UserAccount account = userService.reinstateUserByUsername(TestData.testUser);
        verify(userRepository, times(1)).save(any());
        Assertions.assertFalse(account.getSuspended());

    }

    @Test
    public void test_suspendUser_happy() {
        doReturn(TestData.userAccount).when(userRepository).findByUsername(any());
        UserAccount account = userService.suspendUserByUsername(TestData.testUser);
        verify(userRepository, times(1)).save(any());
        Assertions.assertTrue(account.getSuspended());

    }

    static class TestData {
        private static String testUser = "prkharg";
        private static UserAccount userAccount = UserAccount.builder()
                .username(testUser)
                .password("anything")
                .build();
        private static  UserDTO userDTO = UserDTO.builder().email("as@shopper.com").password("^&9845737").username(testUser).build();
    }
}
