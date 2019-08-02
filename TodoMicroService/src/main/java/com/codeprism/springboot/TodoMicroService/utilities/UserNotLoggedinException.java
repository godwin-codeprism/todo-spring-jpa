package com.codeprism.springboot.TodoMicroService.utilities;

public class UserNotLoggedinException extends Exception{
  public UserNotLoggedinException(String message){
    super(message);
  }
}
