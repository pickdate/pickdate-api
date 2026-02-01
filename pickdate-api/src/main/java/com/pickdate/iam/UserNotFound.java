package com.pickdate.iam;

import com.pickdate.shared.exception.NotFound;
import com.pickdate.shared.model.Property;

class UserNotFound extends NotFound {

    static final String MESSAGE = "User not found";

    public UserNotFound(String username) {
        this(Property.of("username", username), MESSAGE);
    }

    public UserNotFound(Property<Object> property, String message) {
        super(property, message);
    }

    static UserNotFound withUsername(String username) {
        return new UserNotFound(username);
    }
}
