package com.pickdate.iam;

import java.util.List;


record UserData(
        String username,
        String email,
        List<String> authorities
) {

    static UserData from(User user) {
        return new UserData(
                user.getUsername().value(),
                user.getEmail().value(),
                user.getAuthoritiesAsString()
        );
    }
}
