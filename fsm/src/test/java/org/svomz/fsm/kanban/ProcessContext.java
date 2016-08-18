package org.svomz.fsm.kanban;

import org.svomz.fsm.AbstractFSMContext;
import org.svomz.fsm.FSMContext;

public class ProcessContext extends AbstractFSMContext<ProcessStage> {

    public ProcessContext(ProcessStage currentState) {
        super(currentState);
    }

    @Override
    public FSMContext<ProcessStage> copy(ProcessStage newState) {
        return new ProcessContext(newState);
    }
}
