package com.codeprism.springboot.TodoMicroService.entities;

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
@Table(name = "todos")
@NoArgsConstructor @AllArgsConstructor
public class ToDo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  @Getter @Setter
  private Integer id;

  @Column(name = "DESCRIPTION")
  @Getter @Setter
  @NotNull @NotBlank @NotEmpty
  private String description;

  @Column(name = "DATE")
  @Getter @Setter
  private Date date;

  @Column(name = "PRIORITY")
  @Getter @Setter
  @NotNull @NotBlank @NotEmpty
  private String priority;

  @Column(name = "FKUSER")
  @Getter @Setter
  @NotNull @NotBlank @NotEmpty
  private String fkUser;

  @PrePersist
  void getTimeOperation(){
    this.date = new Date();
  }

}
