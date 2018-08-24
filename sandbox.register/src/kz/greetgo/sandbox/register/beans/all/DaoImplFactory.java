///MODIFY replace sandbox PROJECT_NAME
package kz.greetgo.sandbox.register.beans.all;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
///MODIFY replace sandbox PROJECT_NAME
import kz.greetgo.sandbox.register.util.AbstractMybatisDaoImplFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

@Bean
public class DaoImplFactory extends AbstractMybatisDaoImplFactory {

  public BeanGetter<DbSessionFactory> dbSessionFactory;

  @Override
  protected SqlSession getSqlSession() {
    return dbSessionFactory.get().sqlSession();
  }

  @Override
  protected Configuration getConfiguration() {
    return dbSessionFactory.get().getConfiguration();
  }
}
