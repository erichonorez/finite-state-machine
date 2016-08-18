package org.svomz.fsm.kanban;

import org.svomz.fsm.AbstractFSM;
import org.svomz.fsm.FSMBuilder;
import org.svomz.fsm.FSMGraphvizGenerator;

public class Process extends AbstractFSM<ProcessStage, ProcessContext> {
    @Override
    protected FSMBuilder<ProcessStage, ProcessContext> define() {
        return
            startWith(ProcessStage.TODO).transit(

                on(ProcessEvent.WORK_ITEM_ASSIGNED)
                    .to(ProcessStage.IN_PROGRESS).transit(

                        on(ProcessEvent.CODE_COMMITED)
                            .to(ProcessStage.WAITING_ACCEPTANCE).transit(

                                on(ProcessEvent.ACCEPTED)
                                    .to(ProcessStage.FOR_PRODUCTION).transit(

                                        on(ProcessEvent.DEPLOYED)
                                            .to(ProcessStage.IN_PRODUCTION)
                        ),

                        on(ProcessEvent.REFUSED)
                            .to(ProcessStage.IN_PROGRESS)
                    )
                )
            );
    }

    public static void main(String args[]) {
        System.out.println(FSMGraphvizGenerator.generate(new Process()));
    }
}
