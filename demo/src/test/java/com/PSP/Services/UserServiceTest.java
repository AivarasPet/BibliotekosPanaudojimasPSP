package com.PSP.Services;

import com.PSP.DemoApplication;
import com.PSP.Models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testSaveUser_successful() {
        User user1 = new User(1, "name", "lname", "+37068380454", "aiwaraspet@gmail.com", "Didlaukio 59", "Apassword123!");
        User user2 = userService.saveUser(user1);
        assertEquals(user1, user2);
    }

    @Test
    void testSaveUser_invalidEmail_noAtSymbol() {
        User user1 = new User(1, "name", "lname", "+37068380454", "aiwaraspetgmail.com", "Didlaukio 59", "Apassword123!");
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user1));
        assertEquals("Invalid email", exception.getMessage());
    }

    @Test
    void testSaveUser_invalidEmail_wrongDomain() {
        User user1 = new User(1, "name", "lname", "+37068380454", "aiwaraspet@gmail..com", "Didlaukio 59", "Apassword123!");
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user1));
        assertEquals("Invalid email", exception.getMessage());
    }

    @Test
    void testSaveUser_invalidPhoneNumber_badPrefix() {
        User user1 = new User(1, "name", "lname", "+39968380454", "aiwaraspet@gmail.com", "Didlaukio 59", "Apassword123!");
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user1));
        assertEquals("Invalid phone number", exception.getMessage());
    }

    @Test
    void testSaveUser_invalidPhoneNumber_numberTooShort() {
        User user1 = new User(1, "name", "lname", "+370683", "aiwaraspet@gmail.com", "Didlaukio 59", "Apassword123!");
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user1));
        assertEquals("Invalid phone number", exception.getMessage());
    }

    @Test
    void testSaveUser_invalidPassword_passwordTooShort() {
        User user1 = new User(1, "name", "lname", "+37068380454", "aiwaraspet@gmail.com", "Didlaukio 59", "spasswo3!");
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user1));
        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void testSaveUser_invalidPassword_noSpecialCharacter() {
        User user1 = new User(1, "name", "lname", "+37068380454", "aiwaraspet@gmail.com", "Didlaukio 59", "Apassword1234");
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user1));
        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void testFetchUsers() {
        User user1 = new User(1, "name", "lname", "+37068380454", "aiwaraspet@gmail.com", "Didlaukio 59", "Apassword123!");
        User user2 = new User(2, "name2", "lname2", "+37068380454", "aiwaraspet@gmail.com", "Didlaukio 59", "Apassword123!");
        userService.saveUser(user1);
        userService.saveUser(user2);
        List<User> actualUsers = userService.getUsers();
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user1);
        expectedUsers.add(user2);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testFetchUsersById() {
        User expectedUser = new User(1, "name", "lname", "+37068380454", "aiwaraspet@gmail.com", "Didlaukio 59", "Apassword123!");
        userService.saveUser(expectedUser);
        User actualUser = userService.getUserById(1).stream().findFirst().orElse(null);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testDeleteUserById() {
        User user = new User(1, "name", "lname", "+37068380454", "aiwaraspet@gmail.com", "Didlaukio 59", "Apassword123!");
        userService.saveUser(user);
        userService.deleteUser(user.getId());
        List<User> users = userService.getUsers();
        assertTrue(users.size() == 0);
    }
}