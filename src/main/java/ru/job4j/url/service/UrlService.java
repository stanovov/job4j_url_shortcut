package ru.job4j.url.service;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.url.model.Site;
import ru.job4j.url.model.Url;
import ru.job4j.url.repository.SiteRepository;
import ru.job4j.url.repository.UrlRepository;

import java.util.List;

@Service
public class UrlService {

    private final UrlValidator urlValidator;

    private final SiteRepository siteRepository;

    private final UrlRepository urlRepository;

    private final RandomStringGenerator stringGenerator;

    public UrlService(UrlValidator urlValidator, SiteRepository siteRepository,
                      UrlRepository urlRepository, RandomStringGenerator stringGenerator) {
        this.urlValidator = urlValidator;
        this.siteRepository = siteRepository;
        this.urlRepository = urlRepository;
        this.stringGenerator = stringGenerator;
    }

    public void convert(Url url) {
        if (url.getOriginalUrl() == null || url.getOriginalUrl().isEmpty()) {
            throw new NullPointerException("Url mustn't be empty");
        }
        if (!urlValidator.isValid(url.getOriginalUrl())) {
            throw new IllegalArgumentException("URL is invalid");
        }
        Url urlInDB = urlRepository.findByOriginalUrl(url.getOriginalUrl());
        if (urlInDB != null) {
            url.setCode(urlInDB.getCode());
            return;
        }
        Site site = siteRepository.findByLogin(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        checkUrlRefersSite(url, site);
        url.setCode(stringGenerator.generate(7));
        url.setSite(site);
        urlRepository.save(url);
    }

    public Url findAndIncrementCall(String code) {
        Url url = urlRepository.findByCode(code).orElseThrow(
                () -> new IllegalArgumentException("Unable to redirect. No such code exists in the database.")
        );
        urlRepository.incrementCall(code);
        return url;
    }

    public List<Url> findAllByCurrentSite() {
        Site site = siteRepository.findByLogin(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        return urlRepository.findAllBySite(site);
    }

    private void checkUrlRefersSite(Url url, Site site) {
        String searchCharacters = "://";
        String withoutProtocol = url.getOriginalUrl()
                .substring(url.getOriginalUrl().indexOf(searchCharacters) + 3);
        boolean rsl = withoutProtocol.startsWith(site.getSiteUrl());
        if (!rsl) {
            throw new IllegalArgumentException("URL is not related to the site");
        }
    }
}
