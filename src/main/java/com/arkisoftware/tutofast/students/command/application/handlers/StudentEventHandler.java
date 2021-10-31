package com.arkisoftware.tutofast.students.command.application.handlers;

import com.arkisoftware.tutofast.students.contracts.events.StudentRegistered;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import com.arkisoftware.tutofast.students.contracts.events.StudentEdited;
import com.arkisoftware.tutofast.students.command.infra.*;

import java.util.Optional;

@Component
@ProcessingGroup("studentTiu")
public class StudentEventHandler {
    private final StudentTiuRepository studentTiuRepository;

    public StudentEventHandler(StudentTiuRepository studentTiuRepository) {
        this.studentTiuRepository = studentTiuRepository;
    }

    @EventHandler
    public void on(StudentRegistered event) {
        studentTiuRepository.save(new StudentTiu(event.getTiu(), event.getStudentId()));
    }

    @EventHandler
    public void on(StudentEdited event) {
        Optional<StudentTiu> StudentTiuOptional = studentTiuRepository.getTiuByStudentId(event.getStudentId());
        StudentTiuOptional.ifPresent(studentTiuRepository::delete);
        studentTiuRepository.save(new StudentTiu(event.getTiu(), event.getStudentId()));
    }
}
