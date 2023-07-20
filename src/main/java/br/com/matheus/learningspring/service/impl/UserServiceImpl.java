package br.com.matheus.learningspring.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.matheus.learningspring.dto.CreateDepositDto;
import br.com.matheus.learningspring.dto.UserDto;
import br.com.matheus.learningspring.exception.AppException;
import br.com.matheus.learningspring.model.User;
import br.com.matheus.learningspring.repository.UserRepository;
import br.com.matheus.learningspring.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void checkEmailAndCpf(final UserDto userData) {
        if (userRepository.existsUserByCpf(userData.getCpf())) {
            throw new AppException("cpfAlreadyInUse", HttpStatus.CONFLICT);
        }

        if (userRepository.existsUserByEmail(userData.getEmail())) {
            throw new AppException("emailAlreadyInUse", HttpStatus.CONFLICT);
        }
    }

    public User createUser(final UserDto userData) {

        checkEmailAndCpf(userData);

        final User newUser = new User(userData.getName(), userData.getCpf(), userData.getEmail(),
                userData.getPassword(),
                userData.getType());

        return userRepository.save(newUser);
    }

    public List<User> readUsers() {
        return userRepository.findAll();
    }

    public User retrieveUser(final long id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException("userNotFound", HttpStatus.NOT_FOUND));
    }

    public User updateUser(final UserDto userData, final long id) {

        checkEmailAndCpf(userData);

        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("userNotFound", HttpStatus.NOT_FOUND));

        foundUser.setName(userData.getName());
        foundUser.setCpf(userData.getCpf());
        foundUser.setEmail(userData.getEmail());
        foundUser.setPassword(userData.getPassword());
        foundUser.setType(userData.getType());

        return userRepository.save(foundUser);
    }

    public void deleteUser(final long id) {

        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("userNotFound", HttpStatus.NOT_FOUND));

        userRepository.delete(foundUser);
    }

    // Transactions
    public User createDeposit(final CreateDepositDto depositData, final long id) {

        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("userNotFound", HttpStatus.NOT_FOUND));

        final float currentBalance = foundUser.getBalance();

        foundUser.setBalance(currentBalance + depositData.getValue());

        return userRepository.save(foundUser);
    }
}
