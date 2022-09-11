package com.example.speakup.entity.report;

import com.example.speakup.entity.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
        Optional<List<Report>> findAllReportByTeacher(Teacher teacher);
}
