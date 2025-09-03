package ru.practicum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatsClient extends BaseClient {
    String serverUrl;

    @Autowired
    public StatsClient(@Value("${stat-server.url:http://stat-server:9090}") String serverUrl,
                       RestTemplateBuilder builder) {
        super(builder.build());
        this.serverUrl = serverUrl;
    }

    public ResponseEntity<Object> createHit(HttpServletRequest request, String appName) {
        String uri = request.getRequestURI();
        EndpointHitDTO dto = getEndpointHitDTO(request, appName);
        return post(serverUrl + "/hit", dto);

    }

    public List<ViewStatsDTO> viewStats(LocalDateTime start, LocalDateTime end,
                                            List<String> uris, Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> params = new HashMap<>();
        params.put("start", start.format(formatter));
        params.put("end", end.format(formatter));
        params.put("uris", uris != null ? String.join(",", uris) : "");
        params.put("unique", unique != null ? unique : false);

        ResponseEntity<Object> obj = get(serverUrl + "/stats", params);

        ObjectMapper mapper = new ObjectMapper();
        List<ViewStatsDTO> views = null;

        if (obj.getStatusCode().is2xxSuccessful() && obj.getBody() != null) {
            try {
                views = mapper.readValue(
                        mapper.writeValueAsString(obj.getBody()), new TypeReference<>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return views;
    }

    private EndpointHitDTO getEndpointHitDTO(HttpServletRequest request, String appName) {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        return EndpointHitDTO.builder()
                .app(appName)
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();
    }

    protected ResponseEntity<Object> get(String path, Map<String, Object> parameters) {
        return makeGetRequest(HttpMethod.GET, path, parameters, null);
    }

    private <T> ResponseEntity<Object> makeGetRequest(HttpMethod method, String path,
                                                      @Nullable Map<String, Object> parameters,
                                                      @Nullable T body) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(path);
        if (parameters != null) {
            parameters.forEach(builder::queryParam);
        }

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(builder.build().encode().toUri(),
                method, entity, Object.class);
    }
}
