package com.codeprism.stastics.statisticsMicroService.controller;

import com.codeprism.stastics.statisticsMicroService.services.StatisticsService;
import com.codeprism.stastics.statisticsMicroService.utils.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndpointsController {
  @Autowired
  StatisticsService statisticsService;

  @RequestMapping("/getStats")
  public ResponseEntity<JsonResponseBody> getStats (@RequestParam(value = "jwt") String jwt, @RequestParam(value ="email") String email){
    return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), statisticsService.getStatistics(jwt, email)));
  }

  @RequestMapping("test")
  public String test(){
    return "Statistics endpoint works!";
  }
}
