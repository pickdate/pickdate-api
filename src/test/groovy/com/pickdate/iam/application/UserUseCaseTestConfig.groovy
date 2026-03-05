package com.pickdate.iam.application


import com.pickdate.test.fixture.UserFixture
import com.pickdate.test.stub.UserRepositoryFake


class UserUseCaseTestConfig {

    static final def repo = new UserRepositoryFake()

    static UserUseCase userUseCase() {
        new UserService(repo)
    }

    static void setupTestData() {
        repo.save(UserFixture.SOME_ADMIN)
        repo.save(UserFixture.SOME_USER)
    }
}
