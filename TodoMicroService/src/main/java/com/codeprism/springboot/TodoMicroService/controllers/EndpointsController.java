package com.codeprism.springboot.TodoMicroService.controllers;

import com.codeprism.springboot.TodoMicroService.entities.ToDo;
import com.codeprism.springboot.TodoMicroService.entities.User;
import com.codeprism.springboot.TodoMicroService.services.LoginService;
import com.codeprism.springboot.TodoMicroService.services.ToDoService;
import com.codeprism.springboot.TodoMicroService.utilities.JsonResponseBody;
import com.codeprism.springboot.TodoMicroService.utilities.ToDoValidator;
import com.codeprism.springboot.TodoMicroService.utilities.UserNotInDBExecption;
import com.codeprism.springboot.TodoMicroService.utilities.UserNotLoggedinException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class EndpointsController {

  @Autowired
  LoginService loginService;

  @Autowired
  ToDoService toDoService;

  @RequestMapping("/hello")
  public String sayHello() {
    return "hello";
  }

  @RequestMapping("/settodo")
  public String setTodo(ToDo toDo) {
    return "ToDo Description: " + toDo.getDescription() + ", ToDo Priority: " + toDo.getPriority();
  }

  @RequestMapping("/settodostrict")
  public String setTodoStrict(@Valid ToDo toDo, BindingResult result) {
    ToDoValidator validator = new ToDoValidator();
    validator.validate(toDo, result);
    if (result.hasErrors()) {
      return "Missing required parameters: " + result.toString();
    }
    return "ToDo Description: " + toDo.getDescription() + ", ToDo Priority: " + toDo.getPriority();
  }

  @RequestMapping(value = "/login", method = POST)
  public ResponseEntity<JsonResponseBody> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
    try {
      Optional<User> user = loginService.getUserFormDB(email, password);
      User user1 = user.get();
      String jwt = loginService.createJwt(email, user1.getName(), new Date());
      return ResponseEntity.status(HttpStatus.OK)
          .header("jwt", jwt)
          .body(new JsonResponseBody(HttpStatus.OK.value(), "Login Successful!"));
    } catch (UserNotInDBExecption unknownUserException) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "FORBIDDEN: Unknown user"));
    } catch (UnsupportedEncodingException unsupportedDateFormatException) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST: Date format not supported"));
    }
  }

  @RequestMapping(value = "/showToDos", method = GET)
  public ResponseEntity<JsonResponseBody> showToDos(HttpServletRequest request) {
    try {
      Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
      return ResponseEntity.status(HttpStatus.OK)
          .body(new JsonResponseBody(HttpStatus.OK.value(), toDoService.getToDos((String) userData.get("email"))));

    } catch (UnsupportedEncodingException unsupportedDateFormatException) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "BAD REQUEST"));
    } catch (UserNotLoggedinException userNotLoggedinException) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "User not logged in"));
    } catch (ExpiredJwtException expiredJwtException) {
      return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
          .body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "JWT Token Expired"));
    }
  }

  @RequestMapping(value = "/newToDo", method = POST)
  public ResponseEntity<JsonResponseBody> newToDo(HttpServletRequest req, @Valid ToDo toDo, BindingResult result) {
    ToDoValidator validator = new ToDoValidator();
    validator.validate(toDo, result);
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Invalid Todo: " + result.toString()));
    }
    try {
      loginService.verifyJwtAndGetData(req);
      return ResponseEntity.status(HttpStatus.OK)
          .body(new JsonResponseBody(HttpStatus.OK.value(), toDoService.addToDo(toDo)));
    } catch (UnsupportedEncodingException unsupportedDateFormatException) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "BAD REQUEST"));
    } catch (UserNotLoggedinException userNotLoggedinException) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "User not logged in"));
    } catch (ExpiredJwtException expiredJwtException) {
      return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
          .body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "JWT Token Expired"));
    }
  }

  @RequestMapping(value = "/deleteToDo/{id}", method = POST)
  public ResponseEntity<JsonResponseBody> deletToDo(HttpServletRequest req, @PathVariable(name = "id") int toDoId) {
    try {
      loginService.verifyJwtAndGetData(req);
      toDoService.deleteTodo(toDoId);
      return ResponseEntity.status(HttpStatus.OK)
          .body(new JsonResponseBody(HttpStatus.OK.value(), "To do deleted successfully"));
    } catch (UnsupportedEncodingException unsupportedDateFormatException) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "BAD REQUEST"));
    } catch (UserNotLoggedinException userNotLoggedinException) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "User not logged in"));
    } catch (ExpiredJwtException expiredJwtException) {
      return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
          .body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "JWT Token Expired"));
    }
  }
}
