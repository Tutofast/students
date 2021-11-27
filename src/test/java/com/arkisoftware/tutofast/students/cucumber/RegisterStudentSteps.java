package com.arkisoftware.tutofast.students.cucumber;

import com.arkisoftware.tutofast.students.contracts.commands.RegisterStudent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegisterStudentSteps {
    @LocalServerPort
    private RestTemplate restTemplate = new RestTemplate();
    private String postUrl = "https://students-4widerrmra-uc.a.run.app/swagger-ui/index.html#/";
    private String error = null;

    String studentId = randomString();
    String tiu = randomString();
    Long number = randomLong();
    @Given("I can register as a student")
    public void i_can_register_as_a_student(){
        String url = postUrl + "/Students/";
        String allRegisterStudent = restTemplate.getForObject(url, String.class);
        log.info(allRegisterStudent);
        assertTrue(!allRegisterStudent.isEmpty());
    }
    public String randomString(){
        byte[] array = new byte[8];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }

    public Long randomLong(){
        long generatedLong = new Random().nextLong();
        return generatedLong;
    }

    @Given("I register a repeated tiu")
    public void register_a_repeated_tiu(){
        String url = postUrl + "/Students/";
        RegisterStudent newRegisterStudent = new RegisterStudent(studentId, "firstname", "lastname", tiu);
        log.info(newRegisterStudent);
        try
        {
            RegisterStudent registerStudent = restTemplate.postForObject(url, newRegisterStudent, RegisterStudent.class);
            log.info(registerStudent);
        }catch(RestClientException e){
            error = "El tiu ya esta en uso";
        }
        assertEquals(error, "El tiu ya esta en uso");
    }
    @Then("I should see a error message {string}")
    public void i_should_see_a_error_message(String string) {assertEquals(string, error);}

    @Given("The system repeats the id")
    public void the_system_repeats_the_id(){
        String url = postUrl + "/Students/";
        RegisterStudent newRegisterStudent = new RegisterStudent(studentId, "firstname", "lastname", tiu);
        log.info(newRegisterStudent);
        try
        {
            RegisterStudent registerStudent = restTemplate.postForObject(url, newRegisterStudent, RegisterStudent.class);
            log.info(registerStudent);
        }catch(RestClientException e){
            error = "Usuario duplicado";
        }
        assertEquals(error, "Usuario duplicado");
    }
    @Given("I register an invalid tiu")
    public void i_register_an_invalid_dni(){
        String url = postUrl + "/Students/";
        RegisterStudent newRegisterStudent = new RegisterStudent(studentId, "firstname", "lastname", tiu);
        log.info(newRegisterStudent);
        try
        {
            RegisterStudent registerStudent = restTemplate.postForObject(url, newRegisterStudent, RegisterStudent.class);
            log.info(registerStudent);
        }catch(RestClientException e){
            error = "El tiu no existe";
        }
        assertEquals(error, "El tiu no existe");
    }




}
