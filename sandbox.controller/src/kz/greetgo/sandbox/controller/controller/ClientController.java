package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnDelete;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.mvc.interfaces.RequestTunnel;
import kz.greetgo.sandbox.controller.controller.report.ClientReportView;
import kz.greetgo.sandbox.controller.controller.report.ClientReportViewPdf;
import kz.greetgo.sandbox.controller.controller.report.ClientReportViewXlsx;
import kz.greetgo.sandbox.controller.model.ClientDisplay;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;
import kz.greetgo.sandbox.controller.model.ClientToSave;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.util.Controller;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

@Bean
@ControllerPrefix("/client")
public class ClientController implements Controller {

  public BeanGetter<ClientRegister> clientRegister;

  @ToJson
  @OnPost("/render")
  public void render(@Json @Par("filter") ClientToFilter filter, RequestTunnel tunnel) throws Exception {
    String userId = "User1"; // get it from session
    String type = "pdf"; // get it from content type

    tunnel.setResponseHeader("Content-Disposition", "attachment; filename=" + userId + "_report." + type);
    OutputStream out = tunnel.getResponseOutputStream();

    PrintStream printStream = new PrintStream(out, false, "UTF-8");

    ClientReportView view = getMyView(type, printStream);

    clientRegister.get().generateReport(filter, userId, new Date(), view);

    printStream.flush();
    tunnel.flushBuffer();
  }

  private ClientReportView getMyView(String type, PrintStream printStream) {
    switch (type) {
      case "pdf":
        return new ClientReportViewPdf(printStream);
      case "xlsx":
        return new ClientReportViewXlsx(printStream);
    }
    throw new RuntimeException("Unknown type = " + type);
  }

  @ToJson
  @OnGet("/list")
  public List<ClientRecord> list(@Json @Par("filter") ClientToFilter filter) {
    return clientRegister.get().list(filter);
  }

  @ToJson
  @OnGet("/count")
  public int count(@Json @Par("filter") ClientToFilter filter) {
    return clientRegister.get().count(filter);
  }

  @ToJson
  @OnPost("/save")
  public ClientRecord save(@Json @Par("clientToSave") ClientToSave clientToSave) {
    return clientRegister.get().save(clientToSave);
  }

  @ToJson
  @OnGet("/details")
  public ClientDisplay details(@Par("id") int id) {
    return clientRegister.get().details(id);
  }

  @ToJson
  @OnDelete("/delete")
  public void delete(@Par("id") int id) {
    clientRegister.get().delete(id);
  }
}
