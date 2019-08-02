package com.codeprism.springboot.TodoMicroService;

import com.codeprism.springboot.TodoMicroService.daos.ToDoDao;
import com.codeprism.springboot.TodoMicroService.daos.UserDao;
import com.codeprism.springboot.TodoMicroService.entities.ToDo;
import com.codeprism.springboot.TodoMicroService.entities.User;
import com.codeprism.springboot.TodoMicroService.utilities.EncryptionUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@Log
@SpringBootApplication
public class TodoMicroServiceApplication implements CommandLineRunner {

	@Autowired
	UserDao userDao;

	@Autowired
	ToDoDao toDoDao;

	@Autowired
	EncryptionUtils encryptionUtils;

	public static void main(String[] args) {
		SpringApplication.run(TodoMicroServiceApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("Lets fill H2 in memory database");

		String encryptedPwd;
		encryptedPwd = encryptionUtils.encrypt("godwinpassword");
		userDao.save( new User("godwin@godwinvc.com", "Godwin", encryptedPwd));

		encryptedPwd = encryptionUtils.encrypt("vinnypassword");
		userDao.save( new User("vinny@godwinvc.com", "Vinny", encryptedPwd));

		encryptedPwd = encryptionUtils.encrypt("carolepassword");
		userDao.save( new User("carole@godwinvc.com", "Carole", encryptedPwd));


		toDoDao.save( new ToDo(1, "Learn MicroServices", new Date(), "high",  "godwin@godwinvc.com"));
		toDoDao.save( new ToDo(null, "Learn Flutter", new Date(), "low",  "godwin@godwinvc.com"));

		toDoDao.save( new ToDo(3, "Learn Spring Boot", new Date(), "high",  "vinny@godwinvc.com"));
		toDoDao.save( new ToDo(null, "Learn JavaScript", new Date(), "low",  "vinny@godwinvc.com"));

		toDoDao.save( new ToDo(5, "Learn JPA", new Date(), "high",  "carole@godwinvc.com"));
		toDoDao.save( new ToDo(null, "Learn React Native", new Date(), "low",  "carole@godwinvc.com"));


		log.info("Finished filling the database");
	}

}
