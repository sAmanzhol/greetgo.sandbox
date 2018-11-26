package kz.greetgo.sandbox.backend.service.impl;

import kz.greetgo.sandbox.backend.service.MoneyService;
import kz.greetgo.sandbox.backend.service.MoneyTransactionalService;
import kz.greetgo.sandbox.backend.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class MoneyTransactionalServiceImplTest extends ParentTestNg {
  @Autowired
  private MoneyService moneyService;

  @Autowired
  private MoneyTransactionalService moneyTransactionalService;

  @Test
  public void transactionTest() throws Exception {

    String room = RND.str(10);

    System.out.println("room = [[" + room + "]]");

    final List<String> idList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      long amount = RND.plusLong(1_000_000_000L);
      {
        String id = RND.str(10);
        idList.add(id);
        moneyService.create(id, room, +amount);
      }
      assertThat(moneyService.amountSum(room)).isEqualTo(amount);
      {
        String id = RND.str(10);
        idList.add(id);
        moneyService.create(id, room, -amount);
      }
      assertThat(moneyService.amountSum(room)).isZero();
    }

    class Worker extends Thread {
      final String name;

      public Worker(String name) {
        this.name = name;
      }

      @Override
      public void run() {

        for (int j = 0; j < 100; j++) {

          String idFrom = idList.get(RND.plusInt(idList.size()));
          String idTo = idList.get(RND.plusInt(idList.size()));
          long amount = RND.plusLong(1_000_000_000L);

          moneyTransactionalService.move(idFrom, idTo, amount);
        }

        System.out.println("Finished worker " + name);

      }
    }

    Worker workers[] = new Worker[10];
    for (int i = 0; i < workers.length; i++) {
      workers[i] = new Worker("Worker " + i);
    }

    System.out.println("Start workers");

    for (Worker worker : workers) {
      worker.start();
    }

    System.out.println("Join to all workers");

    for (Worker worker : workers) {
      worker.join();
    }

    System.out.println("Finished all workers");

    assertThat(moneyService.amountSum(room)).isZero();
  }
}