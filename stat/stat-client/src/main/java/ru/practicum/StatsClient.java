package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class StatsClient extends BaseClient {


    @Autowired
    public StatsClient(@Value("${stat-server.url}") String serverUrl) {
        super(RestClient.create(serverUrl));

    }

    private static RestClient createRestClient(String serverUrl) {
        return RestClient.builder()
                .baseUrl(serverUrl)
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .build();
    }

    public ResponseEntity<Object> createHit(EndpointHitDTO hitDTO) {
        return post("/hits", hitDTO);
    }

    public ResponseEntity<Object> viewStats(StatsRequestDTO statsRequestDTO) {
        return get("/stats", statsRequestDTO);
    }

}
