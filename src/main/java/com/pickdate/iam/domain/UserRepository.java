package com.pickdate.iam.domain;

import com.pickdate.bootstrap.domain.Email;
import com.pickdate.bootstrap.domain.Identifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;


public interface UserRepository {

    boolean existsByEmail(Email email);

    Optional<User> findByEmail(Email email);

    Page<User> findAll(Pageable pageable);

    Collection<User> findAll();

    User save(User user);

    Optional<User> findById(Identifier id);
}
