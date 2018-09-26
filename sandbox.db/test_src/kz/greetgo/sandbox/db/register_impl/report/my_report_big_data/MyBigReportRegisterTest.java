package kz.greetgo.sandbox.db.register_impl.report.my_report_big_data;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.model.MyReportRow;
import kz.greetgo.sandbox.controller.report.report.ReportView;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class MyBigReportRegisterTest extends ParentTestNg {

	public BeanGetter<ReportRegister> myBigReportRegisterBeanGetter;

	private static class TestView implements ReportView {

		public MyReportHeadData headData = null;

		public MyReportFootData footData=null;

		private List<MyReportRow> row = new ArrayList<>();

		@Override
		public void start(MyReportHeadData headData) {

			this.headData = headData;
		}

		@Override
		public void addRow(MyReportRow row) {
			this.row.add(row);
		}

		@Override
		public void finish(MyReportFootData footData) {

			this.footData = footData;
		}
	}
	@Test
	public void testGenReport() throws Exception {
		//insert into db test data


		ClientFilter clientFilter= new ClientFilter();
		TestView testView = new TestView();

		myBigReportRegisterBeanGetter.get().genReport(testView,clientFilter);
		assertThat(testView.headData).isNotNull();
		assertThat(testView.headData.title).isEqualTo("Report");
	}
}