package com.arkisoftware.tutofast.students.command.application.validators;

import com.arkisoftware.tutofast.students.application.Notification;
import com.arkisoftware.tutofast.students.command.application.dtos.request.RegisterStudentRequest;
import com.arkisoftware.tutofast.students.command.infra.StudentTiu;
import com.arkisoftware.tutofast.students.command.infra.StudentTiuRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegisterStudentValidator {
    private final StudentTiuRepository studentTiuRepository;

    public RegisterStudentValidator(StudentTiuRepository studentTiuRepository) {
        this.studentTiuRepository = studentTiuRepository;
    }

    public Notification validate(RegisterStudentRequest registerStudentRequest) {
        Notification notification = new Notification();
        String firstName = registerStudentRequest.getFirstName().trim();
        String lastName = registerStudentRequest.getLastName().trim();
        String tiu = registerStudentRequest.getTiu().trim();

        if (firstName.isEmpty()) {
            notification.addError("Student firstname is required");
        }
        if (lastName.isEmpty()) {
            notification.addError("Student lastname is required");
        }
        if (tiu.isEmpty()) {
            notification.addError("Student DNI is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }
        Optional<StudentTiu> studentTiuOptional = studentTiuRepository.findById(tiu);
        if (studentTiuOptional.isPresent()) {
            notification.addError("Student DNI is taken");
        }
        return notification;
    }


}
