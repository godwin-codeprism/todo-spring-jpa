package com.codeprism.springboot.TodoMicroService.utilities;

public class UserNotInDBExecption  extends Exception{
  public UserNotInDBExecption(String message){
    super(message);
  }
}
