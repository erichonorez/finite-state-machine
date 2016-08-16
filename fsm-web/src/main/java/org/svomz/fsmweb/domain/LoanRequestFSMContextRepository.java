package org.svomz.fsmweb.domain;

import java.util.Optional;

public interface LoanRequestFSMContextRepository {

  Optional<LoanRequestFSMContext> findByLoanRequestId(String aLoanRequestId);

  void save(LoanRequestFSMContext newContext);
}
