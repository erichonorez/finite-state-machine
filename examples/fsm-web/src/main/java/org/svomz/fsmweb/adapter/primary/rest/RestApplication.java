package org.svomz.fsmweb.adapter.primary.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.svomz.fsmweb.application.LoanRequestService;
import org.svomz.fsmweb.domain.LoanRequestFSMContextRepository;
import org.svomz.fsmweb.domain.LoanRequestFSMService;
import org.svomz.fsmweb.adapter.secondary.persistence.InMemoryLoanRequestFSMContextRepository;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class RestApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestApplication.class, args);
  }


  @Bean
  public LoanRequestFSMContextRepository loanRequestFSMContextRepository() {
    return new InMemoryLoanRequestFSMContextRepository();
  }

  @Bean
  public LoanRequestService loanRequestService() {
    return new LoanRequestService(this.loanRequestFSMService());
  }

  @Bean
  public LoanRequestFSMService loanRequestFSMService() {
    return new LoanRequestFSMService(this.loanRequestFSMContextRepository());
  }
}
