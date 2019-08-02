package com.codeprism.springboot.TodoMicroService.daos;

import com.codeprism.springboot.TodoMicroService.entities.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoDao extends JpaRepository<ToDo, Integer> {
  List<ToDo> findByFkUser(String email);
}
