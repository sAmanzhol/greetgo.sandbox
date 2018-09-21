package kz.greetgo.sandbox.controller.model.model;

import java.sql.Timestamp;

// TODO: asset 9/21/18 Nadao ubrat class kotorye ispolzuetsya v testovom classe cotorye voobshe ne nuzhny v controllere
public class ClientAccount {
	public int id;
	public int client;
	public float money;
	public String number;
	public Timestamp registeredAt;
}
