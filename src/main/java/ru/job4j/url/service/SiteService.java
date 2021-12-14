package ru.job4j.url.service;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.url.model.Site;
import ru.job4j.url.repository.SiteRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SiteService {

    private final SiteRepository siteRepository;

    private final BCryptPasswordEncoder encoder;

    private final RandomStringGenerator stringGenerator;

    public SiteService(SiteRepository siteRepository, BCryptPasswordEncoder encoder, RandomStringGenerator stringGenerator) {
        this.siteRepository = siteRepository;
        this.encoder = encoder;
        this.stringGenerator = stringGenerator;
    }

    public Map<String, String> registration(Site site) {
        if (site.getSiteUrl() == null || site.getSiteUrl().isEmpty()) {
            throw new NullPointerException("Site URL mustn't be empty");
        }
        Map<String, String> dto = new LinkedHashMap<>();
        boolean registration = !siteRepository.existsBySiteUrl(site.getSiteUrl());
        dto.put("registration", Boolean.toString(registration));
        if (!registration) {
            return dto;
        }
        site.setLogin(stringGenerator.generate(15));
        site.setPassword(stringGenerator.generate(30));
        dto.put("login", site.getLogin());
        dto.put("password", site.getPassword());
        save(site);
        return dto;
    }

    public void save(Site site) {
        site.setPassword(encoder.encode(site.getPassword()));
        siteRepository.save(site);
    }
}
