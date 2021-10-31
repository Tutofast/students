package com.arkisoftware.tutofast.students.command.application.validators;

import com.arkisoftware.tutofast.students.command.application.dtos.request.EditStudentRequest;
import com.arkisoftware.tutofast.students.command.infra.StudentTiu;
import com.arkisoftware.tutofast.students.command.infra.StudentTiuRepository;
import com.arkisoftware.tutofast.students.command.domain.Student;
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.stereotype.Component;
import org.axonframework.modelling.command.Repository;
import org.arkisoftware.tutofast.common.application.Notification;

import java.util.Optional;

@Component
public class EditStudentValidator {
    private final StudentTiuRepository studentTiuRepository;
    private final Repository<Student> studentRepository;

    public EditStudentValidator(StudentTiuRepository studentTiuRepository, Repository<Student> studentRepository) {
        this.studentTiuRepository = studentTiuRepository;
        this.studentRepository = studentRepository;
    }

    public Notification validate(EditStudentRequest editStudentRequest) {
        Notification notification = new Notification();
        String studentId = editStudentRequest.getStudentId().trim();
        String firstName = editStudentRequest.getFirstName().trim();
        String lastName = editStudentRequest.getLastName().trim();
        String tiu = editStudentRequest.getTiu().trim();
        if (studentId.isEmpty()) {
            notification.addError("Student ID is required");
        }
        loadCustomAggregate(studentId);
        if (firstName.isEmpty()) {
            notification.addError("Student firstName is required");
        }
        if (lastName.isEmpty()) {
            notification.addError("Student lastName is required");
        }
        if (tiu.isEmpty()) {
            notification.addError("Student DNI is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }
        Optional<StudentTiu> studentTiu = studentTiuRepository.getByTiuForDistinctStudentId(tiu, studentId);
        if (studentTiu.isPresent()) {
            notification.addError("Student DNI is taken");
        }
        return notification;
    }

    private void loadCustomAggregate(String studentId) {
        UnitOfWork unitOfWork = null;
        try {
            unitOfWork = DefaultUnitOfWork.startAndGet(null);
            studentRepository.load(studentId);
            unitOfWork.commit();
        } catch (AggregateNotFoundException ex) {
            unitOfWork.commit();
            throw ex;
        } catch (Exception ex) {
            if (unitOfWork != null) unitOfWork.rollback();
        }
    }
}
