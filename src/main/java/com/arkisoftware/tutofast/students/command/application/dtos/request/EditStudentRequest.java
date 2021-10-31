package com.arkisoftware.tutofast.students.command.application.dtos.request;

import lombok.Getter;
import lombok.Setter;

public class EditStudentRequest {
    private @Setter @Getter String studentId;
    private @Getter String firstName;
    private @Getter String lastName;
    private @Getter String tiu;
}
