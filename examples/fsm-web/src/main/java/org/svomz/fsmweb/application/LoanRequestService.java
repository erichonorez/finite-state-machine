package org.svomz.fsmweb.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svomz.fsmweb.domain.LoanRequestEvent;
import org.svomz.fsmweb.domain.LoanRequestFSMContextNotFoundException;
import org.svomz.fsmweb.domain.LoanRequestFSMService;

import java.util.UUID;

import javax.transaction.Transactional;

public class LoanRequestService {

  public LoanRequestFSMService fsmService;

  @Autowired
  public LoanRequestService(LoanRequestFSMService fsmService) {
    this.fsmService = fsmService;
  }

  @Transactional
  public String createLoanRequest() {
    String loanRequestId = UUID.randomUUID().toString();

    this.fsmService.createNewFSMForLoanRequestWithId(loanRequestId);

    return loanRequestId;
  }

  @Transactional
  public void personalInformation(String aLoanRequestId)
    throws LoanRequestFSMContextNotFoundException {
    // Persist data and call the cracking service and then call the final state maching with
    // the output of the cracking service.
    this.fsmService.trigger(aLoanRequestId, LoanRequestEvent.ACCEPTED_BY_CRAKING_SYSTEM);
  }
}
