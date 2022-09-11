package com.example.speakup.controller.reportController;

import com.example.speakup.auth.CustomUserDetails;
import com.example.speakup.entity.groupReport.GroupReport;
import com.example.speakup.entity.groupReport.GroupReportRepository;
import com.example.speakup.entity.groups.Group;
import com.example.speakup.entity.groups.GroupRepository;
import com.example.speakup.entity.report.Report;
import com.example.speakup.entity.report.ReportRepository;
import com.example.speakup.entity.teacher.Teacher;
import com.example.speakup.entity.teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final TeacherRepository teacherRepository;
    private final GroupReportRepository groupReportRepository;
    private final GroupRepository groupRepository;

    public List<Report> getReportByTeacher(Authentication authentication){
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        Teacher teacher = teacherRepository.findById(userId).get();
        List<Report> reports = reportRepository.findAllReportByTeacher(teacher).get();
        return reports;
    }

    public Report getById(Integer id){
        return reportRepository.findById(id).get();
    }

    public void createReport(HttpServletResponse resp, Authentication authentication){
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

        Teacher teacher = teacherRepository.findById(userId).get();

        Report report = new Report();
        report.setNumberOfLessons(0);
        report.setTotalPrice(0);
        report.setTeacher(teacher);
        report.setDates(LocalDate.now());

        reportRepository.save(report);

        try {
            resp.sendRedirect("/report");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteReport(Map<String,String> map, HttpServletResponse resp){
        int id = Integer.parseInt(map.get("id"));
        Optional<Report> byId = reportRepository.findById(id);
        reportRepository.delete(byId.get());

        try {
            resp.sendRedirect("/report");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Group> getAllGroupByReport(Integer idReport){
        Report report = reportRepository.findById(idReport).get();
        List<GroupReport> list = groupReportRepository.findAllByReport(report).get();
        List<Group> groupList = new ArrayList<>();
        for (GroupReport groupReport:list) {
            groupList.add(groupReport.getGroup());
        }
        return groupList;
    }

    public List<Group> getAllGroupByTeacher(Authentication authentication){
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        Teacher teacher = teacherRepository.findById(userId).get();
        return groupRepository.findAllByTeacher(teacher).get();
    }

    public void createGroupReport(Map<String,String> map){
        int reportId = Integer.parseInt(map.get("reportId"));
        int groupId = Integer.parseInt(map.get("groupId"));
        Report report = reportRepository.findById(reportId).get();
        Group group = groupRepository.findById(groupId).get();

        report.setNumberOfLessons(report.getNumberOfLessons() + 1);
        report.setTotalPrice(report.getTotalPrice() + group.getPrice());
        reportRepository.save(report);

        GroupReport groupReport = new GroupReport();
        groupReport.setGroup(group);
        groupReport.setReport(report);

        groupReportRepository.save(groupReport);
    }

    public void deleteGroupByReport(Map<String,String> map){
        int reportId = Integer.parseInt(map.get("reportId"));
        int groupId = Integer.parseInt(map.get("groupId"));

        Report report = reportRepository.findById(reportId).get();
        Group group = groupRepository.findById(groupId).get();

        report.setNumberOfLessons(report.getNumberOfLessons() - 1);
        report.setTotalPrice(report.getTotalPrice() - group.getPrice());

        reportRepository.save(report);

        GroupReport groupReport = groupReportRepository.findByGroupAndAndReport(group,report).get();

        groupReportRepository.delete(groupReport);
    }


}
