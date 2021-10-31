package com.arkisoftware.tutofast.students.query.api;

import com.arkisoftware.tutofast.students.query.projections.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.arkisoftware.tutofast.common.api.ApiController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@Api(tags = "Students")
public class StudentQueryController {
    private final StudentViewRepository studentViewRepository;
    private final StudentHistoryViewRepository studentHistoryViewRepository;

    public StudentQueryController(StudentViewRepository studentViewRepository, StudentHistoryViewRepository studentHistoryViewRepository) {
        this.studentViewRepository = studentViewRepository;
        this.studentHistoryViewRepository = studentHistoryViewRepository;
    }

    @GetMapping("")
    @ApiOperation(value="Get all Students", response = List.class)
    public ResponseEntity<Object> getAll() {
        try {
            return ApiController.ok(studentViewRepository.findAll());
        } catch ( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get student by id", response = StudentView.class)
    public ResponseEntity<Object> getById(@PathVariable("id") String id) {
        try {
            Optional<StudentView> studentViewOptional = studentViewRepository.findById(id);
            if (studentViewOptional.isPresent()) {
                return ApiController.ok(studentViewOptional.get());
            }
            return ApiController.notFound();
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }

    @GetMapping(path = "/tiu/{tiu}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get student", response = StudentView.class)
    public ResponseEntity<Object> getByDocument(@PathVariable("tiu") String tiu) {
        try {
            Optional<StudentView> studentViewOptional = studentViewRepository.getByTiu(tiu);
            if (studentViewOptional.isPresent()) {
                return ApiController.ok(studentViewOptional.get());
            }
            return ApiController.notFound();
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }

    @GetMapping("/history/{id}")
    @ApiOperation(value = "Get student history", response = List.class)
    public ResponseEntity<Object> getHistoryById(@PathVariable("id") String id){
        try {
            List<StudentHistoryView> students = studentHistoryViewRepository.getHistoryByStudentId(id);
            return ApiController.ok(students);
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }
}
