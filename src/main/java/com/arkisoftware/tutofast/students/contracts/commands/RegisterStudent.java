package com.arkisoftware.tutofast.students.contracts.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class RegisterStudent {
    @TargetAggregateIdentifier
    String studentId;
    String firstName;
    String lastName;
    String tiu;
}
