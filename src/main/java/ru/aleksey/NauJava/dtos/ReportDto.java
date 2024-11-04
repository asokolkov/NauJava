package ru.aleksey.NauJava.dtos;

import ru.aleksey.NauJava.enums.ReportStatus;

public class ReportDto
{
    private long id;
    private String content;
    private ReportStatus status;

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
}
