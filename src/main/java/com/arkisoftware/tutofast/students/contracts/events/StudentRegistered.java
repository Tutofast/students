package com.arkisoftware.tutofast.students.contracts.events;

import lombok.Value;

import java.time.Instant;

@Value
public class StudentRegistered {
    String studentId;
    String firstName;
    String lastName;
    String tiu;
    Instant occurredOn;
}
