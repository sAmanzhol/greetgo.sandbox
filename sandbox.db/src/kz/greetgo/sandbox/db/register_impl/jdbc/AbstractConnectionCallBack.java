package kz.greetgo.sandbox.db.register_impl.jdbc;

import com.itextpdf.text.DocumentException;
import kz.greetgo.db.ConnectionCallback;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConnectionCallBack<T, R> implements ConnectionCallback<R> {

	protected List<Object> param = new ArrayList<>();

	protected SQL sql = new SQL();

	protected StringBuilder sb = new StringBuilder();

	protected final void prepareSql() {

		select();
		from();
		innerJoin();
		leftJoin();
		where();
		groupBy();
		orderBy();
		limit();
		offSet();

	}

				protected abstract R getResult();

				protected abstract void innerJoin();

				protected abstract void offSet();

				protected abstract void select();

				protected abstract void leftJoin();

				protected abstract void where();

				protected abstract void from();

				protected abstract void orderBy();

				protected abstract void groupBy();

				protected abstract void limit();

				protected abstract void addRow(T t) throws DocumentException;


	public R doInConnection(Connection connection) throws Exception {

		prepareSql();

		try (PreparedStatement ps = connection.prepareStatement(sb.toString())) {

			for (int i = 0; i < param.size(); i++) {

				Object pm = param.get(i);
				ps.setObject(i + 1, pm);
			}

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					addRow(doInSelection(rs));
				}

				return getResult();
			}
		}
	}

	protected abstract T doInSelection(ResultSet rs) throws SQLException;

}
