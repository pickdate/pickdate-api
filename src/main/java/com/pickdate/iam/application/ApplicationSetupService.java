package com.pickdate.iam.application;

import com.pickdate.bootstrap.domain.Email;
import com.pickdate.bootstrap.domain.Property;
import com.pickdate.bootstrap.exception.ResourceAlreadyExistException;
import com.pickdate.iam.domain.*;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pickdate.iam.domain.Authority.ADMIN;


@Service
@RequiredArgsConstructor
class ApplicationSetupService implements ApplicationSetupUseCase {

    private final ApplicationSetupRepository applicationSetupRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void setupDomain(DomainUrl domainUrl) {
        ApplicationSetup config = createOrFetchApplicationSetup();
        config.setDomainUrl(domainUrl);
        applicationSetupRepository.save(config);
    }

    @Override
    @Transactional
    public void setupAdmin(User user) {
        assertAdminNotTaken();
        assertEmailNotTaken(user.getEmail());
        user.addAuthority(ADMIN);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void completeSetup() {
        ApplicationSetup appConfig = createOrFetchApplicationSetup();
        if (appConfig.isSetupCompleted()) {
            return;
        }
        appConfig.completeSetup();
        applicationSetupRepository.save(appConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean setupCompleted() {
        return applicationSetupRepository.findAppConfig()
                .map(ApplicationSetup::isSetupCompleted)
                .orElse(false);
    }

    private @NonNull ApplicationSetup createOrFetchApplicationSetup() {
        return applicationSetupRepository.findAppConfig()
                .orElseGet(ApplicationSetup::new);
    }

    private void assertAdminNotTaken() {
        if (userRepository.findAll().stream().anyMatch(user -> user.getAuthorities().contains(ADMIN)))
            throw new ResourceAlreadyExistException(
                    Property.of("user", ADMIN), "Admin already exists"
            );
    }

    private void assertEmailNotTaken(Email email) {
        if (userRepository.existsByEmail(email))
            throw new ResourceAlreadyExistException(
                    Property.of("email", email), "User with email %s already exists".formatted(email)
            );
    }
}
