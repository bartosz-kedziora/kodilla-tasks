package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {

    @InjectMocks
    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    public void mapToTask() {
        // Given
        TaskDto taskDto = new TaskDto(1L, "test task title", "test task content");

        // When
        Task fetchedTask = taskMapper.mapToTask(taskDto);
        Long expectedId = 1L;

        //Then
        assertEquals(expectedId , fetchedTask.getId());
        assertEquals("test task title", fetchedTask.getTitle());
        assertEquals("test task content", fetchedTask.getContent());
    }

    @Test
    public void mapToTaskDto() {
        // Given
        Task task = new Task(1L, "test task title", "test task content");

        // When
        TaskDto fetchedTaskDto = taskMapper.mapToTaskDto(task);
        Long expectedId = 1L;

        //Then
        assertEquals(expectedId , fetchedTaskDto.getId());
        assertEquals("test task title", fetchedTaskDto.getTitle());
        assertEquals("test task content", fetchedTaskDto.getContent());
    }

    @Test
    public void mapToTaskDtoList() {
        // Given
        Task task = new Task(1L, "test task title", "test task content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        // When
        List<TaskDto> fetchedTaskDtoList = taskMapper.mapToTaskDtoList(taskList);
        Long expectedId = 1L;
        List<TaskDto> fetchedEmptyTaskDtoList = taskMapper.mapToTaskDtoList(new ArrayList<>());

        //Then
        assertEquals(1 , fetchedTaskDtoList.size());
        assertEquals(expectedId, fetchedTaskDtoList.get(0).getId());
        assertEquals("test task title", fetchedTaskDtoList.get(0).getTitle());
        assertEquals("test task content", fetchedTaskDtoList.get(0).getContent());
        assertEquals(0, fetchedEmptyTaskDtoList.size());
    }
}
