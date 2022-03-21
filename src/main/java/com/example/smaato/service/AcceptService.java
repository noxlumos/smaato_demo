package com.example.smaato.service;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AcceptService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Set<Integer> set = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    private final PrintWriter statusWriter;
    private final PrintWriter counterWriter;

    public AcceptService() throws IOException {
        this.statusWriter = new PrintWriter("statusLog.txt", StandardCharsets.UTF_8);
        this.counterWriter = new PrintWriter("counterLog.txt", StandardCharsets.UTF_8);
    }

    public void accept(Integer id, String endpoint) {
        set.add(id);
        if(endpoint != null) {
            String urlTemplate = UriComponentsBuilder.fromHttpUrl(endpoint)
                    .queryParam("count", set.size())
                    .encode()
                    .toUriString();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    urlTemplate,
                    String.class);
            writeToFile(responseEntity.getStatusCode().toString(), statusWriter);
        }
    }

    @Scheduled(cron = "0 * * * * *")
    private void logUniqueIdCount(){
        writeToFile(String.valueOf(set.size()), counterWriter);
        set.clear();
    }

    public void writeToFile(String line, PrintWriter writer) {
        synchronized (writer) {
            writer.println(line);
            writer.flush();
        }
    }
}
