package com.arkisoftware.tutofast.students.command.application.dtos.response;

import lombok.Value;

@Value
public class EditStudentResponse {
    String studentId;
    String firstName;
    String lastName;
    String tiu;
}
