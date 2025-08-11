package ru.practicum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

public class StatsClient extends BaseClient{
    String appName;

    public StatsClient(@Value("${stat-server.url}") String serverUrl,
                       @Value("${appName}") String appName) {
        super(RestClient.create(serverUrl));
        this.appName = appName;
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
