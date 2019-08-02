package com.codeprism.springboot.TodoMicroService.services;

import com.codeprism.springboot.TodoMicroService.entities.User;
import com.codeprism.springboot.TodoMicroService.utilities.UserNotInDBExecption;
import com.codeprism.springboot.TodoMicroService.utilities.UserNotLoggedinException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public interface LoginService {
  Optional<User> getUserFormDB(String email, String password) throws UserNotInDBExecption;

  String createJwt(String email, String name, Date date) throws UnsupportedEncodingException;

  Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UnsupportedEncodingException, UserNotLoggedinException;
}
