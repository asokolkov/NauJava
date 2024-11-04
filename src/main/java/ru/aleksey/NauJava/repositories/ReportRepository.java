package ru.aleksey.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.aleksey.NauJava.entities.Report;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long>
{
}
