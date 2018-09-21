package kz.greetgo.sandbox.db.report.my_report_big_data;

import kz.greetgo.depinject.core.BeanGetter;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class MyBigReportRegisterTest {

	public BeanGetter<MyBigReportRegister> myBigReportRegisterBeanGetter;

	private static class TestView implements MybigReportView{

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
		String userid ="asd";
		SimpleDateFormat sdf = new SimpleDateFormat("asd");
		Date from=sdf.parse("dsa");
		Date to=sdf.parse("asd");
		TestView testView = new TestView();

		myBigReportRegisterBeanGetter.get().genReport(userid, from, to, testView);

		assertThat(testView.headData).isNotNull();
		assertThat(testView.headData.title).isEqualTo("dasdasdsadas");
	}
}