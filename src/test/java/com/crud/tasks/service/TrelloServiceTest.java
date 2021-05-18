package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void testFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "test list name", false));

        List<TrelloBoardDto> trelloBoardDtoStub = new ArrayList<>();
        trelloBoardDtoStub.add(new TrelloBoardDto("1", "test board name", trelloListDto));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoStub);

        //When
        List<TrelloBoardDto> fetchedTrelloBoardDto = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(1, fetchedTrelloBoardDto.size());
        assertEquals("test board name", fetchedTrelloBoardDto.get(0).getName());
    }

    @Test
    public void testFetchEmptyTrelloBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDto);

        //When
        List<TrelloBoardDto> fetchedEmptyTrelloBoardDto = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(0, fetchedEmptyTrelloBoardDto.size());
    }

    @Test
    public void createTrelloCardTest() {
        //Given
        Trello trello = new Trello(1, 1);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);
        TrelloBadges trelloBadges = new TrelloBadges(1, attachmentsByType);
        CreatedTrelloCardDto createdTrelloCardDtoStub = new CreatedTrelloCardDto("1", "name", "test.com/test", trelloBadges);
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "pos", "1");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDtoStub);

        //When
        CreatedTrelloCardDto createdTrelloCardDto = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("1", createdTrelloCardDto.getId());
        assertEquals("name", createdTrelloCardDto.getName());
        assertEquals("test.com/test", createdTrelloCardDto.getShortUrl());
    }
}
