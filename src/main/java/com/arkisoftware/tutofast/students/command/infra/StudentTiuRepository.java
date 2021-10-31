package com.arkisoftware.tutofast.students.command.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentTiuRepository extends JpaRepository<StudentTiu, String> {
    Optional<StudentTiu> getTiuByStudentId(String studentId);

    @Query(value = "SELECT * FROM student_id WHERE student_id <> :studentId AND tiu = :tiu LIMIT 1", nativeQuery = true)
    Optional<StudentTiu> getByTiuForDistinctStudentId(String tiu, String studentId);
}
