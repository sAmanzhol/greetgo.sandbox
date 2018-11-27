package kz.greetgo.sandbox.backend.test.beans;

import kz.greetgo.db.nf36.gen.AuthorType;
import kz.greetgo.db.nf36.gen.DdlGenerator;
import kz.greetgo.db.nf36.gen.JavaGenerator;
import kz.greetgo.db.nf36.gen.ModelCollector;
import kz.greetgo.db.nf36.gen.SqlDialectPostgres;
import kz.greetgo.sandbox.backend.configuration.logging.LOG;
import kz.greetgo.sandbox.backend.configuration.util.FileUtil;
import kz.greetgo.sandbox.backend.model36.Account;
import kz.greetgo.sandbox.backend.test.util.Modules;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Nf36Generators {

  private final LOG log = LOG.byClass(getClass());

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

    ddlGenerator = DdlGenerator.newGenerator(collector)
        .setSqlDialect(new SqlDialectPostgres())
        .setCommandSeparator(";;")
    ;
  }

  public void generateJava() {
    javaGenerator.generate();
  }

  public String generateSqlFilesAndGetText() throws Exception {
    List<File> sqlFileList = new ArrayList<>();
    {
      File outFile = new File(Modules.backend() + "/build/gen_sql/001_nf3_tables.sql");
      ddlGenerator.generateNf3Tables(outFile);
      sqlFileList.add(outFile);
      log.info(() -> "Сгенерирован SQL file: " + outFile.getPath());
    }

    {
      File outFile = new File(Modules.backend() + "/build/gen_sql/002_nf6_tables.sql");
      ddlGenerator.generateNf6Tables(outFile);
      sqlFileList.add(outFile);
      log.info(() -> "Сгенерирован SQL file: " + outFile.getPath());
    }

    {
      File outFile = new File(Modules.backend() + "/build/gen_sql/003_nf3_references.sql");
      ddlGenerator.generateNf3References(outFile);
      sqlFileList.add(outFile);
      log.info(() -> "Сгенерирован SQL file: " + outFile.getPath());
    }

    {
      File outFile = new File(Modules.backend() + "/build/gen_sql/004_nf6_id_references.sql");
      ddlGenerator.generateNf6IdReferences(outFile);
      sqlFileList.add(outFile);
      log.info(() -> "Сгенерирован SQL file: " + outFile.getPath());
    }


    {
      File outFile = new File(Modules.backend() + "/build/gen_sql/005_comments.sql");
      ddlGenerator.generateComments(outFile);
      sqlFileList.add(outFile);
      log.info(() -> "Сгенерирован SQL file: " + outFile.getPath());
    }

    return sqlFileList.stream()
        .map(File::toPath)
        .map(FileUtil::pathToStr)
        .collect(Collectors.joining(";;"));
  }
}
