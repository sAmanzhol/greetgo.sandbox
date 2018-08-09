package kz.greetgo.sandbox.register.impl;

import kz.greetgo.mvc.security.SerializeUtil;
import kz.greetgo.mvc.util.Base64Util;
import kz.greetgo.sandbox.controller.register.model.SessionInfo;
import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Набор автоматизированных тестов для тестирования методов класса {@link SecurityRegister}
 */
public class SecurityRegisterTest {

  /**
   * Ссылка на тестируемый объект. Инициируется в методе {@link #prepareTokenRegister()} перед запуском каждого теста
   */
  private SecurityRegister securityRegister;

  /**
   * Этот метод запускается перед каждым тестом. Он подготавливает поле {@link #securityRegister}
   *
   * @throws Exception пробрасываем, чтобы не требовались try/catch-блоки
   */
  @BeforeMethod
  public void prepareTokenRegister() throws Exception {
    securityRegister = new SecurityRegister();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss_SSS");
    String ext = sdf.format(new Date());

    // Перед каждым тестом придумываем новые файлы, где система будет хранить ключи для ассиместричного шифрования
    securityRegister.privateKeyFile = new File("build/TokenRegisterTest/" + ext + "_privateKeyFile");
    securityRegister.publicKeyFile = new File("build/TokenRegisterTest/" + ext + "_publicKeyFile");

    //Инициируем внутренние ресурсы
    securityRegister.afterInject();
  }

  /**
   * С помощью этого enum-а задаются различные состояния для тестирования
   */
  @SuppressWarnings("unused")
  enum BreakKeyFiles {
    DO_NOT_BREAK_ANYMORE(false, false),
    BREAK_PRIVATE_KEY_FILE(true, false),
    BREAK_PUBLIC_KEY_FILE(false, true),
    BREAK_BOTH_KEY_FILES(true, true);

    /**
     * Если <code>true</code>, то необходимо создасть состояние, в котором приватный файл ключа будет сломан
     */
    final boolean breakPrivateKeyFile;

    /**
     * Если <code>true</code>, то необходимо создасть состояние, в котором публичный файл ключа будет сломан
     */
    final boolean breakPublicKeyFile;

    BreakKeyFiles(boolean breakPrivateKeyFile, boolean breakPublicKeyFile) {
      this.breakPrivateKeyFile = breakPrivateKeyFile;
      this.breakPublicKeyFile = breakPublicKeyFile;
    }

    /**
     * Подготавливает данные для Data Provider
     *
     * @return все значения представленные в виде, пригодном для Data Provider
     */
    static Object[][] dataProviderReturn() {
      Object[][] ret = new Object[values().length][];
      for (int i = 0, n = values().length; i < n; i++) {
        ret[i] = new Object[]{values()[i]};
      }
      return ret;
    }
  }

  @DataProvider
  public Object[][] leftFilesDataProvider() {
    return BreakKeyFiles.dataProviderReturn();
  }

  @Test(dataProvider = "leftFilesDataProvider")
  public void createToken_decryptToken_тестируемРазличныеНачальныеСостояния
    (BreakKeyFiles breakKeyFiles) throws Exception {

    //ломаем соответствующие файлы
    breakFiles(breakKeyFiles);

    // берём какую-нибудь сессию
    SessionInfo sessionInfo = new SessionInfo(RND.str(10));

    // и вызываем последовательно тестируемые методы создания и дешифровки токена

    //
    //
    String token = securityRegister.createToken(sessionInfo);
    SessionInfo sessionInfo2 = securityRegister.decryptToken(token);
    //
    //

    // Проверяем, чтобы всё работало правильно

    assertThat(sessionInfo2.personId).isEqualTo(sessionInfo.personId);
    assertThat(sessionInfo2.createdAt.getTime()).isEqualTo(sessionInfo.createdAt.getTime());
  }

  private void breakFiles(BreakKeyFiles breakKeyFiles) throws Exception {
    if (breakKeyFiles.breakPrivateKeyFile) breakFile(securityRegister.privateKeyFile);
    if (breakKeyFiles.breakPublicKeyFile) breakFile(securityRegister.publicKeyFile);
    securityRegister.afterInject();
  }

  private void breakFile(File file) throws Exception {
    file.getParentFile().mkdirs();
    file.delete();
    try (FileOutputStream out = new FileOutputStream(file)) {
      out.write("Broken Data".getBytes(StandardCharsets.UTF_8));
    }
  }

