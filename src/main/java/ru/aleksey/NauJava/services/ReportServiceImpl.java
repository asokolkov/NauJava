package ru.aleksey.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksey.NauJava.dtos.ReportCreateDto;
import ru.aleksey.NauJava.dtos.ReportDto;
import ru.aleksey.NauJava.entities.Product;
import ru.aleksey.NauJava.entities.Report;
import ru.aleksey.NauJava.entities.UserProduct;
import ru.aleksey.NauJava.enums.ReportStatus;
import ru.aleksey.NauJava.mappers.ReportMapper;
import ru.aleksey.NauJava.repositories.ReportRepository;
import ru.aleksey.NauJava.repositories.UserRepository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
public class ReportServiceImpl implements ReportService
{
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ReportDto getReport(Long reportId)
    {
        var report = reportRepository.findById(reportId).orElse(null);

        var reportDto = ReportMapper.MAPPER.mapToDto(report);

        return reportDto;
    }

    @Override
    public ReportDto createReport(String username, ReportCreateDto reportCreateDto)
    {
        var user = userRepository.findByLogin(username);
        if (user == null)
        {
            return null;
        }

        var report = new Report();
        report.setStatus(ReportStatus.CREATED);
        report.setContent("");
        report.setStartDate(reportCreateDto.getStartDate());
        report.setEndDate(reportCreateDto.getEndDate());

        reportRepository.save(report);

        user.getReports().add(report);

        userRepository.save(user);

        var reportDto = ReportMapper.MAPPER.mapToDto(report);

        return reportDto;
    }

    @Override
    @Transactional
    public CompletableFuture<ReportDto> generateAsyncReport(String username, ReportDto reportDto)
    {
        var executor = Executors.newFixedThreadPool(2);
        try
        {
            var startTime = System.currentTimeMillis();

            var user = userRepository.findByLogin(username);
            if (user == null)
            {
                throw new Exception();
            }

            var productsListFutureStartTime = System.currentTimeMillis();
            var productsListFuture = CompletableFuture.supplyAsync(() -> user
                .getProducts()
                .stream()
                .filter(product -> product.getEatenAt().isAfter(reportDto.getStartDate()) && product.getEatenAt().isBefore(reportDto.getEndDate()))
                .map(UserProduct::getProduct)
                .toList(), executor);
            var productsListFutureEndTime = System.currentTimeMillis();
            var productsListFutureElapsedTime = productsListFutureEndTime - productsListFutureStartTime;

            var productsAmountFutureStartTime = System.currentTimeMillis();
            var productsAmountFuture = CompletableFuture.supplyAsync(() -> user
                .getProducts()
                .stream()
                .filter(product -> product.getEatenAt().isAfter(reportDto.getStartDate()) && product.getEatenAt().isBefore(reportDto.getEndDate()))
                .toList()
                .size(), executor);
            var productsAmountFutureEndTime = System.currentTimeMillis();
            var productsAmountFutureElapsedTime = productsAmountFutureEndTime - productsAmountFutureStartTime;

            var productsList = productsListFuture.join();
            var productsAmount = productsAmountFuture.join();
            var caloriesSum = productsList
                .stream()
                .mapToInt(Product::getCalories)
                .sum();

            var reportContent = new StringBuilder();

            reportContent.append("<h1>Список продуктов</h1><ul>");
            productsList.forEach(product -> reportContent.append(String.format("<li>%s - %s</li>", product.getName(), product.getCalories())));
            reportContent.append("</ul>");

            reportContent.append("<h1>Статистика</h1><ul>");
            reportContent.append(String.format("<li>Количество продуктов - %s</li>", productsAmount));
            reportContent.append(String.format("<li>Количество калорий - %s</li>", caloriesSum));
            reportContent.append(String.format("<li>Время получения продуктов в миллисекундах - %s</li>", productsListFutureElapsedTime));
            reportContent.append(String.format("<li>Время подсчета продуктов в миллисекундах - %s</li>", productsAmountFutureElapsedTime));

            var endTime = System.currentTimeMillis();
            var elapsedTime = endTime - startTime;

            reportContent.append(String.format("<li>Время генерации отчета в миллисекундах - %s</li>", elapsedTime));

            reportContent.append("</ul>");

            var report = reportRepository.findById(reportDto.getId()).orElseThrow();
            report.setStatus(ReportStatus.COMPLETED);
            report.setContent(reportContent.toString());

            reportRepository.save(report);

            var resultReportDto = ReportMapper.MAPPER.mapToDto(report);

            return CompletableFuture.completedFuture(resultReportDto);
        }
        catch (Exception e)
        {
            var report = reportRepository.findById(reportDto.getId()).orElse(new Report());

            report.setId(reportDto.getId());
            report.setStatus(ReportStatus.ERROR);
            report.setContent("<h1>Got exception during report generation</h1>");

            reportRepository.save(report);

            var resultReportDto = ReportMapper.MAPPER.mapToDto(report);

            return CompletableFuture.completedFuture(resultReportDto);
        }
        finally
        {
            executor.shutdown();
        }
    }
}
