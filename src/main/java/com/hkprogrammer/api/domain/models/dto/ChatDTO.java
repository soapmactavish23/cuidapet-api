package com.hkprogrammer.api.domain.models.dto;

import com.hkprogrammer.api.domain.models.Chat;
import com.hkprogrammer.api.domain.models.Supplier;
import lombok.Data;

@Data
public class ChatDTO {

    private Integer id;

    private Integer user;

    private Supplier supplier;

    private String name;

    private String petName;

    private String status;

    private DeviceToken userDeviceToken;

    private DeviceToken supplierDeviceToken;

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.user = chat.getSchedule().getUser().getId();
        this.supplier = chat.getSchedule().getSupplier();
        this.name = chat.getSchedule().getName();
        this.petName = chat.getSchedule().getPetName();
        this.status = chat.getSchedule().getStatus().toString();

        String androidTokenUser = chat.getSchedule().getUser().getAndroidToken();
        String iosTokenUser = chat.getSchedule().getUser().getIosToken();
        this.userDeviceToken = new DeviceToken(androidTokenUser, iosTokenUser);

    }

}
