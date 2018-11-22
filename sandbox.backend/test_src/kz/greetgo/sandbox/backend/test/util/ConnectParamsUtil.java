package kz.greetgo.sandbox.backend.test.util;

import kz.greetgo.sandbox.backend.config.DbConfig;

import static kz.greetgo.conf.sys_params.SysParams.pgAdminPassword;
import static kz.greetgo.conf.sys_params.SysParams.pgAdminUrl;
import static kz.greetgo.conf.sys_params.SysParams.pgAdminUserid;

public class ConnectParamsUtil {
  public static ConnectParams extractConnectParams(DbConfig dbConfig, ConnectionKind connectionKind) {
    switch (connectionKind) {
      case ADMIN:
        return new ConnectParams() {
          @Override
          public String url() {
            return pgAdminUrl();
          }

          @Override
          public String username() {
            return pgAdminUserid();
          }

          @Override
          public String password() {
            return pgAdminPassword();
          }
        };

      case OPERATIVE:
        return new ConnectParams() {
          @Override
          public String url() {
            return dbConfig.url();
          }

          @Override
          public String username() {
            return dbConfig.username();
          }

          @Override
          public String password() {
            return dbConfig.password();
          }
        };

      case DIFF:
        return new ConnectParams() {
          @Override
          public String url() {
            return dbConfig.url() + "_diff";
          }

          @Override
          public String username() {
            return dbConfig.username();
          }

          @Override
          public String password() {
            return dbConfig.password();
          }
        };

      default:
        throw new IllegalStateException();
    }
  }
}
