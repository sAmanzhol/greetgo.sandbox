package kz.greetgo.sandbox.backend.test.beans;

import kz.greetgo.db.nf36.gen.AuthorType;
import kz.greetgo.db.nf36.gen.DdlGenerator;
import kz.greetgo.db.nf36.gen.JavaGenerator;
import kz.greetgo.db.nf36.gen.ModelCollector;
import kz.greetgo.sandbox.backend.model36.Account;
import kz.greetgo.sandbox.backend.test.util.Modules;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Nf36Generators {

  private JavaGenerator javaGenerator;
  private DdlGenerator ddlGenerator;

  @PostConstruct
  public void initialize() {

    ModelCollector collector = ModelCollector
        .newCollector()
        .setNf3Prefix(/*empty*/"")
        .setNf6Prefix("m.")
        .setNf6TableSeparator("_")
        .setNf6timeField("ts")
        .setNf3CreatedAtField("created_at")
        .setNf3ModifiedAtField("modified_at")
        .setAuthorFields("created_by", "modified_by", "inserted_by", AuthorType.STR, 32)
        .setMoreMethodName("more")
        .setCommitMethodName("commit")
        .setIdLength(32)
        .setLongLength(2000)
        .setShortLength(50)
        .setDefaultLength(300)
        .setEnumLength(50)
        .scanPackageOfClassRecursively(Account.class, true);

    javaGenerator = JavaGenerator.newGenerator(collector)
        .setOutDir(Modules.backend() + "/nf36_gen_src")
        .setCleanOutDirsBeforeGeneration(true)
        .setInterfaceBasePackage("kz.greetgo.sandbox.backend.nf36.view")
        .setImplBasePackage("kz.greetgo.sandbox.backend.nf36.impl")
        .setUpdaterClassName("DbUpdater")
        .setUpserterClassName("DbUpserter")
        .setAbstracting(true)
    ;
  }

  public void generateJava() {
    javaGenerator.generate();
  }
}
