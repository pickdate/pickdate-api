package com.pickdate.iam;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;


@Data
@Entity
@Table(name = "setup")
@NoArgsConstructor(access = PROTECTED)
class AppConfigEntity {

    public static final String APP_CONFIG = "app_config";

    @Id
    private String id = APP_CONFIG;

    private boolean success;

    public boolean success() {
        return success;
    }
}
