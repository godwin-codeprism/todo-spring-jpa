package com.codeprism.stastics.statisticsMicroService.services;

import com.codeprism.stastics.statisticsMicroService.models.Statistics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisticsService {
  List<Statistics> getStatistics (String jwt, String email);
}
