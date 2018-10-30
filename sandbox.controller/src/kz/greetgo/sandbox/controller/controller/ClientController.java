package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ParPath;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.mvc.interfaces.RequestTunnel;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientReqParams;
import kz.greetgo.sandbox.controller.register.CharmRegister;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.register.model.ReportParam;
import kz.greetgo.sandbox.controller.register.report.ReportType;
import kz.greetgo.sandbox.controller.register.report.ReportView;
import kz.greetgo.sandbox.controller.util.Controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

@Bean
@ControllerPrefix("/client")
public class ClientController implements Controller {
    public BeanGetter<ClientRegister> clientRegister;

    public BeanGetter<CharmRegister> charmRegister;

    public BeanGetter<ReportRegister> reportRegister;

    @ToJson
    @OnGet("/getAll")
    public List<Client> getAll(@Par("params") @Json ClientReqParams params) {
        return clientRegister.get().getListByParam(params);
    }

    @ToJson
    @OnGet("/getCount")
    public Long getCount() {
        return clientRegister.get().getCount();
    }

    @ToJson
    @OnGet("/getById")
    public Client getById(@Par("id") Long id) {
        return clientRegister.get().getById(id);
    }

    @ToJson
    @OnGet("/getCharms")
    public List<Charm> getCharms() {
        return charmRegister.get().list();
    }

    @OnPost("/update")
    public void update(@Par("client") @Json Client client) {
        clientRegister.get().update(client);
    }

    @OnPost("/insert")
    public void insert(@Par("client") @Json Client client) {
        clientRegister.get().insert(client);
    }

    @OnPost("/delete")
    public void delete(@Par("id") Long id) {
        clientRegister.get().delete(id);
    }

    @OnGet("/report/{type}")
    public void getClientsReport(@ParPath("type") String type, @Par("username") String username, RequestTunnel tunnel) throws Exception {
        tunnel.setResponseContentType(type);
        tunnel.setResponseHeader("Content-Disposition", "attachment;filename=ClientsReport." + type);
        try(OutputStream out = tunnel.getResponseOutputStream()) {
            ReportType reportType = ReportType.valueOf(type.toUpperCase());
            if (type == null || reportType == null)
                throw new RuntimeException("Неизвестный тип документа");
            reportRegister.get().generate(new ReportParam(username, new Date(), reportType, out));
            tunnel.flushBuffer();
        }
    }
}
