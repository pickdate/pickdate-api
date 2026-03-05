package com.pickdate.iam.infrastructure;

import com.pickdate.bootstrap.domain.Email;
import com.pickdate.iam.domain.UserNotFoundException;
import com.pickdate.iam.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
class UserAuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String userEmail) throws UsernameNotFoundException {
        return userRepository.findByEmail(Email.of(userEmail))
                .map(UserAuthentication::from)
                .orElseThrow(() -> UserNotFoundException.withEmail(userEmail));
    }
}
