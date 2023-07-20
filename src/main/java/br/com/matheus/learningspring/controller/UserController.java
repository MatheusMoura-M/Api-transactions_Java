package br.com.matheus.learningspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matheus.learningspring.dto.CreateDepositDto;
import br.com.matheus.learningspring.dto.UserDto;
import br.com.matheus.learningspring.model.User;
import br.com.matheus.learningspring.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody final UserDto userData) {
        final User createdUser = userService.createUser(userData);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> readUser() {
        final List<User> allUsers = userService.readUsers();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> retrieveUser(@PathVariable final String id) {
        final User user = userService.retrieveUser(Long.parseLong(id));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody final UserDto userData, @PathVariable final String id) {
        final User updatedUser = userService.updateUser(userData, Long.parseLong(id));

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final String id) {
        userService.deleteUser(Long.parseLong(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Transactions
    @PostMapping("/{id}/deposit")
    public ResponseEntity<User> createDeposit(@Valid @RequestBody final CreateDepositDto depositData,
            @PathVariable final String id) {
        final User depositedUser = userService.createDeposit(depositData, Long.parseLong(id));

        return new ResponseEntity<>(depositedUser, HttpStatus.CREATED);
    }
}
