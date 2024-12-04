package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.dtos.ReportCreateDto;
import ru.aleksey.NauJava.dtos.ReportDto;

import java.util.concurrent.CompletableFuture;

public interface ReportService
{
    ReportDto getReport(Long reportId);

    ReportDto createReport(String username, ReportCreateDto reportCreateDto);

    CompletableFuture<ReportDto> generateAsyncReport(String username, ReportDto reportDto);
}
