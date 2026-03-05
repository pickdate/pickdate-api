package com.pickdate.iam.domain;

import java.util.List;

import static com.pickdate.bootstrap.domain.Value.valueOrNull;


public record UserData(
        String id,
        String email,
        List<String> authorities
) {

    public static UserData from(User user) {
        return new UserData(
                valueOrNull(user.getId()),
                valueOrNull(user.getEmail()),
                user.getAuthoritiesAsString()
        );
    }
}
