package ru.aleksey.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aleksey.NauJava.dtos.ReportDto;
import ru.aleksey.NauJava.services.ReportService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/reports")
public class ReportController
{
    @Autowired
    private ReportService reportService;

    @GetMapping("/{reportId}")
    public ModelAndView getUsersReport(@PathVariable long reportId)
    {
        var report = reportService.getReport(reportId);

        var modelAndView = new ModelAndView();
        modelAndView.setViewName("report");

        var reportContent = report != null ? report.getContent() : "No such report";

        modelAndView.addObject("reportContent", reportContent);

        return modelAndView;
    }

    @PostMapping("/generate")
    public ResponseEntity<ReportDto> generateReport()
    {
        var createdReportDto = reportService.createReport();

        CompletableFuture.runAsync(() -> reportService.generateAsyncReport(createdReportDto.getId()));

        return ResponseEntity.ok(createdReportDto);
    }
}
