package com.pickdate.iam.infrastructure;

import com.pickdate.bootstrap.encryption.Encryptor;
import com.pickdate.iam.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties("pickdate.keys")
@NoArgsConstructor
class ApplicationSetupConfiguration {

    private String masterKey;
    private String rememberMeKey;

    @Bean
    public ApplicationSetupUseCase applicationSetupUseCase(
            ApplicationSetupRepository repository,
            UserRepository userRepository
    ) {
        return new ApplicationSetupService(
                repository,
                userRepository,
                encryptor(),
                keyProperties()
        );
    }

    @Bean
    public KeyProperties keyProperties() {
        return new KeyProperties(masterKey, rememberMeKey);
    }

    @Bean
    public Encryptor encryptor() {
        return new EncryptorDelegate(Encryptor.noop());
    }
}
