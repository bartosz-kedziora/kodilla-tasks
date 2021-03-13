package com.crud.tasks.trello.config;

import org.springframework.beans.factory.annotation.Value;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TrelloConfig {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.api.username}")
    private String trelloUser;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;
}
