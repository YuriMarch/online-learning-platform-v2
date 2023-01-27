package com.distancelearning.course.services.impl;

import com.distancelearning.course.dtos.NotificationCommandDto;
import com.distancelearning.course.models.CourseModel;
import com.distancelearning.course.models.LessonModel;
import com.distancelearning.course.models.ModuleModel;
import com.distancelearning.course.models.UserModel;
import com.distancelearning.course.publishers.NotificationCommandPublisher;
import com.distancelearning.course.repositories.CourseRepository;
import com.distancelearning.course.repositories.LessonRepository;
import com.distancelearning.course.repositories.ModuleRepository;
import com.distancelearning.course.services.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    NotificationCommandPublisher notificationCommandPublisher;

    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if (!moduleModelList.isEmpty()){
            for (ModuleModel module : moduleModelList){
                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessonModelList.isEmpty()){
                    lessonRepository.deleteAll(lessonModelList);
                }
            }
            moduleRepository.deleteAll(moduleModelList);
        }
        courseRepository.deleteCourseUserByCourse(courseModel.getCourseId());
        courseRepository.delete(courseModel);
    }

    @Override
    public CourseModel save(CourseModel courseModel) {
        return courseRepository.save(courseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public List<CourseModel> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public boolean existsByCourseAndUser(UUID courseId, UUID userId) {
        return courseRepository.existsByCourseAndUser(courseId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        courseRepository.saveCourseUser(courseId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourseAndSendNotification(CourseModel course, UserModel user){
        courseRepository.saveCourseUser(course.getCourseId(), user.getUserId());
        try {
            var notificationCommandDto = new NotificationCommandDto();
            notificationCommandDto.setTitle("Welcome to the Course: " + course.getName());
            notificationCommandDto.setMessage(user.getFullName() + " your subscription was successfully completed.");
            notificationCommandDto.setUserId(user.getUserId());
            notificationCommandPublisher.publishNotificationCommand(notificationCommandDto);
        } catch (Exception e) {
            log.warn("Error sending the notification.");
        }
    }
}