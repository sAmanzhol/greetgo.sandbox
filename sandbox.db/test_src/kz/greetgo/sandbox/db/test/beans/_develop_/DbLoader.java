package kz.greetgo.sandbox.db.test.beans._develop_;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.db.model.ClientAccount;
import kz.greetgo.sandbox.db.register_impl.TokenRegister;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.sandbox.db.stand.model.PersonDot;
import kz.greetgo.sandbox.db.test.dao.AuthTestDao;
import kz.greetgo.sandbox.db.test.dao.LoadTestDao;
import kz.greetgo.util.RND;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.function.Function;

@Bean
public class DbLoader {

	final Logger logger = Logger.getLogger(getClass());

	public BeanGetter<StandDb> standDb;

	public BeanGetter<AuthTestDao> authTestDao;

	public BeanGetter<TokenRegister> tokenManager;

	public BeanGetter<LoadTestDao> loadTestDao;

	public void loadTestData() {

		logger.info("Start loading test data...");

		logger.info("Loading persons...");
		Function<String, String> passwordEncryption = tokenManager.get()::encryptPassword;
		standDb.get().personStorage.values().stream()
			.peek(p -> p.encryptedPassword = passwordEncryption.apply(p.password))
			.peek(PersonDot::showInfo)
			.forEach(authTestDao.get()::insertPersonDot);

		Client client;
		Charm charm;
		ClientPhone clientPhone;
		ClientAccount clientAccount;

		for (int i = 2; i < 35; i++) {
			charm = addCharmKnowValue(i);
			client = addClientKnowValue(i);
			clientPhone = addClientPhoneKnowValue(i);
			System.err.println("LOAD CLIENT TEST DATA TO DB");
			loadTestDao.get().inserCharm(charm);
			loadTestDao.get().insertClient(client);
			loadTestDao.get().insertClientPhone(clientPhone);
			for (int k = 0; k < 4; k++) {
				clientAccount = addClientAccount(i);
				loadTestDao.get().insertClientAccount(clientAccount);

			}
		}


		logger.info("Finish loading test data");
	}

	private Client addClientKnowValue(int i) {

		Client client = new Client();
		client.id = i;
		client.firstname = RND.str(10);
		client.lastname = RND.str(10);
		client.patronymic = RND.str(10);
		client.gender = GenderType.MALE;
		client.birthDate = new Date();
		client.charm = i;
		return client;
	}

	private ClientPhone addClientPhoneKnowValue(int i) {

		ClientPhone clientPhone = new ClientPhone();
		clientPhone.client = i;
		clientPhone.type = PhoneType.MOBILE;
		clientPhone.number = RND.plusInt(11000000) + "";
		return clientPhone;
	}

	private Charm addCharmKnowValue(int i) {

		Charm charm = new Charm();
		charm.id = i;
		charm.name = RND.str(10);
		charm.description = RND.str(20);
		charm.energy = RND.plusInt(1000);
		charm.actually = rndBoolean();
		return charm;
	}

	private ClientAccount addClientAccount(int i) {

		ClientAccount clientAccount = new ClientAccount();
		clientAccount.id = RND.plusInt(100000);
		clientAccount.client = i;
		clientAccount.money = RND.plusInt(2000);
		clientAccount.number = RND.str(10);
		clientAccount.registeredAt = new Timestamp(RND.plusInt(532));
		return clientAccount;
	}

	private boolean rndBoolean() {

		int IntRnd = (int) (Math.random() * 10 - 5);
		return IntRnd >= 0;
	}


}
