package ru.job4j.url.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.model.Url;
import ru.job4j.url.model.UrlDTO;
import ru.job4j.url.service.UrlService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/url")
public class UrlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class.getSimpleName());

    private final UrlService urlService;

    private final ObjectMapper objectMapper;

    public UrlController(UrlService urlService, ObjectMapper objectMapper) {
        this.urlService = urlService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/convert")
    public ResponseEntity<Map<String, String>> convert(@RequestBody Url url) {
        urlService.convert(url);
        return new ResponseEntity<>(
                Map.of("code", url.getCode()),
                HttpStatus.OK
        );
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<?> redirect(@PathVariable("code") String code) {
        Url url = urlService.findAndIncrementCall(code);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.getOriginalUrl()));
        return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<UrlDTO>> statistic() {
        List<UrlDTO> rsl = new ArrayList<>();
        urlService.findAllByCurrentSite().forEach(
                        url -> rsl.add(
                                UrlDTO.of(url.getOriginalUrl(), url.getCallCounter())
                        )
        );
        return new ResponseEntity<>(rsl, HttpStatus.OK);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
