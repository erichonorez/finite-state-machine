package org.svomz.fsmweb.domain;

import org.svomz.fsm.AbstractFSMContext;
import org.svomz.fsm.FSMContext;

public class LoanRequestFSMContext extends AbstractFSMContext<LoanRequestState> {

  private final String loanRequestId;

  public LoanRequestFSMContext(LoanRequestState state, String aLoanRequestId) {
    super(state);
    this.loanRequestId = aLoanRequestId;
  }

  @Override
  public FSMContext<LoanRequestState> copy(LoanRequestState newState) {
    return new LoanRequestFSMContext(newState, this.loanRequestId());
  }

  public String loanRequestId() {
    return this.loanRequestId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LoanRequestFSMContext that = (LoanRequestFSMContext) o;

    return loanRequestId.equals(that.loanRequestId);

  }

  @Override
  public int hashCode() {
    return loanRequestId.hashCode();
  }
}
