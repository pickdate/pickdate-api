package com.pickdate.iam.infrastructure;


import com.pickdate.bootstrap.domain.Email;
import com.pickdate.iam.domain.Password;
import com.pickdate.iam.domain.User;

record CreateUserRequest(
        String email,
        String password
) {

    public User toUser() {
        return new User(
                Email.of(email),
                Password.fromPlaintext(password)
        );
    }
}
