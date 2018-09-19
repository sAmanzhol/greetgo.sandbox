package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.model.model.Client;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientJdbc implements ConnectionCallback<Integer> {

	public ClientFilter clientFilter;

	private Boolean totalOrList;

	public List<Client> clientList = new ArrayList<>();


	public ClientJdbc(ClientFilter clientFilter, Boolean totalOrList) {

		this.clientFilter = clientFilter;
		this.totalOrList = totalOrList;
	}


	@Override
	public Integer doInConnection(Connection connection) throws Exception {


		try (PreparedStatement ps = connection.prepareStatement(generateSql(totalOrList).toString())) {

			try (ResultSet rs = ps.executeQuery()) {
				Integer count=0;
				while (rs.next()) {

					count= rs.getInt("count");
					/*Client client = new Client();
					client.id = rs.getInt("id");
					client.firstname = rs.getString("firstname");
					client.lastname = rs.getString("lastname");
					client.patronymic = rs.getString("patronymic");
					client.gender = GenderType.valueOf(rs.getString("gender"));
					client.birthDate = rs.getDate("birth_date");
					client.charm = rs.getInt("charm");
					System.err.println("CLIENT ID: " + client.id);

					clientList.add(client);*/

				}
				rs.close();
				ps.close();
				connection.close();
				return count;

			}
		}

	}



	private StringBuilder generateSql(Boolean totalOrList) {

		StringBuilder sb = new StringBuilder();

		if(!totalOrList) {
			sb.append("select count(id)");
			sb.append(" from client");
			sb.append(" where firstname like '" + clientFilter.firstname + "%'");
			if (!clientFilter.lastname.equals(""))
				sb.append(" and lastname like '" + clientFilter.patronymic + "%'");
			if (!clientFilter.patronymic.equals(""))
				sb.append(" and patronymic like '" + clientFilter.lastname + "%'");
		}
		sb.append("select *");
		sb.append(" from client");
		sb.append(" where firstname like '" + clientFilter.firstname + "%'");
		if (!clientFilter.lastname.equals(""))
			sb.append(" and lastname like '" + clientFilter.patronymic + "%'");
		if (!clientFilter.patronymic.equals(""))
			sb.append(" and patronymic like '" + clientFilter.lastname + "%'");


		return sb;
	}


	private void appendSelect(StringBuilder sb) {
		sb.append("select	*");
	}

}
