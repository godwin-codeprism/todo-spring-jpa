package com.codeprism.springboot.TodoMicroService.services;

import com.codeprism.springboot.TodoMicroService.daos.ToDoDao;
import com.codeprism.springboot.TodoMicroService.entities.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoServiceImplementation implements ToDoService {

  @Autowired
  ToDoDao toDoDao;

  @Override
  public List<ToDo> getToDos(String email){
    return toDoDao.findByFkUser(email);
  }

  @Override
  public  ToDo addToDo(ToDo toDo){
    return toDoDao.save(toDo);
  }

  @Override
  public void deleteTodo(Integer id){
    toDoDao.delete(id);
  }
}