  @Test(dataProvider = "leftFilesDataProvider")
  public void createToken_decryptToken_twice(BreakKeyFiles breakKeyFiles) throws Exception {
    SessionInfo sessionInfo = new SessionInfo(RND.str(10));

    {
      String token = securityRegister.createToken(sessionInfo);
      SessionInfo sessionInfo2 = securityRegister.decryptToken(token);

      assertThat(sessionInfo2.personId).isEqualTo(sessionInfo.personId);
      assertThat(sessionInfo2.createdAt.getTime()).isEqualTo(sessionInfo.createdAt.getTime());
    }

    breakFiles(breakKeyFiles);

    {
      String token = securityRegister.createToken(sessionInfo);
      SessionInfo sessionInfo2 = securityRegister.decryptToken(token);

      assertThat(sessionInfo2.personId).isEqualTo(sessionInfo.personId);
      assertThat(sessionInfo2.createdAt.getTime()).isEqualTo(sessionInfo.createdAt.getTime());
    }
  }

  @Test
  public void createToken_onNull() throws Exception {

    String token = securityRegister.createToken(null);

    assertThat(token).isNull();

  }

  @Test
  public void decryptToken_onNull() throws Exception {

    SessionInfo sessionInfo = securityRegister.decryptToken(null);

    assertThat(sessionInfo).isNull();

  }

  @Test
  public void decryptToken_onEmpty() throws Exception {

    SessionInfo sessionInfo = securityRegister.decryptToken("");

    assertThat(sessionInfo).isNull();

  }

  @Test
  public void decryptToken_onSpacesOnly() throws Exception {

    SessionInfo sessionInfo = securityRegister.decryptToken("     ");

    assertThat(sessionInfo).isNull();

  }

  @Test
  public void decryptToken_onLeftToken() throws Exception {

    SessionInfo sessionInfo = securityRegister.decryptToken("hjb56hjb6hjb7hjb4b3hjb2hjb54hj67hn3kjn2354l6lkm67klm4n3hk3");

    assertThat(sessionInfo).isNull();

  }

  @Test
  public void createToken_breakKeyFiles_decryptToken() throws Exception {
    SessionInfo sessionInfo = new SessionInfo(RND.str(10));

    String token = securityRegister.createToken(sessionInfo);

    breakFiles(BreakKeyFiles.BREAK_BOTH_KEY_FILES);

    SessionInfo sessionInfo2 = securityRegister.decryptToken(token);

    assertThat(sessionInfo2).isNull();
  }

  @Test
  public void decryptToken_leftDeserialization() throws Exception {
    byte[] leftBytes = RND.byteArray(100);
    byte[] encryptedBytes = securityRegister.securityCrypto.encrypt(leftBytes);
    String leftToken = Base64Util.bytesToBase64(encryptedBytes);

    SessionInfo sessionInfo = securityRegister.decryptToken(leftToken);

    assertThat(sessionInfo).isNull();

  }

  @Test
  public void decryptToken_leftDecryption() throws Exception {
    byte[] leftBytes = RND.byteArray(100);
    String leftToken = Base64Util.bytesToBase64(leftBytes);

    SessionInfo sessionInfo = securityRegister.decryptToken(leftToken);

    assertThat(sessionInfo).isNull();
  }

  public static class LeftObject implements Serializable {}

  @Test
  public void decryptToken_leftSerializedObject() throws Exception {
    LeftObject leftObject = new LeftObject();
    byte[] bytes = SerializeUtil.serialize(leftObject);
    byte[] encryptedBytes = securityRegister.securityCrypto.encrypt(bytes);
    String leftToken = Base64Util.bytesToBase64(encryptedBytes);

    SessionInfo sessionInfo = securityRegister.decryptToken(leftToken);

    assertThat(sessionInfo).isNull();
  }

  @Test
  public void encryptPassword() throws Exception {

    String password = RND.str(10);

    //
    //
    String encryptPassword = securityRegister.encryptPassword(password);
    //
    //

    assertThat(encryptPassword).isNotNull();

    String encryptPassword2 = securityRegister.encryptPassword(password);

    assertThat(encryptPassword).isNotEqualTo(encryptPassword2);
  }

  @Test
  public void encryptPassword_null() throws Exception {

    //
    //
    String encryptPassword = securityRegister.encryptPassword(null);
    //
    //

    assertThat(encryptPassword).isNull();

  }

  @Test
  public void encryptPassword_validatePassword() throws Exception {

    String password = RND.str(10);

    //
    //
    String encryptedPassword = securityRegister.encryptPassword(password);
    //
    //

    //
    //
    boolean validation = securityRegister.validatePassword(password, encryptedPassword);
    //
    //

    assertThat(validation).isTrue();
  }
}