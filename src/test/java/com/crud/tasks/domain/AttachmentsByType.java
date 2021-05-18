package com.crud.tasks.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)

class AttachmentsByTypeTest {

    @Test
    public void testGetTrello() {
        //Given
        Trello trelloTrelloDto = new Trello(1,1);
        AttachmentsByType attachmentsDto = new AttachmentsByType(trelloTrelloDto);
        //When
        //Then
        assertEquals(1,attachmentsDto.getTrello().getBoard());
        assertEquals(1,attachmentsDto.getTrello().getCard());
    }
}
