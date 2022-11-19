package com.distancelearning.authuser.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class UserEventDto {

    private UUID userId;
    private String userName;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private String actionType;
}
