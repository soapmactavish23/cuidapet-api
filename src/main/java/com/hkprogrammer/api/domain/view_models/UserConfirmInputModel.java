package com.hkprogrammer.api.domain.view_models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConfirmInputModel {
    @NotNull
    private Integer userId;
    @NotBlank
    private String refreshToken;
    private String iosDeviceToken;
    private String androidDeviceToken;
}
