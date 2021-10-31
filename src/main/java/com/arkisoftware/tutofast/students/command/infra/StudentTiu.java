package com.arkisoftware.tutofast.students.command.infra;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StudentTiu {
    @Id
    @Column(length = 8)
    public String tiu;
    String studentId;

    public StudentTiu() {}

    public StudentTiu(String tiu, String studentId) {
        this.tiu = tiu;
        this.studentId = studentId;
    }

    public String getTiu() {
        return tiu;
    }

    public void setTiu(String tiu) {
        this.tiu = tiu;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
