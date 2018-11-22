///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.backend.configuration.util;

import kz.greetgo.conf.hot.FileConfigFactory;

public abstract class LocalConfigFactory extends FileConfigFactory {
  @Override
  protected String getBaseDir() {
    return App.folderD().resolve("conf").toString();
  }
}
