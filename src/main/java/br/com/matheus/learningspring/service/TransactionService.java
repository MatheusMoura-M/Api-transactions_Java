package br.com.matheus.learningspring.service;

import br.com.matheus.learningspring.dto.CreateTransactionDto;
import br.com.matheus.learningspring.model.Transaction;

public interface TransactionService {

    public Transaction createTransaction(final CreateTransactionDto transactionData);

    public Transaction retrieveTransaction(final long id);

}
