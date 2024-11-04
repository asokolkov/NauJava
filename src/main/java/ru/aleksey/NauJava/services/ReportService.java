package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.dtos.ReportDto;

import java.util.concurrent.CompletableFuture;

public interface ReportService
{
    ReportDto getReport(Long reportId);

    ReportDto createReport();

    CompletableFuture<ReportDto> generateAsyncReport(long reportId);
}
