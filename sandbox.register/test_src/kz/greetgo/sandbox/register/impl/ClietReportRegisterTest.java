package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.msoffice.xlsx.fastgen.simple.SimpleFastXlsxFile;
import kz.greetgo.msoffice.xlsx.fastgen.simple.SimpleRowStyle;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.register.report.view.ClientReportViewXLSX;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.util.Date;

public class ClietReportRegisterTest extends ParentTestNg {
    public BeanGetter<ReportRegister> clientReportRegister;
    @Test
    void testView(){

//        clientReportRegister.get().generate("Test",new Date(),new ClientReportViewXLSX());
    }


    @Test
    public void test1() throws Exception {
        SimpleFastXlsxFile x = new SimpleFastXlsxFile("build/tmp");

        x.newSheet(new double[]{23.3, 11.1, 21.12});

        x.appendRow(SimpleRowStyle.NORMAL, new String[]{"sad", "fdsfdsf", "fdsfds"});
        x.appendRow(SimpleRowStyle.GREEN, new String[]{"sad1", "fdsfdsf1", "fdsfds1"});
        x.appendRow(SimpleRowStyle.NORMAL, new String[]{"sad2", "fdsfdsf2", "fdsfds2"});

        x.newSheet(new double[]{11.3, 11.1, 21.12});

        x.appendRow(SimpleRowStyle.NORMAL, new String[]{"1sad", "2fdsfdsf", "3fdsfds"});
        x.appendRow(SimpleRowStyle.GREEN, new String[]{"1sad1", "2fdsfdsf1", "3fdsfds1"});
        x.appendRow(SimpleRowStyle.NORMAL, new String[]{"1sad2", "2fdsfdsf2", "3fdsfds2"});

        x.complete(new FileOutputStream("build/SimpleXlsxFileTest_test1.xlsx"));
    }

}
