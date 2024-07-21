package com.getreadyforthenext.core.user.repositories;

import com.getreadyforthenext.core.common.utils.CommonUtil;
import com.getreadyforthenext.core.user.entities.User;
import com.getreadyforthenext.core.user.enums.AuthenticationProvider;
import com.getreadyforthenext.core.user.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@ComponentScan(basePackages = "com.getreadyforthenext.core")
class UserRepositorySupportTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositorySupport userRepositorySupport;

    @MockBean
    private CommonUtil commonUtil;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        when(commonUtil.generateRandomString(32)).thenReturn("randompassword");
        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword");
    }

    @Test
    void testGoogleUserRegistration_NewUser() {
        Map<String, Object> googleUserInfo = new HashMap<>();
        googleUserInfo.put("email", "new_user@example.com");
        googleUserInfo.put("name", "New User");

        User newUser = userRepositorySupport.googleUserRegistration(googleUserInfo);

        assertNotNull(newUser, "User should not be null");
        assertEquals("new_user@example.com", newUser.getEmail(), "Email should match");
        assertEquals("New User", newUser.getName(), "Name should match");
        assertEquals("encodedpassword", newUser.getPassword(), "Password should match");
        assertEquals(AuthenticationProvider.GOOGLE, newUser.getAuthenticationProvider(), "Authentication provider should be GOOGLE");
        assertEquals(Role.USER, newUser.getRole(), "Role should be USER");
    }

    @Test
    void testGoogleUserRegistration_ExistingUser() {
        User user = new User();
        user.setEmail("existing_user@example.com");
        user.setPassword("existingpassword");
        user.setName("Existing User");
        user.setAuthenticationProvider(AuthenticationProvider.GOOGLE);
        user.setRole(Role.USER);
        userRepository.save(user);

        Map<String, Object> googleUserInfo = new HashMap<>();
        googleUserInfo.put("email", "existing_user@example.com");

        User existingUser = userRepositorySupport.googleUserRegistration(googleUserInfo);

        assertNotNull(existingUser, "User should not be null");
        assertEquals("existing_user@example.com", existingUser.getEmail(), "Email should match");
        assertEquals("Existing User", existingUser.getName(), "Name should match");
        assertEquals("existingpassword", existingUser.getPassword(), "Password should match");
        assertEquals(AuthenticationProvider.GOOGLE, existingUser.getAuthenticationProvider(), "Authentication provider should be GOOGLE");
        assertEquals(Role.USER, existingUser.getRole(), "Role should be USER");
    }
}
