package com.codeprism.stastics.statisticsMicroService.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "latest_statistics")
@AllArgsConstructor @NoArgsConstructor
public class Statistics {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  @Getter @Setter
  private Integer id;

  @Column(name = "DESCRIPTION")
  @Getter @Setter
  @NotEmpty @NotBlank @NotNull
  private String description;

  @Column(name = "DATE")
  @Getter @Setter
  @NotNull
  private Date date;

  @Column(name = "EMAIL")
  @Getter @Setter
  @NotEmpty @NotBlank @NotNull
  private String email;

  @PrePersist
  private void getTimeOperation(){
    this.date = new Date();
  }
}
