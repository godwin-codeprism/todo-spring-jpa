package com.codeprism.stastics.statisticsMicroService.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
public class JsonResponseBody {

  @Getter @Setter
  private int server;

  @Getter @Setter
  public Object response;
}
