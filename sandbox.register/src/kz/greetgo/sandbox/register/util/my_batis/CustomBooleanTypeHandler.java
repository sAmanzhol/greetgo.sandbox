/**
 * Copyright 2009-2015 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kz.greetgo.sandbox.register.util.my_batis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomBooleanTypeHandler extends BaseTypeHandler<Boolean> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType)
    throws SQLException {
    if (parameter == null)
      ps.setNull(i, jdbcType.TYPE_CODE);
    else
      ps.setInt(i, parameter ? 1 : 0);
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, String columnName)
    throws SQLException {
    String strVal = rs.getString(columnName);
    return strVal == null ? null : !"0".equals(strVal);
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, int columnIndex)
    throws SQLException {
    String strVal = rs.getString(columnIndex);
    return strVal == null ? null : !"0".equals(strVal);
  }

  @Override
  public Boolean getNullableResult(CallableStatement cs, int columnIndex)
    throws SQLException {
    String strVal = cs.getString(columnIndex);
    return strVal == null ? null : !"0".equals(strVal);
  }
}
