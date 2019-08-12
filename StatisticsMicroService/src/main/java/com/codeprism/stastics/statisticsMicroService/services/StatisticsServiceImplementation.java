package com.codeprism.stastics.statisticsMicroService.services;

import com.codeprism.stastics.statisticsMicroService.doas.StatisticDao;
import com.codeprism.stastics.statisticsMicroService.models.Statistics;
import com.codeprism.stastics.statisticsMicroService.utils.JsonResponseBody;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service @Log
public class StatisticsServiceImplementation implements StatisticsService{
  @Autowired
  StatisticDao statisticsDao;

  @Override
  public List<Statistics> getStatistics (String jwt, String email){
    List<LinkedHashMap> toDos = getNewDateFromToDoMicroService(jwt);

    String statisticsDescription = "No statistics available";

    if(toDos != null && toDos.size() > 0){
      int lowPriorityTodos = 0;
      int highPriorityTodos = 0;
      for(int i =0; i < toDos.size(); i++){
        LinkedHashMap todo = toDos.get(i);
        String priority = (String) todo.get("priority");
        if(priority == "low"){
          lowPriorityTodos++;
        }else if(priority == "high"){
          highPriorityTodos++;
        }
      }
      statisticsDescription = "You have <b>" + lowPriorityTodos + " low priority</b> to dos and <b>" + highPriorityTodos + " high priority</b> to dos.";
    }
    List<Statistics> statistics = statisticsDao.getLastStatistics(email);
    log.info("GODWINVC" + statistics.size());
    if(statistics.size() > 0){
      Date now = new Date();
      long diff = now.getTime() - statistics.get(0).getDate().getTime();
      long days = diff / (24 * 60 * 60 * 1000);
      if(days > 1){
        statistics.add(statisticsDao.save(new Statistics(null, statisticsDescription, new Date(), email)));
      }
    }
    return statistics;
  }

  private List<LinkedHashMap> getNewDateFromToDoMicroService(String jwt){
    MultiValueMap<String, String> headers= new LinkedMultiValueMap<String, String>();
    headers.add("jwt", jwt);
    HttpEntity<?> req = new HttpEntity<>(String.class, headers);

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<JsonResponseBody> responseEntity = restTemplate.exchange("http://localhost:8080/showToDos", HttpMethod.GET, req, JsonResponseBody.class);
    log.info("Godwin" + responseEntity.getBody().getResponse().toString());
    List<LinkedHashMap> todoList = (List) responseEntity.getBody().getResponse();
    return todoList;
  }
}
