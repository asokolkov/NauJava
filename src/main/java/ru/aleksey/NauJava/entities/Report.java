package ru.aleksey.NauJava.entities;

import jakarta.persistence.*;
import ru.aleksey.NauJava.enums.ReportStatus;

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
}
