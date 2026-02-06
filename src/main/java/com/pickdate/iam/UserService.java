package com.pickdate.iam;

import com.pickdate.shared.exception.ResourceAlreadyExist;
import com.pickdate.shared.model.Email;
import com.pickdate.shared.model.Property;
import com.pickdate.shared.model.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserBy(Principal principal) {
        var username = principal.getName();
        return getOrThrow(username);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return getOrThrow(username);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void createUser(User user) {
        assertUsernameNotTaken(user.getUsername());
        assertEmailNotTaken(user.getEmail());

        user.addAuthority(Authority.USER);
        userRepository.save(user);
    }

    private User getOrThrow(String username) {
        return getUser(Username.of(username))
                .orElseThrow(() -> UserNotFound.withUsername(username));
    }

    private void assertUsernameNotTaken(Username username) {
        if (userRepository.existsById(username))
            throw new ResourceAlreadyExist(
                    Property.of("username", username.value()), "User with username %s already exists".formatted(username)
            );
    }

    private void assertEmailNotTaken(Email email) {
        if (userRepository.existsByEmail(email))
            throw new ResourceAlreadyExist(
                    Property.of("email", email.value()), "User with email %s already exists".formatted(email)
            );
    }

    private Optional<User> getUser(Username user) {
        return userRepository.findById(user);
    }
}
