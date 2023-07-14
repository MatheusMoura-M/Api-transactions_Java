package br.com.matheus.learningspring.repository;

import br.com.matheus.learningspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
