package kz.greetgo.sandbox.controller.model.model;

import java.sql.Timestamp;

public class ClientAccountTransaction {
	public int id;
	public int account;
	public float money;
	public Timestamp finishedAt;
	public int type;
}