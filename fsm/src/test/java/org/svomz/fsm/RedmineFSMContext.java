package org.svomz.fsm;

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
