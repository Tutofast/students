package com.arkisoftware.tutofast.students.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Instant;

@Entity
public class StudentView {
    @Id @Column(length = 36) @Getter @Setter
    private String studentId;
    @Column(length = 50) @Getter @Setter
    private String firstName;
    @Column(length = 50) @Getter @Setter
    private String lastName;
    @Column(length = 8, unique = true) @Getter @Setter
    private String tiu;
    @Column(length = 20) @Getter @Setter
    private String status;
    private Instant createdAt;
    @Column(nullable = true) @Getter @Setter
    private Instant updatedAt;

    public StudentView(){}

    public StudentView(String studentId, String firstName, String lastName, String tiu, String status, Instant createdAt) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tiu = tiu;
        this.status = status;
        this.createdAt = createdAt;
    }
}
