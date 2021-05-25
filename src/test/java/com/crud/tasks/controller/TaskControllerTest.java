package com.crud.tasks.controller;

import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)

class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchEmptyTasksList() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        List<TaskDto> taskDto = new ArrayList<>();

        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDto);
        //When &Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status()).is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldFetchTasksList() throws Exception{
        //Given
        List<TaskDto> taskDto = new ArrayList<>();
        taskDto.add(new TaskDto(1L, "test", "test 1"));

        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDto);
        //When &Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status()).is(200))
                .andExpect((MockMvcResultMatchers.jsonPath("$.[0].title",Matchers.is("test"))));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(1L, "test", "test 1");
        TaskDto taskDto = new TaskDto(1L, "test", "test 1");
        //When &Then
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When &Then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent);
        mockMvc.perform(requestBuilder)
                .andExpect((MockMvcResultMatchers.status()).is2xxSuccessful());
    }

    @Test
    public void shouldUpdateTask()throws Exception{
        // Given
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");
        TaskDto updatedTask = new TaskDto(1L, "updated title", "updated content");

        when(taskMapper.mapToTaskDto(taskMapper.mapToTask(taskDto))).thenReturn(updatedTask);

        Gson gson = new Gson();
        String cont = gson.toJson(taskDto);


        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(cont))
                .andExpect((MockMvcResultMatchers.status()).is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("updated title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("updated content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {

        //Given
        Task task = new Task(1L,"Test","Test content");
        TaskDto taskDto = new TaskDto(1L,"Test","Test content");
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/task/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect((MockMvcResultMatchers.status()).is(200));
    }
}
