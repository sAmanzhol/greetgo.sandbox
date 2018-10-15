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
import kz.greetgo.mvc.interfaces.BinResponse;
import kz.greetgo.sandbox.controller.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;
import kz.greetgo.sandbox.controller.model.ClientToSave;
import kz.greetgo.sandbox.controller.model.RenderFilter;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.report.ClientReportView;
import kz.greetgo.sandbox.controller.report.ClientReportViewPdf;
import kz.greetgo.sandbox.controller.report.ClientReportViewXlsx;
import kz.greetgo.sandbox.controller.util.Controller;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SuppressWarnings("WeakerAccess, unused")
@Bean
@ControllerPrefix("/client")
public class ClientController implements Controller {

  public BeanGetter<ClientRegister> clientRegister;

  @OnGet("/render")
  public void render(@Json @Par("filter") ClientToFilter filter, @Par("type") String type, @Par("username") String author, BinResponse binResponse) throws Exception {
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

    Date generatedAt = new Date();

    binResponse.setFilename(author + "_" + df.format(generatedAt) + "." + type);
    binResponse.setContentTypeByFilenameExtension();

    ClientReportView view = getMyView(type, binResponse.out());

    RenderFilter renderFilter = new RenderFilter(filter, author, generatedAt);
    clientRegister.get().render(renderFilter, view);

    binResponse.flushBuffers();
  }

  // FIXME: 10/15/18 Переименуй принтСтримы в аутпутСтрим !везде где надо!
  private ClientReportView getMyView(String type, OutputStream outputStream) throws Exception {
    switch (type) {
      case "pdf":
        return new ClientReportViewPdf(outputStream);
      case "xlsx":
        return new ClientReportViewXlsx(outputStream);
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
  public ClientDetails details(@Par("id") int id) {
    return clientRegister.get().details(id);
  }

  @ToJson
  @OnDelete("/delete")
  public void delete(@Par("id") int id) {
    clientRegister.get().delete(id);
  }
}
