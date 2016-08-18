package org.svomz.fsmweb.adapters.primary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.svomz.fsmweb.application.LoanRequestService;
import org.svomz.fsmweb.domain.LoanRequestFSMContextNotFoundException;
import org.svomz.fsmweb.domain.LoanRequestFSMService;

@RestController
@RequestMapping("/requests")
public class LoanRequestEndPoint {

  private LoanRequestService loanRequestService;
  private LoanRequestFSMService fsmService;

  @Autowired
  public LoanRequestEndPoint(LoanRequestService loanRequestService, LoanRequestFSMService fsmService) {
    this.loanRequestService = loanRequestService;
    this.fsmService = fsmService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public String createNewRequest() {
    return this.loanRequestService.createLoanRequest();
  }

  @RequestMapping(path = "/{requestId}/personal-information", method = RequestMethod.PUT)
  public void personalInformation(@PathVariable String requestId)
    throws LoanRequestFSMContextNotFoundException {

    this.loanRequestService.personalInformation(requestId);
  }

  @RequestMapping(path = "/{requestId}/fsm", method = RequestMethod.GET)
  public String fsm(@PathVariable String requestId) throws LoanRequestFSMContextNotFoundException {
    return this.fsmService.graph(requestId);
  }

}
