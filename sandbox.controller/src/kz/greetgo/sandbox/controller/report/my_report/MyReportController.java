package kz.greetgo.sandbox.controller.report.my_report;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.ParPath;
import kz.greetgo.sandbox.controller.util.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Bean
public class MyReportController implements Controller {

	BeanGetter<MyReportRegister> myReportRegister;

	@Mapping("/my_collor_repoty/{type}/{contactId}")
	public void MyReportControllerMethod(@ParPath("contactId") String contactId,
																			 @ParPath("type") String type
																					 ) throws Exception {

		String userId = "asd";


		File file = new File("build/my_report/result."+type);
		file.getParentFile().mkdir();
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			MyReportView viewHtml = getMyView(type,outputStream); //new MyReportViewHtml(outputStream);
			myReportRegister.get().genReport(userId,contactId,viewHtml);
		}


	}

	private MyReportView getMyView(String type, OutputStream outputStream) {
		switch (type){
			case"HTML":return new MyReportViewHtml(outputStream);
		}
		throw new RuntimeException("type is not known " + type);

	}
}