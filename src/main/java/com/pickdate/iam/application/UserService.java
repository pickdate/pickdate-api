package com.pickdate.iam.application;

import com.pickdate.bootstrap.domain.Email;
import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.bootstrap.domain.Property;
import com.pickdate.bootstrap.exception.ResourceAlreadyExistException;
import com.pickdate.iam.domain.Authority;
import com.pickdate.iam.domain.User;
import com.pickdate.iam.domain.UserNotFoundException;
import com.pickdate.iam.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
class UserService implements UserUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(String id) {
        return getByIdOrThrow(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return getByEmailOrThrow(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        assertEmailNotTaken(user.getEmail());
        user.addAuthority(Authority.USER);
        return userRepository.save(user);
    }

    private void assertEmailNotTaken(Email email) {
        if (userRepository.existsByEmail(email))
            throw new ResourceAlreadyExistException(
                    Property.of("email", email), "User with email %s already exists".formatted(email)
            );
    }

    private User getByEmailOrThrow(String email) {
        return userRepository.findByEmail(Email.of(email))
                .orElseThrow(() -> UserNotFoundException.withEmail(email));
    }

    private User getByIdOrThrow(String id) {
        return userRepository.findById(Identifier.of(id))
                .orElseThrow(() -> UserNotFoundException.withId(id));
    }
}
