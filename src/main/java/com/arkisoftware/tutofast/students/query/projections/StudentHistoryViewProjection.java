package com.arkisoftware.tutofast.students.query.projections;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;
import com.arkisoftware.tutofast.students.command.domain.*;
import com.arkisoftware.tutofast.students.contracts.events.*;


import java.time.Instant;
import java.util.Optional;

@Component
public class StudentHistoryViewProjection {
    private final StudentHistoryViewRepository studentHistoryViewRepository;

    public StudentHistoryViewProjection(StudentHistoryViewRepository studentHistoryViewRepository) {
        this.studentHistoryViewRepository = studentHistoryViewRepository;
    }

    @EventHandler
    public void on(StudentRegistered event, @Timestamp Instant timestamp) {
        StudentHistoryView studentHistoryView = new StudentHistoryView(event.getStudentId(), event.getFirstName(), event.getLastName(), event.getTiu(), StudentStatus.ACTIVE.toString(), event.getOccurredOn());
        studentHistoryViewRepository.save(studentHistoryView);
    }

    @EventHandler
    public void on(StudentEdited event, @Timestamp Instant timestamp) {
        Optional<StudentHistoryView> studentHistoryViewOptional = studentHistoryViewRepository.getLastByStudentId(event.getStudentId());
        if (studentHistoryViewOptional.isPresent()) {
            StudentHistoryView studentHistoryView = studentHistoryViewOptional.get();
            studentHistoryView = new StudentHistoryView(studentHistoryView);
            studentHistoryView.setFirstName(event.getFirstName());
            studentHistoryView.setLastName(event.getLastName());
            studentHistoryView.setTiu(event.getTiu());
            studentHistoryView.setCreatedAt(event.getOccurredOn());
            studentHistoryViewRepository.save(studentHistoryView);
        }
    }
}
