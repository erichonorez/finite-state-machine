package org.svomz.fsmweb.org.svomz.fsmweb.secondary.persistence;

import org.svomz.fsmweb.domain.LoanRequestFSMContext;
import org.svomz.fsmweb.domain.LoanRequestFSMContextRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class InMemoryLoanRequestFSMContextRepository implements LoanRequestFSMContextRepository {

  Set<LoanRequestFSMContext> contexts = new HashSet<>();

  @Override
  public Optional<LoanRequestFSMContext> findByLoanRequestId(final String aLoanRequestId) {
    return this.contexts.stream()
      .filter(context -> context.loanRequestId().equals(aLoanRequestId))
      .findFirst();
  }

  @Override
  public void save(LoanRequestFSMContext newContext) {
    if (this.contexts.contains(newContext)) {
      this.contexts.remove(newContext);
    }
    this.contexts.add(newContext);
  }
}
