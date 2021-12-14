package ru.job4j.url.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.url.model.Site;

public interface SiteRepository extends CrudRepository<Site, Integer> {

    boolean existsBySiteUrl(String siteUrl);

    Site findByLogin(String login);
}
