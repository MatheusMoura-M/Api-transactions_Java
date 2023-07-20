package br.com.matheus.learningspring.service;

import java.util.List;

import br.com.matheus.learningspring.dto.CreateDepositDto;
import br.com.matheus.learningspring.dto.UserDto;
import br.com.matheus.learningspring.model.User;

public interface UserService {
    User createUser(final UserDto userData);

    List<User> readUsers();

    User retrieveUser(final long id);

    User updateUser(final UserDto userData, final long id);

    void deleteUser(final long id);

    // Transactions
    User createDeposit(final CreateDepositDto depositData, final long id);
}
