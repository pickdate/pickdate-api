package com.pickdate.iam.infrastructure;

import com.pickdate.bootstrap.domain.Email;
import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.iam.domain.User;
import com.pickdate.iam.domain.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
interface UserRepositoryJpa extends UserRepository, JpaRepository<User, Identifier> {

    @Override
    boolean existsByEmail(Email email);

    @Override
    Optional<User> findByEmail(Email email);

    @Override
    @NonNull
    Optional<User> findById(Identifier id);

    @Override
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    @Override
    User save(User user);
}
