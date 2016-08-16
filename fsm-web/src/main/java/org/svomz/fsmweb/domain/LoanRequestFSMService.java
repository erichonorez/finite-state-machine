package org.svomz.fsmweb.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.svomz.fsm.FSMGraphvizGenerator;

import java.util.Optional;

import javax.transaction.Transactional;

public class LoanRequestFSMService {

  private LoanRequestFSMContextRepository loanRequestFSMContextRepository;
  private LoanRequestFSM fsm;

  @Autowired
  public LoanRequestFSMService(LoanRequestFSMContextRepository loanRequestFSMContextRepository) {
    this.loanRequestFSMContextRepository = loanRequestFSMContextRepository;
    this.fsm = new LoanRequestFSM();
  }

  @Transactional
  public void trigger(String aLoanRequestId, LoanRequestEvent anEvent)
    throws LoanRequestFSMContextNotFoundException {

    LoanRequestFSMContext loanRequestFSMContext = this.existingLoanRequestFSMContextWithId(aLoanRequestId);

    LoanRequestFSMContext newContext = this.fsm.trigger(anEvent, loanRequestFSMContext);

    this.loanRequestFSMContextRepository().save(newContext);
  }

  @Transactional
  public void createNewFSMForLoanRequestWithId(String loanRequestId) {
    LoanRequestFSMContext context = new LoanRequestFSMContext(LoanRequestState.WAITING_PERSONAL_INFORMATION, loanRequestId);
    this.loanRequestFSMContextRepository.save(context);
  }

  public String graph(String aRequestId) throws LoanRequestFSMContextNotFoundException {
    LoanRequestFSMContext loanRequestFSMContext = this.existingLoanRequestFSMContextWithId(aRequestId);
    return FSMGraphvizGenerator.generate(this.fsm, loanRequestFSMContext);
  }

  private LoanRequestFSMContext existingLoanRequestFSMContextWithId(String aLoanRequestId)
    throws LoanRequestFSMContextNotFoundException {

    Optional<LoanRequestFSMContext> optionalLoanRequestFSMContext = this.loanRequestFSMContextRepository()
      .findByLoanRequestId(aLoanRequestId);

    if (!optionalLoanRequestFSMContext.isPresent()) {
      throw new LoanRequestFSMContextNotFoundException();
    }

    return optionalLoanRequestFSMContext.get();
  }

  private LoanRequestFSMContextRepository loanRequestFSMContextRepository() {
    return this.loanRequestFSMContextRepository;
  }
}
