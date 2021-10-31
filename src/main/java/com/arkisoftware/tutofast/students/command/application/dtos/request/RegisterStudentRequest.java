package com.arkisoftware.tutofast.students.command.application.dtos.request;

import lombok.Value;

@Value
public class RegisterStudentRequest {
    String firstName;
    String lastName;
    String tiu;
}
