package ru.aleksey.NauJava.dtos;

import jakarta.persistence.Column;
import ru.aleksey.NauJava.enums.ReportStatus;

import java.time.OffsetDateTime;

public class ReportDto
{
    private long id;
    private String content;
    private ReportStatus status;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public ReportStatus getStatus()
    {
        return status;
    }

    public void setStatus(ReportStatus status)
    {
        this.status = status;
    }

    public OffsetDateTime getStartDate()
    {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate)
    {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate()
    {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate)
    {
        this.endDate = endDate;
    }
}
