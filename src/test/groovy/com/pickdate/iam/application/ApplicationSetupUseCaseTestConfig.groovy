package com.pickdate.iam.application


import com.pickdate.test.stub.ApplicationSetupRepositoryFake
import com.pickdate.test.stub.UserRepositoryFake


class ApplicationSetupUseCaseTestConfig {

    static final def setupRepo = new ApplicationSetupRepositoryFake()
    static final def userRepo = new UserRepositoryFake()

    static ApplicationSetupUseCase applicationSetupUseCase() {
        new ApplicationSetupService(setupRepo, userRepo)
    }
}
