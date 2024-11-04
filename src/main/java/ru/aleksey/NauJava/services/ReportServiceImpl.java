package ru.aleksey.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aleksey.NauJava.dtos.ReportDto;
import ru.aleksey.NauJava.entities.Report;
import ru.aleksey.NauJava.entities.User;
import ru.aleksey.NauJava.enums.ReportStatus;
import ru.aleksey.NauJava.mappers.ReportMapper;
import ru.aleksey.NauJava.repositories.ReportRepository;
import ru.aleksey.NauJava.repositories.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
public class ReportServiceImpl implements ReportService
{
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;

    public ReportDto getReport(Long reportId)
    {
        var report = reportRepository.findById(reportId).orElse(null);

        var reportDto = ReportMapper.MAPPER.mapToDto(report);

        return reportDto;
    }

    public ReportDto createReport()
    {
        var report = new Report();
        report.setStatus(ReportStatus.CREATED);
        report.setContent("");

        reportRepository.save(report);

        var reportDto = ReportMapper.MAPPER.mapToDto(report);

        return reportDto;
    }

    public CompletableFuture<ReportDto> generateAsyncReport(long reportId)
    {
        var executor = Executors.newFixedThreadPool(2);
        try
        {
            var startTime = System.currentTimeMillis();

            var usersListFutureStartTime = System.currentTimeMillis();
            var usersListFuture = CompletableFuture.supplyAsync(() -> (List<User>) userRepository.findAll(), executor);
            var usersListFutureEndTime = System.currentTimeMillis();
            var usersListFutureElapsedTime = usersListFutureEndTime - usersListFutureStartTime;

            var usersAmountFutureStartTime = System.currentTimeMillis();
            var usersAmountFuture = CompletableFuture.supplyAsync(() -> ((List<User>) userRepository.findAll()).size(), executor);
            var usersAmountFutureEndTime = System.currentTimeMillis();
            var usersAmountFutureElapsedTime = usersAmountFutureEndTime - usersAmountFutureStartTime;

            var usersList = usersListFuture.join();
            var usersAmount = usersAmountFuture.join();

            var reportContent = new StringBuilder();

            reportContent.append("<article><h1>Список пользователей</h1><ul>");
            usersList.forEach(user -> reportContent.append(String.format("<li>%s</li>", user.getLogin())));
            reportContent.append("</ul></article>");

            reportContent.append("<article><h1>Статистика</h1><ul>");
            reportContent.append(String.format("<li>Количество пользователей - %s</li>", usersAmount));
            reportContent.append(String.format("<li>Время получения пользователей - %s</li>", usersListFutureElapsedTime));
            reportContent.append(String.format("<li>Время подсчета пользователей - %s</li>", usersAmountFutureElapsedTime));

            var endTime = System.currentTimeMillis();
            var elapsedTime = endTime - startTime;

            reportContent.append(String.format("<li>Время генерации отчета - %s</li>", elapsedTime));

            reportContent.append("</ul></article>");

            var report = reportRepository.findById(reportId).orElseThrow();
            report.setStatus(ReportStatus.COMPLETED);
            report.setContent(reportContent.toString());

            reportRepository.save(report);

            var reportDto = ReportMapper.MAPPER.mapToDto(report);

            return CompletableFuture.completedFuture(reportDto);
        }
        catch (Exception e)
        {
            var report = new Report();
            report.setId(reportId);
            report.setStatus(ReportStatus.ERROR);
            report.setContent("Got exception during report generation");

            reportRepository.save(report);

            var reportDto = ReportMapper.MAPPER.mapToDto(report);

            return CompletableFuture.completedFuture(reportDto);
        }
        finally
        {
            executor.shutdown();
        }
    }
}
