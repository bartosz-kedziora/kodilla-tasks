package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloListDto> testList = new ArrayList<>();
        testList.add(new TrelloListDto("1", "TestList 1", false));
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(new TrelloBoardDto("1", "TestBoard 1", testList));
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);
        //Then
        assertEquals("TestBoard 1", trelloBoards.get(0).getName());
        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals("TestList 1", trelloBoards.get(0).getLists().get(0).getName());
        assertEquals(1, trelloBoards.size());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloList> testList = new ArrayList<>();
        testList.add(new TrelloList("1", "TestList 1", false));
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "TestBoard 1", testList));
        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals("TestBoard 1", trelloBoardsDto.get(0).getName());
        assertEquals("1", trelloBoardsDto.get(0).getId());
        assertEquals("TestList 1", trelloBoardsDto.get(0).getLists().get(0).getName());
        assertEquals(1, trelloBoardsDto.size());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> testListDto = new ArrayList<>();
        testListDto.add(new TrelloListDto("1", "Test list 1", false));
        testListDto.add(new TrelloListDto("2","Test list 2", false));
        //When
        List<TrelloList> testLists = trelloMapper.mapToList(testListDto);
        //Then
        assertEquals(2, testLists.size());
        assertEquals("1",testLists.get(0).getId());
        assertEquals("Test list 2", testLists.get(1).getName());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> testList = new ArrayList<>();
        testList.add(new TrelloList("1", "Test list 1", false));
        testList.add(new TrelloList("2","Test list 2", false));
        //When
        List<TrelloListDto> testListsDto = trelloMapper.mapToListDto(testList);
        //Then
        assertEquals(2, testListsDto.size());
        assertEquals("1",testListsDto.get(0).getId());
        assertEquals("Test list 2", testListsDto.get(1).getName());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("TestCard","Test desc","top","1");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("TestCard", trelloCard.getName());
        assertEquals("Test desc", trelloCard.getDescription());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("TestCard","Test desc","top","1");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("1", trelloCardDto.getListId());
        assertEquals("top", trelloCardDto.getPos());
    }
}