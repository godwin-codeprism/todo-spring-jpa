package com.codeprism.springboot.TodoMicroService.services;

import com.codeprism.springboot.TodoMicroService.entities.ToDo;

import java.util.List;

public interface ToDoService {
  List<ToDo> getToDos(String email);

  ToDo addToDo(ToDo toDo);

  void deleteTodo(Integer id);
}
