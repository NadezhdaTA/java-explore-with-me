package ru.practicum;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatsClient1 {
    private final RestTemplate restTemplate;

    private final String serverUrl;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public StatsClient1(@Value("${stats.server.url:http://stat-server:9090}") String serverUrl,
                       RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.serverUrl = serverUrl;


    }

    public void createHit(HttpServletRequest request, String appName) {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        post("/hit", createDto(uri, ip, appName));
    }

    public ResponseEntity<Object> viewStats(LocalDateTime start, LocalDateTime end,
                                            List<String> uris, Boolean unique) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start.format(formatter));
        params.put("end", end.format(formatter));
        params.put("uris", uris != null ? String.join(",", uris) : "");
        params.put("unique", unique != null ? unique : false);

        return get("/stats", params);
    }

    private ResponseEntity<Object> get(String path, Map<String, Object> parameters) {
        return makeRequest(HttpMethod.GET, path, parameters, null);
    }

    private ResponseEntity<Object> post(String path, Object body) {
        return makeRequest(HttpMethod.POST, path, null, body);
    }

    private ResponseEntity<Object> makeRequest(HttpMethod method, String path,
                                               Map<String, Object> parameters, Object body) {
        String url = serverUrl + path;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (parameters != null) {
            parameters.forEach(builder::queryParam);
        }

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                builder.build().encode().toUri(),
                method,
                entity,
                Object.class
        );

    }

    private EndpointHitDTO createDto(String uri, String ip, String appName) {
        return EndpointHitDTO.builder()
                .app(appName)
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
