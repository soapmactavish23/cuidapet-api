package com.hkprogrammer.api.domain.view_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConfirmInputModel {
    private String iosToken;
    private String androidToken;
}
