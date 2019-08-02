package com.codeprism.springboot.TodoMicroService.services;

import com.codeprism.springboot.TodoMicroService.daos.UserDao;
import com.codeprism.springboot.TodoMicroService.entities.User;
import com.codeprism.springboot.TodoMicroService.utilities.EncryptionUtils;
import com.codeprism.springboot.TodoMicroService.utilities.JwtUtils;
import com.codeprism.springboot.TodoMicroService.utilities.UserNotInDBExecption;
import com.codeprism.springboot.TodoMicroService.utilities.UserNotLoggedinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImplementation implements LoginService {

  @Autowired
  UserDao userDao;

  @Autowired
  EncryptionUtils encryptionUtils;

  @Autowired
  JwtUtils jwtUtils;


  @Override
  public Optional<User> getUserFormDB(String email, String password) throws UserNotInDBExecption {
    Optional<User> user = userDao.findUserByEmail(email);
    if (user.isPresent()) {
      User user1 = user.get();
      if (!encryptionUtils.decrypt(user1.getPassword()).equals(password)) {
        throw new UserNotInDBExecption("Incorrect Password");
      }
    } else {
      throw new UserNotInDBExecption("Incorrect Username");
    }
    return user;
  }

  @Override
  public String createJwt(String email, String name, Date date) throws UnsupportedEncodingException{
    date.setTime(date.getTime() + (300 * 1000));
    return  jwtUtils.generateJwt(email, name, date);
  }

  @Override
  public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UnsupportedEncodingException, UserNotLoggedinException {
    String jwt = jwtUtils.getJwtFromHttpRequest(request);
    if(jwt == null){
      throw new UserNotLoggedinException("User is not Logged in");
    }
    return jwtUtils.jwt2Map(jwt);
  }
}
