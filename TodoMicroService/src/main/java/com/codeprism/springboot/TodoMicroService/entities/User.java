package com.codeprism.springboot.TodoMicroService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@AllArgsConstructor @NoArgsConstructor
public class User {

  @Id
  @Column(name = "EMAIL")
  @Getter @Setter
  @NotNull @NotBlank @NotEmpty
  private String email;

  @Column(name = "NAME")
  @Getter @Setter
  @NotNull @NotBlank @NotEmpty
  private String name;

  @Column(name = "PASSWORD")
  @Getter @Setter
  @NotNull @NotBlank @NotEmpty
  private String password;

}
