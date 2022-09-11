package com.example.speakup.entity.groupReport;

import com.example.speakup.entity.groups.Group;
import com.example.speakup.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupReportRepository extends JpaRepository<GroupReport,Integer> {
    Optional<List<GroupReport>> findAllByReport(Report report);
    Optional<GroupReport> findByGroupAndAndReport(Group group,Report report);
}
