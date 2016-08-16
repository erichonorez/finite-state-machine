package org.svomz.fsmweb.domain;


import org.svomz.fsm.AbstractFSM;
import org.svomz.fsm.FSMBuilder;
import org.svomz.fsm.FSMGraphvizGenerator;

import static org.svomz.fsmweb.domain.LoanRequestEvent.ABANDONNED_BY_CLIENT;
import static org.svomz.fsmweb.domain.LoanRequestEvent.ACCEPTED_BY_CRAKING_SYSTEM;
import static org.svomz.fsmweb.domain.LoanRequestEvent.ACCEPTED_BY_HUMAN;
import static org.svomz.fsmweb.domain.LoanRequestEvent.ACCEPTED_BY_SCORING_SYSTEM;
import static org.svomz.fsmweb.domain.LoanRequestEvent.CLIENT_SIGNED;
import static org.svomz.fsmweb.domain.LoanRequestEvent.CONTRACT_OPTIONS_CHOSEN;
import static org.svomz.fsmweb.domain.LoanRequestEvent.CONTRACT_PRINTED;
import static org.svomz.fsmweb.domain.LoanRequestEvent.CONTRACT_VALIDATED;
import static org.svomz.fsmweb.domain.LoanRequestEvent.DIGITAL_SIGNED_ABANDONNED;
import static org.svomz.fsmweb.domain.LoanRequestEvent.DIGITAL_SIGNING_CHOSEN;
import static org.svomz.fsmweb.domain.LoanRequestEvent.EMPLOYER_INFORMATION_COLLECTED;
import static org.svomz.fsmweb.domain.LoanRequestEvent.EVIDENCES_UPLOADED;
import static org.svomz.fsmweb.domain.LoanRequestEvent.MANUAL_SIGNING_CHOSEN;
import static org.svomz.fsmweb.domain.LoanRequestEvent.REFUSED_BY_CRAKING_SYSTEM;
import static org.svomz.fsmweb.domain.LoanRequestEvent.REFUSED_BY_SCORING_SYSTEM;
import static org.svomz.fsmweb.domain.LoanRequestEvent.REFUSED_BY_HUMAN;
import static org.svomz.fsmweb.domain.LoanRequestEvent.TEMPORARY_CARD_NUMBER_ASSIGNED;
import static org.svomz.fsmweb.domain.LoanRequestEvent.TEMPORARY_CARD_RECEIPT_SIGNED;
import static org.svomz.fsmweb.domain.LoanRequestEvent.UNKNOWN_SCORING;
import static org.svomz.fsmweb.domain.LoanRequestEvent.VENDOR_SIGNED;
import static org.svomz.fsmweb.domain.LoanRequestState.ABANDONED;
import static org.svomz.fsmweb.domain.LoanRequestState.REFUSED;
import static org.svomz.fsmweb.domain.LoanRequestState.VALIDATED;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_CLIENT_SIGNATURE;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_CONTRACT_OPTIONS_CHOICE;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_CONTRACT_PRINTING;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_CONTRACT_VALIDATION;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_EVIDENCES;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_SIGNATURE_TYPE_CHOICE;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_EMPLOYER_INFORMATION;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_HOUSEHOLD_INFORMATION;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_MANUAL_ACCEPTATION;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_PERSONAL_INFORMATION;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_TEMPORARY_CARD_ATTRIBUTION;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_TEMPORARY_CARD_RECEIPT_SIGNATURE;
import static org.svomz.fsmweb.domain.LoanRequestState.WAITING_VENDOR_SIGNATURE;

public class LoanRequestFSM extends AbstractFSM<LoanRequestState, LoanRequestFSMContext> {


  @Override
  protected FSMBuilder<LoanRequestState, LoanRequestFSMContext> define() {
    return
      startWith(WAITING_PERSONAL_INFORMATION).transit(

        on(ACCEPTED_BY_CRAKING_SYSTEM)
          .to(WAITING_EMPLOYER_INFORMATION).transit(

            on(EMPLOYER_INFORMATION_COLLECTED)
              .to(WAITING_HOUSEHOLD_INFORMATION).transit(

              on(ACCEPTED_BY_SCORING_SYSTEM)
                .to(WAITING_SIGNATURE_TYPE_CHOICE).transit(

                on(MANUAL_SIGNING_CHOSEN)
                  .to(WAITING_CONTRACT_PRINTING).transit(

                  on(CONTRACT_PRINTED)
                    .to(WAITING_CONTRACT_VALIDATION).transit(

                      on(CONTRACT_VALIDATED)
                        .to(VALIDATED)

                  )

                ),

                on(DIGITAL_SIGNING_CHOSEN)
                  .to(WAITING_CONTRACT_OPTIONS_CHOICE).transit(

                  on(CONTRACT_OPTIONS_CHOSEN)
                    .to(WAITING_CLIENT_SIGNATURE).transit(

                    on(CLIENT_SIGNED)
                      .to(WAITING_VENDOR_SIGNATURE).transit(

                        on(VENDOR_SIGNED)
                          .to(WAITING_EVIDENCES).transit(

                          on(EVIDENCES_UPLOADED)
                            .to(WAITING_TEMPORARY_CARD_ATTRIBUTION).transit(

                            on(TEMPORARY_CARD_NUMBER_ASSIGNED)
                              .to(WAITING_TEMPORARY_CARD_RECEIPT_SIGNATURE).transit(

                              on(TEMPORARY_CARD_RECEIPT_SIGNED)
                                .to(VALIDATED),

                              on(ABANDONNED_BY_CLIENT)
                                .to(ABANDONED),

                              on(DIGITAL_SIGNED_ABANDONNED)
                                .to(WAITING_CONTRACT_PRINTING)

                            ),

                            on(ABANDONNED_BY_CLIENT)
                              .to(ABANDONED),

                            on(DIGITAL_SIGNED_ABANDONNED)
                              .to(WAITING_CONTRACT_PRINTING)

                          ),

                          on(ABANDONNED_BY_CLIENT)
                            .to(ABANDONED),

                          on(DIGITAL_SIGNED_ABANDONNED)
                            .to(WAITING_CONTRACT_PRINTING)

                        ),

                      on(ABANDONNED_BY_CLIENT)
                        .to(ABANDONED),

                      on(DIGITAL_SIGNED_ABANDONNED)
                        .to(WAITING_CONTRACT_PRINTING)

                    ),

                    on(ABANDONNED_BY_CLIENT)
                      .to(ABANDONED),

                    on(DIGITAL_SIGNED_ABANDONNED)
                      .to(WAITING_CONTRACT_PRINTING)

                  ),

                  on(ABANDONNED_BY_CLIENT)
                    .to(ABANDONED),

                  on(DIGITAL_SIGNED_ABANDONNED)
                    .to(WAITING_CONTRACT_PRINTING)

                )

              ),

              on(REFUSED_BY_SCORING_SYSTEM)
                .to(REFUSED),

              on(UNKNOWN_SCORING)
                .to(WAITING_MANUAL_ACCEPTATION).transit(

                on(ACCEPTED_BY_HUMAN)
                  .to(WAITING_SIGNATURE_TYPE_CHOICE),

                on(REFUSED_BY_HUMAN)
                  .to(REFUSED)

              )

            )

        ),

        on(REFUSED_BY_CRAKING_SYSTEM)
          .to(REFUSED)

      );
  }

  public static void main(String args[]) {
    System.out.println(FSMGraphvizGenerator.generate(new LoanRequestFSM()));
  }
}
