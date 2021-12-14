package ru.job4j.url.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.url.model.Site;
import ru.job4j.url.model.Url;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {

    Url findByOriginalUrl(String originalUrl);

    Optional<Url> findByCode(String code);

    @Transactional
    @Modifying
    @Query("update Url u set u.callCounter = u.callCounter + 1 where u.code = ?1")
    void incrementCall(String code);

    List<Url> findAllBySite(Site site);
}
