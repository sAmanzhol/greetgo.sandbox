package kz.greetgo.sandbox.db.register_impl.report.my_report;

import kz.greetgo.sandbox.controller.report.my_report.MyReportInData;
import kz.greetgo.sandbox.controller.report.my_report.MyReportRegister;
import kz.greetgo.sandbox.controller.report.my_report.MyReportView;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MyReportRegisterTest {

	@Test
	public void genReport() throws Exception {

		MyReportRegister register = new MyReportRegister();
		final MyReportInData inData[] = new MyReportInData[1];

		register.genReport("asduser", "asdContr", new MyReportView() {

			@Override
			public void generate(MyReportInData inData1) throws Exception {

				inData[0] = inData1;
			}

		});
		assertThat(inData[0].generatedAt).isNotNull();
		assertThat(inData[0].generatedBy).isNotNull();

	}

}

