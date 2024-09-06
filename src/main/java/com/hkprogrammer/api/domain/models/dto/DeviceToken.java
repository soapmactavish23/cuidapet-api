package com.hkprogrammer.api.domain.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DeviceToken {

    private String android;
    private String ios;

    List<String> getTokens() {
        var tokens = new ArrayList<String>();
        tokens.add(android);
        tokens.add(ios);
        return tokens;
    }

}
