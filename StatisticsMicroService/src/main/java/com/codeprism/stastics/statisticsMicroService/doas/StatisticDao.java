package com.codeprism.stastics.statisticsMicroService.doas;

import com.codeprism.stastics.statisticsMicroService.models.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticDao extends JpaRepository<Statistics, Integer> {
  @Query(value = "SELECT * FROM latest_statistics WHERE EMAIL = :email ORDER BY DATE DESC LIMIT 10;", nativeQuery = true)
  List<Statistics> getLastStatistics(@Param("email") String email);
}
