package ru.aleksey.NauJava.entities;

import jakarta.persistence.*;
import ru.aleksey.NauJava.enums.ReportStatus;

import java.time.OffsetDateTime;

@Entity
@Table(name = "report")
public class Report
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition="TEXT", length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @Column
    private OffsetDateTime startDate;

    @Column
    private OffsetDateTime endDate;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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
