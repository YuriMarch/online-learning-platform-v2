package com.distancelearning.course.dtos;

import com.distancelearning.course.models.UserModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

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

    public UserModel convertToUserModel(){
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(this, userModel);
        return userModel;
    }
}
