package org.svomz.fsmweb.domain;

import org.svomz.fsm.State;

public enum LoanRequestState implements State {
  /**
   * The initial state.
   */
  WAITING_PERSONAL_INFORMATION(false),
  /**
   *
   */
  WAITING_EMPLOYER_INFORMATION(false),
  /**
   *
   */
  WAITING_HOUSEHOLD_INFORMATION(false),

  WAITING_CONTRACT_PRINTING(false),

  WAITING_CONTRACT_VALIDATION(false),

  WAITING_CONTRACT_OPTIONS_CHOICE(false),

  WAITING_CLIENT_SIGNATURE(false),

  WAITING_VENDOR_SIGNATURE(false),

  WAITING_EVIDENCES(false),

  WAITING_TEMPORARY_CARD_ATTRIBUTION(false),

  WAITING_TEMPORARY_CARD_RECEIPT_SIGNATURE(false),

  /**
   * The request is accepted by our scoring system.
   */
  WAITING_SIGNATURE_TYPE_CHOICE(false),
  /**
   * The request is refused by our scoring system.
   */
  REFUSED(true),
  /**
   * The scoring system can't take an automatic decision and the request must be analysed by a human.
   */
  WAITING_MANUAL_ACCEPTATION(false),
  /**
   * The contract is signed by the client and the vendor.
   */
  VALIDATED(true),
  /**
   * The request is abandoned.
   */
  ABANDONED(true);

  private final boolean isFinal;

  LoanRequestState(boolean isFinal) {
    this.isFinal = isFinal;
  }

  public boolean isFinal() {
    return this.isFinal;
  }
}
