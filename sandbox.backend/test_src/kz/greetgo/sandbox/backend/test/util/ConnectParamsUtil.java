package kz.greetgo.sandbox.backend.test.util;

import kz.greetgo.sandbox.backend.config.DbConfig;

import java.util.Map;

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

      case MASTER:
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
            return dbConfig.username() + "_diff";
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

  public static String replaceParameterOrReturnSame(String line, Map<String, String> params) {
    int i = line.indexOf("=");
    if (i < 0) {
      return line;
    }

    String key = line.substring(0, i).trim();

    String newValue = params.get(key);
    if (newValue != null) {
      return key + "=" + newValue;
    }

    return line;
  }
}
