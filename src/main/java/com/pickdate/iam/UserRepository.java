package com.pickdate.iam;

import com.pickdate.shared.model.Email;
import com.pickdate.shared.model.Username;
import org.springframework.data.jpa.repository.JpaRepository;


interface UserRepository extends JpaRepository<User, Username> {

    boolean existsByEmail(Email email);
}
