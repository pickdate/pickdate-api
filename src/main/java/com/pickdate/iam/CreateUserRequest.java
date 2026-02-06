package com.pickdate.iam;


record CreateUserRequest(
        String username,
        String password,
        String email
) {
}
