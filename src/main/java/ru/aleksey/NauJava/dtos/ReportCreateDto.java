package ru.aleksey.NauJava.dtos;

import java.time.OffsetDateTime;

public class ReportCreateDto
{
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

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
