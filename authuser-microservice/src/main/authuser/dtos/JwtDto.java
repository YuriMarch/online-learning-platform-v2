package com.distancelearning.authuser.dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class JwtDto {

    @NonNull
    private String token;

    private String type = "Bearer";
}
