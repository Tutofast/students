package com.arkisoftware.tutofast.students.command.application.services;

import com.arkisoftware.tutofast.students.command.application.dtos.response.EditStudentResponse;
import com.arkisoftware.tutofast.students.command.application.dtos.request.EditStudentRequest;
import com.arkisoftware.tutofast.students.command.application.validators.EditStudentValidator;

import com.arkisoftware.tutofast.students.command.application.dtos.response.RegisterStudentResponse;
import com.arkisoftware.tutofast.students.command.application.dtos.request.RegisterStudentRequest;
import com.arkisoftware.tutofast.students.command.application.validators.RegisterStudentValidator;

import com.arkisoftware.tutofast.students.contracts.commands.RegisterStudent;
import com.arkisoftware.tutofast.students.contracts.commands.EditStudent;

import org.arkisoftware.tutofast.common.application.Notification;
import org.arkisoftware.tutofast.common.application.Result;
import org.arkisoftware.tutofast.common.application.ResultType;

import com.arkisoftware.tutofast.students.command.infra.StudentTiuRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class StudentApplicationService {
    private final RegisterStudentValidator registerStudentValidator;
    private final EditStudentValidator editStudentValidator;
    private final CommandGateway commandGateway;
    private final StudentTiuRepository studentTiuRepository;

    public StudentApplicationService(RegisterStudentValidator registerStudentValidator, EditStudentValidator editStudentValidator, CommandGateway commandGateway, StudentTiuRepository studentTiuRepository) {
        this.registerStudentValidator = registerStudentValidator;
        this.editStudentValidator = editStudentValidator;
        this.commandGateway = commandGateway;
        this.studentTiuRepository = studentTiuRepository;
    }

    public Result<RegisterStudentResponse, Notification> register(RegisterStudentRequest registerStudentRequest) throws Exception {
        Notification notification = this.registerStudentValidator.validate(registerStudentRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        String studentId = UUID.randomUUID().toString();
        RegisterStudent registerStudent = new RegisterStudent(
                studentId,
                registerStudentRequest.getFirstName().trim(),
                registerStudentRequest.getLastName().trim(),
                registerStudentRequest.getTiu().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(registerStudent);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        RegisterStudentResponse registerStudentResponseDto = new RegisterStudentResponse(
                registerStudent.getStudentId(),
                registerStudent.getFirstName(),
                registerStudent.getLastName(),
                registerStudent.getTiu()
        );
        return Result.success(registerStudentResponseDto);
    }

    public Result<EditStudentResponse, Notification> edit(EditStudentRequest editStudentRequest) throws Exception {
        Notification notification = this.editStudentValidator.validate(editStudentRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        EditStudent editStudent = new EditStudent(
                editStudentRequest.getStudentId().trim(),
                editStudentRequest.getFirstName().trim(),
                editStudentRequest.getLastName().trim(),
                editStudentRequest.getTiu().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(editStudent);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        EditStudentResponse editStudentResponse = new EditStudentResponse(
                editStudent.getStudentId(),
                editStudent.getFirstName(),
                editStudent.getLastName(),
                editStudent.getTiu()
        );
        return Result.success(editStudentResponse);
    }
}
