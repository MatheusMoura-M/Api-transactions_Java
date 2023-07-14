package br.com.matheus.learningspring.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.matheus.learningspring.dto.CreateDepositDto;
import br.com.matheus.learningspring.dto.UserDto;
import br.com.matheus.learningspring.exception.AppException;
import br.com.matheus.learningspring.model.User;
import br.com.matheus.learningspring.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(final UserDto userData) {
        final User newUser = new User(userData.getName(), userData.getCpf(), userData.getEmail(),
                userData.getPassword(),
                userData.getType());

        return userRepository.save(newUser);
    }

    public List<User> readUsers() {
        return userRepository.findAll();
    }

    public User retrieveUser(final long id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));
    }

    public User updateUser(final UserDto userData, final long id) {
        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        foundUser.setName(userData.getName());
        foundUser.setCpf(userData.getCpf());
        foundUser.setEmail(userData.getEmail());
        foundUser.setPassword(userData.getPassword());
        foundUser.setType(userData.getType());

        return userRepository.save(foundUser);
    }

    public void deleteUser(final long id) {
        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        userRepository.delete(foundUser);
    }

    // Transactions
    public User createDeposit(final CreateDepositDto depositData, final long id) {
        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        final float currentBalance = foundUser.getBalance();

        foundUser.setBalance(currentBalance + depositData.getValue());

        return userRepository.save(foundUser);
    }
}
