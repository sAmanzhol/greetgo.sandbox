package kz.greetgo.sandbox.controller.register;

public interface MigrationRegister {

  void migrate(String fileType, String file) throws Exception;
}
