package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void testGetAllTasks() {
        //Given
        Task task1 = new Task(1L, "test title", "test content1");
        Task task2 = new Task(2L, "test title", "test content2");
        List<Task> tasksStub = new ArrayList<>();
        tasksStub.add(task1);
        tasksStub.add(task2);

        when(taskRepository.findAll()).thenReturn(tasksStub);

        //When
        List<Task> fetchedTaskList = dbService.getAllTasks();

        //Then
        assertEquals(2, fetchedTaskList.size());
    }

    @Test
    public void testGetAllTasksWithEmptyList() {
        //Given
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<Task> fetchedTaskList = dbService.getAllTasks();

        //Then
        assertEquals(0, fetchedTaskList.size());
    }

    @Test
    public void testGetTaskById() {
        //Given
        Task task = new Task(1L,"test title", "test content");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //When
        Optional<Task> fetchedTaskById = dbService.getTaskById(1L);
        long idTaskTest = fetchedTaskById.get().getId();

        //Then
        assertEquals(task .getId(), idTaskTest);
    }

    @Test
    public void testSaveTask() {
        //Given
        Task task = new Task(1L, " test title", "test content");
        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task testTask = dbService.saveTask(task);

        //Then
        assertEquals(task.getId(), testTask.getId());
        assertEquals(task.getTitle(), testTask.getTitle());
        assertEquals(task.getContent(), testTask.getContent());
    }

    @Test
    public void testGetTask() {
        //Given
        Task task = new Task(1L, "test title", "test content");

        when(dbService.getTask(1L)).thenReturn(Optional.of(task));

        //When
        Optional<Task> fetchedTaskById = dbService.getTask(1L);

        //Then
        assertTrue(fetchedTaskById.isPresent());
        assertEquals(task.getId(), fetchedTaskById.get().getId());
    }
}
