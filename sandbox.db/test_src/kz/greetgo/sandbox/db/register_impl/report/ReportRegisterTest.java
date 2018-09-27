package kz.greetgo.sandbox.db.register_impl.report;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.report.ReportView;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ReportRegisterTest extends ParentTestNg {

	public BeanGetter<ReportRegister> myBigReportRegisterBeanGetter;

	private static class TestView implements ReportView {

		public MyReportHeadData headData = null;

		public MyReportFootData footData = null;

		private List<ClientRecord> row = new ArrayList<>();


		@Override
		public void start(MyReportHeadData headData) {

			this.headData = headData;
		}

		@Override
		public void addRow(ClientRecord row) {

			this.row.add(row);
		}

		@Override
		public void finish(MyReportFootData footData) {

			this.footData = footData;
		}

		@Override
		public void close() throws Exception {

		}
	}

	public BeanGetter<ClientTestDao> clientTestDao1;


	@BeforeMethod
	public void setUp() {

		clientTestDao1.get().deleteAllClientAccountTransaction();
		clientTestDao1.get().deleteAllClientAccount();
		clientTestDao1.get().deleteAllClientAddr();
		clientTestDao1.get().deleteAllClientPhone();
		clientTestDao1.get().deleteAllClient();
		clientTestDao1.get().deleteAllTransactionType();
		clientTestDao1.get().deleteAllCharm();
	}

	@Test
	public void testGenReport() throws Exception {

		Charm charm = new Charm();
		charm.id = 1;
		charm.name = "Dobrota";
		Client client = new Client();
		client.id = 1;
		client.firstname = "Nazar";
		client.lastname = "Abu";
		client.gender = GenderType.MALE;
		client.charm = 1;
		clientTestDao1.get().insertCharm(charm);
		clientTestDao1.get().insertClient(client);
		charm.id = 2;
		charm.name = "Nenavиst";
		client.id = 2;
		client.firstname = "Lermontov";
		client.lastname = "Mikhail";
		client.gender = GenderType.MALE;
		client.charm = 2;
		clientTestDao1.get().insertCharm(charm);
		clientTestDao1.get().insertClient(client);
		ClientFilter clientFilter = new ClientFilter();
		clientFilter.orderBy = "id";
		clientFilter.recordTotal=0;
		TestView testView = new TestView();

		//
		//
		myBigReportRegisterBeanGetter.get().genReport(testView, clientFilter);
		//
		//
		assertThat(testView.headData).isNotNull();
		assertThat(testView.footData).isNotNull();
		assertThat(testView.headData.title).isEqualTo("Report");
		assertThat(testView.footData.generatedBy).isEqualTo("Nazar");
		System.err.println(testView.row);
		assertThat(testView.row.size()).isEqualTo(2);
		assertThat(testView.row.get(0).id).isEqualTo(1);
		assertThat(testView.row.get(0).firstname).isEqualTo("Nazar");
		assertThat(testView.row.get(0).lastname).isEqualTo("Abu");
		assertThat(testView.row.get(0).characterName).isEqualTo("Dobrota");
		assertThat(testView.row.get(0).totalAccountBalance).isEqualTo(0);
		assertThat(testView.row.get(0).maximumBalance).isEqualTo(0);
		assertThat(testView.row.get(0).minimumBalance).isEqualTo(0);
		assertThat(testView.row.get(1).id).isEqualTo(2);
		assertThat(testView.row.get(1).firstname).isEqualTo("Lermontov");
		assertThat(testView.row.get(1).lastname).isEqualTo("Mikhail");
		assertThat(testView.row.get(1).characterName).isEqualTo("Nenavиst");
		assertThat(testView.row.get(1).totalAccountBalance).isEqualTo(0);
		assertThat(testView.row.get(1).maximumBalance).isEqualTo(0);
		assertThat(testView.row.get(1).minimumBalance).isEqualTo(0);
	}
}