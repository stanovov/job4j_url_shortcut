package ru.job4j.url.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.url.model.Site;
import ru.job4j.url.service.SiteService;

import java.util.Map;

@RestController
@RequestMapping("/site")
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> registration(@RequestBody Site site) {
        return new ResponseEntity<>(
                siteService.registration(site),
                HttpStatus.CREATED
        );
    }
}
