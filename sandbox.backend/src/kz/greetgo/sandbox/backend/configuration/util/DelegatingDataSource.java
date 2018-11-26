package kz.greetgo.sandbox.backend.configuration.util;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class DelegatingDataSource implements DataSource {

  private final AtomicReference<? extends DataSource> dataSourceAtomicReference;

  public DelegatingDataSource(AtomicReference<? extends DataSource> dataSourceAtomicReference) {
    Objects.requireNonNull(dataSourceAtomicReference, "dataSourceAtomicReference");
    this.dataSourceAtomicReference = dataSourceAtomicReference;
  }

  private DataSource delegation() {
    DataSource dataSource = dataSourceAtomicReference.get();
    Objects.requireNonNull(dataSource, "dataSourceAtomicReference.get() == null");
    return dataSource;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return delegation().getConnection();
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return delegation().getConnection(username, password);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return delegation().unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return delegation().isWrapperFor(iface);
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return delegation().getLogWriter();
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    delegation().setLogWriter(out);
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    delegation().setLoginTimeout(seconds);
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return delegation().getLoginTimeout();
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return delegation().getParentLogger();
  }
}
