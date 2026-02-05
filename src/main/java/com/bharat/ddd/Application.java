package com.bharat.ddd;

import com.bharat.ddd.config.ExcludeFromJacocoGeneratedReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ExcludeFromJacocoGeneratedReport
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
