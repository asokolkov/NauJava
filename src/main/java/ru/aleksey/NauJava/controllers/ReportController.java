package ru.aleksey.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.aleksey.NauJava.dtos.ReportCreateDto;
import ru.aleksey.NauJava.dtos.ReportDto;
import ru.aleksey.NauJava.services.ReportService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/reports")
public class ReportController
{
    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportDto> createReport(@RequestBody ReportCreateDto reportCreateDto, Authentication authentication)
    {
        var username = authentication.getName();

        var reportDto = reportService.createReport(username, reportCreateDto);

        CompletableFuture.runAsync(() -> reportService.generateAsyncReport(username, reportDto));

        return reportDto != null
            ? ResponseEntity.status(HttpStatus.CREATED).body(reportDto)
            : ResponseEntity.notFound().build();
    }
}
