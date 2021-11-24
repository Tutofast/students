package com.arkisoftware.tutofast.students.command.api;

import com.arkisoftware.tutofast.students.api.ApiController;
import com.arkisoftware.tutofast.students.application.Notification;
import com.arkisoftware.tutofast.students.application.Result;
import com.arkisoftware.tutofast.students.command.application.dtos.request.EditStudentRequest;
import com.arkisoftware.tutofast.students.command.application.dtos.request.RegisterStudentRequest;
import com.arkisoftware.tutofast.students.command.application.dtos.response.EditStudentResponse;
import com.arkisoftware.tutofast.students.command.application.dtos.response.RegisterStudentResponse;
import com.arkisoftware.tutofast.students.command.application.services.StudentApplicationService;
import com.arkisoftware.tutofast.students.command.infra.StudentTiuRepository;
import io.swagger.annotations.Api;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@Api(tags = "Students")
public class StudentCommandController {
    private final StudentApplicationService studentApplicationService;
    private final CommandGateway commandGateway;
    private final StudentTiuRepository studentTiuRepository;

    public StudentCommandController(StudentApplicationService studentApplicationService, CommandGateway commandGateway, StudentTiuRepository studentTiuRepository) {
        this.studentApplicationService = studentApplicationService;
        this.commandGateway = commandGateway;
        this.studentTiuRepository = studentTiuRepository;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@RequestBody RegisterStudentRequest registerStudentRequest) {
        try {
            Result<RegisterStudentResponse, Notification> result = studentApplicationService.register(registerStudentRequest);
            if (result.isSuccess()) {
                return ApiController.created(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Object> edit(@PathVariable("studentId") String studentId, @RequestBody EditStudentRequest editStudentRequest) {
        try {
            editStudentRequest.setStudentId(studentId);
            Result<EditStudentResponse, Notification> result = studentApplicationService.edit(editStudentRequest);
            if (result.isSuccess()) {
                return ApiController.ok(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (AggregateNotFoundException exception) {
            return ApiController.notFound();
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }
}
