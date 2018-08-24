///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register._develop_;

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.util.TestsBeanContainer;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.util.TestsBeanContainerCreator;

//

/**
 * <p>
 * see --> Инициация приложения на рабочем месте разработчика
 * </p>
 * <p>
 * Этот скрипт запускается для иницииации приложения: здесь автоматически настраиваются конфиги и инициируется БД
 * </p>
 */
public class RecreateDb {
  public static void main(String[] args) throws Exception {
    TestsBeanContainer bc = TestsBeanContainerCreator.create();

    bc.dbWorker().recreateAll();
  }
}
