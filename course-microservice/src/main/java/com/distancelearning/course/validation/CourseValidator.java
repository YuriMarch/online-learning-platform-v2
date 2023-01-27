package com.distancelearning.course.validation;

import com.distancelearning.course.dtos.CourseDto;
import com.distancelearning.course.enums.UserType;
import com.distancelearning.course.models.UserModel;
import com.distancelearning.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDto courseDto = (CourseDto) target;
        validator.validate(courseDto, errors);
        if (!errors.hasErrors()){
            validateUserInstructor(courseDto.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors){
        Optional<UserModel> userModelOptional = userService.findById(userInstructor);
        if (userModelOptional.isEmpty()){
            errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
        }
        if (userModelOptional.get().getUserType().equals(UserType.STUDENT.toString())){
            errors.rejectValue("userInstructor", "UserInstructorError", "User must be an INSTRUCTOR or ADMIN.");
        }
    }
}
