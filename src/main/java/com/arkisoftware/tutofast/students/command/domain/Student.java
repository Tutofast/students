package com.arkisoftware.tutofast.students.command.domain;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.arkisoftware.tutofast.students.contracts.commands.*;
import com.arkisoftware.tutofast.students.contracts.events.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;

@Aggregate
public class Student {
    @AggregateIdentifier
    private String studentId;
    private String firstName;
    private String lastName;
    private String tiu;
    private StudentStatus status;

    public Student() {}

    @CommandHandler
    public Student(RegisterStudent command) {
        Instant now = Instant.now();
        apply(
                new StudentRegistered(
                        command.getStudentId(),
                        command.getFirstName(),
                        command.getLastName(),
                        command.getTiu(),
                        now
                )
        );
    }

    @CommandHandler
    public void handle(EditStudent command) {
        Instant now = Instant.now();
        apply(
                new StudentEdited(
                        command.getStudentId(),
                        command.getFirstName(),
                        command.getLastName(),
                        command.getTiu(),
                        now
                )
        );
    }

    @EventSourcingHandler
    protected void on(StudentRegistered event) {
        studentId = event.getStudentId();
        firstName = event.getFirstName();
        lastName = event.getLastName();
        tiu = event.getTiu();
        status = StudentStatus.ACTIVE;
    }

    @EventSourcingHandler
    protected void on(StudentEdited event) {
        firstName = event.getFirstName();
        lastName = event.getLastName();
        tiu = event.getTiu();
    }
}
