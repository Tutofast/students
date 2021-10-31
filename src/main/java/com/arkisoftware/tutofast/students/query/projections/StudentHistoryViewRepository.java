package com.arkisoftware.tutofast.students.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentHistoryViewRepository extends JpaRepository<StudentHistoryView, String> {
    @Query(value = "SELECT * FROM student_history_view WHERE student_history_id = (SELECT MAX(student_history_id) FROM student_history_view WHERE student_id = :studentId)", nativeQuery = true)
    Optional<StudentHistoryView> getLastByStudentId(String studentId);

    @Query(value = "SELECT * FROM student_history_view WHERE student_id = :studentId ORDER BY created_at", nativeQuery = true)
    List<StudentHistoryView> getHistoryByStudentId(String studentId);
}
