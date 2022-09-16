package com.example.speakup.entity.groupReport;

import com.example.speakup.entity.groups.Group;
import com.example.speakup.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupReportRepository extends JpaRepository<GroupReport,Integer> {

    Optional<List<GroupReport>> findAllByReport(Report report);

    Optional<GroupReport> findByGroupAndAndReport(Group group,Report report);

    @Query(value = "select sum(g.price)\n" +
            "from group_report gr\n" +
            "inner join report r on r.id=gr.report_id\n" +
            "inner join groups g on g.id=gr.group_id\n" +
            "where r.teacher_id = :id and r.dates between :startDate and :endDate",nativeQuery = true)
    Integer sumBetweenDate(@Param("id") Integer id, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
}
