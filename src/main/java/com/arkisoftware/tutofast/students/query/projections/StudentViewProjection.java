package com.arkisoftware.tutofast.students.query.projections;

import com.arkisoftware.tutofast.students.contracts.events.StudentEdited;
import com.arkisoftware.tutofast.students.contracts.events.StudentRegistered;
import com.arkisoftware.tutofast.students.command.domain.StudentStatus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class StudentViewProjection {
    private final StudentViewRepository studentViewRepository;

    public StudentViewProjection(StudentViewRepository studentViewRepository) {
        this.studentViewRepository = studentViewRepository;
    }

    @EventHandler
    public void on(StudentRegistered event, @Timestamp Instant timestamp) {
        StudentView studentView = new StudentView(event.getStudentId(), event.getFirstName(), event.getLastName(), event.getTiu(), StudentStatus.ACTIVE.toString(), event.getOccurredOn());
        studentViewRepository.save(studentView);
    }

    @EventHandler
    public void on(StudentEdited event, @Timestamp Instant timestamp) {
        Optional<StudentView> studentViewOptional = studentViewRepository.findById(event.getStudentId().toString());
        if (studentViewOptional.isPresent()) {
            StudentView studentView = studentViewOptional.get();
            studentView.setFirstName(event.getFirstName());
            studentView.setLastName(event.getLastName());
            studentView.setTiu(event.getTiu());
            studentView.setUpdatedAt(event.getOccurredOn());
            studentViewRepository.save(studentView);
        }
    }
}