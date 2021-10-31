package com.arkisoftware.tutofast.students.query.projections;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentViewRepository extends JpaRepository<StudentView, String> {
    Optional<StudentView> getByTiu(String tiu);

    @Query(value = "SELECT * FROM student_view WHERE student_id <> :studentId AND tiu = :tiu", nativeQuery = true)
    Optional<StudentView> getByStudentIdAndTiu(String studentId, String tiu);
}
