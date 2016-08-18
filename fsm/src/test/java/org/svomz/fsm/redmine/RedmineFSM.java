package org.svomz.fsm.redmine;


import org.svomz.fsm.AbstractFSM;
import org.svomz.fsm.FSMBuilder;
import org.svomz.fsm.FSMGraphvizGenerator;
import org.svomz.fsm.Status;

public class RedmineFSM extends AbstractFSM<Status, RedmineFSMContext> {

  @Override
  protected FSMBuilder<Status, RedmineFSMContext> define() {
    //noinspection unchecked
    return
      startWith(Status.TODO).transit(

        on(RedmineEvent.STARTED)
          .to(Status.IN_PROGRESS).transit(

            on(RedmineEvent.FIXED)
              .to(Status.FIXED)
              .execute((RedmineFSMContext context) -> {
                System.out.println(context.currentState() + " " + context.redmineId());
              })
        )
      );
  }

  public static void main(String[] args) {
    System.out.println(FSMGraphvizGenerator.generate(
      new RedmineFSM(),
      new RedmineFSMContext(Status.TODO, "123")
    ));

    System.out.println(FSMGraphvizGenerator.generate(
      new RedmineFSM()
    ));
  }

}
