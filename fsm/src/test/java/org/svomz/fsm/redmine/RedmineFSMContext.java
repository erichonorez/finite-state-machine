package org.svomz.fsm.redmine;

import org.svomz.fsm.AbstractFSMContext;
import org.svomz.fsm.FSMContext;
import org.svomz.fsm.Status;

public class RedmineFSMContext extends AbstractFSMContext<Status> {

  private final String redmineId;

  public RedmineFSMContext(Status status, String redmineId) {
    super(status);
    this.redmineId = redmineId;
  }

  @Override
  public FSMContext<Status> copy(Status newState) {
    return new RedmineFSMContext(newState, this.redmineId);
  }

  public String redmineId() {
    return this.redmineId;
  }
}
