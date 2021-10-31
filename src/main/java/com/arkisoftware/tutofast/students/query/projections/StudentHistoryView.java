package com.arkisoftware.tutofast.students.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class StudentHistoryView {
    @Id @GeneratedValue @Getter @Setter
    private Long studentHistoryId;
    @Column(length = 36) @Getter @Setter
    private String studentId;
    @Column(length = 50) @Getter @Setter
    private String firstName;
    @Column(length = 50) @Getter @Setter
    private String lastName;
    @Column(length = 8) @Getter @Setter
    private String tiu;
    @Column(length = 20) @Getter @Setter
    private String status;
    @Getter @Setter
    private Instant createdAt;

    public StudentHistoryView () {}

    public StudentHistoryView(String studentId, String firstName, String lastName, String tiu, String status, Instant createdAt) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tiu = tiu;
        this.status = status;
        this.createdAt = createdAt;
    }

    public StudentHistoryView(StudentHistoryView studentHistoryView) {
        this.studentId = studentHistoryView.getStudentId();
        this.firstName = studentHistoryView.getFirstName();
        this.lastName = studentHistoryView.getLastName();
        this.tiu = studentHistoryView.getTiu();
        this.status = studentHistoryView.getStatus();
        this.createdAt = studentHistoryView.getCreatedAt();
    }

}
