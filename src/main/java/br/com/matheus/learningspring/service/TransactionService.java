package br.com.matheus.learningspring.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.matheus.learningspring.dto.CreateTransactionDto;
import br.com.matheus.learningspring.exception.AppException;
import br.com.matheus.learningspring.model.Transaction;
import br.com.matheus.learningspring.model.User;
import br.com.matheus.learningspring.repository.TransactionRepository;
import br.com.matheus.learningspring.repository.UserRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(final CreateTransactionDto transactionData) {

        final User foundPayer = userRepository.findById(transactionData.getPayer_id())
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));
        final User foundPayee = userRepository.findById(transactionData.getPayee_id())
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        final float payerCurrentBalance = foundPayer.getBalance();
        final float payeeCurrentBalance = foundPayee.getBalance();

        foundPayer.setBalance(payerCurrentBalance - transactionData.getValue());
        foundPayee.setBalance(payeeCurrentBalance + transactionData.getValue());

        final Transaction newTransaction = new Transaction(foundPayer, foundPayee, transactionData.getValue());

        return transactionRepository.save(newTransaction);
    }

    public Transaction retrieveTransaction(final long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new AppException("Transaction not found!", HttpStatus.NOT_FOUND));
    }
}
