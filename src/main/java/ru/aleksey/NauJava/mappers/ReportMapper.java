package ru.aleksey.NauJava.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aleksey.NauJava.dtos.ReportDto;
import ru.aleksey.NauJava.entities.Report;

@Mapper(componentModel = "spring")
public interface ReportMapper
{
    ReportMapper MAPPER = Mappers.getMapper(ReportMapper.class);

    ReportDto mapToDto(Report report);
}