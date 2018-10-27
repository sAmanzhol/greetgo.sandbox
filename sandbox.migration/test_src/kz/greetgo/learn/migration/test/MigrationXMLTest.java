package kz.greetgo.learn.migration.test;

import kz.greetgo.learn.migration.__prepare__.GenerateCiaData;
import kz.greetgo.learn.migration.__prepare__.core.models.*;
import kz.greetgo.learn.migration.core.models.AddressRecord;
import kz.greetgo.learn.migration.core.models.ClientRecord;
import kz.greetgo.learn.migration.core.MigrationXML;
import kz.greetgo.learn.migration.core.models.Gender;
import kz.greetgo.learn.migration.core.models.Phone;
import kz.greetgo.learn.migration.interfaces.ConnectionConfig;
import kz.greetgo.learn.migration.util.ConfigFiles;
import kz.greetgo.learn.migration.util.ConnectionUtils;
import kz.greetgo.learn.migration.util.RND;
import org.testng.TestNG;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@Test
public class MigrationXMLTest extends TestNG {

    private MigrationXML migrationXML;

    private ClientInRecord prepareTestData(){
        ClientInRecord clientInRecord = new ClientInRecord();
        clientInRecord.id           = "TESTID";
        clientInRecord.name         = "TESTNAME";
        clientInRecord.surname      = "TESTNAME";
        clientInRecord.patronymic   = "TESTNAME";
        clientInRecord.birthDate    = new Date();
        clientInRecord.charm        = "TESTCharm";
        clientInRecord.addressList  =  new ArrayList<>();
        clientInRecord.phoneList    =  new ArrayList<>();
        clientInRecord.gender       = Gender.FEMALE;

        AddressInRecord addressInRecordReg = new AddressInRecord();
        addressInRecordReg.street   = "TESTStreet";
        addressInRecordReg.house    = "TESTHouse";
        addressInRecordReg.flat     = "TESTFlat";
        addressInRecordReg.type     = "fact";

        AddressInRecord addressInRecordFact = new AddressInRecord();
        addressInRecordFact.street   = "TESTStreet";
        addressInRecordFact.house    = "TESTHouse";
        addressInRecordFact.flat     = "TESTFlat";
        addressInRecordFact.type     =  "register";

        PhoneInRecord phoneInRecord  = generatePhone();

        clientInRecord.addressList.add(addressInRecordReg);
        clientInRecord.addressList.add(addressInRecordFact);
        clientInRecord.phoneList.add(phoneInRecord);
        return clientInRecord;
    }

    private AddressInRecord generateAddress(){
        AddressInRecord address = new AddressInRecord();
        address.type = (RND.bool(50) ? "fact" : "register");
        address.flat =  RND.str(20);
        address.house =  RND.str(20);
        address.street =  RND.str(20);

        return address;
    }

    private PhoneInRecord generatePhone() {
        PhoneInRecord phoneInRecord = new PhoneInRecord();
        phoneInRecord.number = RND.phoneNum(11);
        phoneInRecord.type = (RND.bool(33) ? PhoneRecordType.HOME : (RND.bool(33) ? PhoneRecordType.MOBILE : PhoneRecordType.WORK));
        return phoneInRecord;
    }

    private void startMigration () throws Exception{
        final File file = new File("build/__migration__");
        file.getParentFile().mkdirs();
        file.createNewFile();

        ConnectionConfig operCC = ConnectionUtils.fileToConnectionConfig(ConfigFiles.operDb());
        ConnectionConfig ciaCC = ConnectionUtils.fileToConnectionConfig(ConfigFiles.ciaDb());

        try {
            migrationXML = new MigrationXML(operCC, ciaCC);
            migrationXML.portionSize = 250_000;
            migrationXML.uploadMaxBatchSize = 50_000;
            migrationXML.downloadMaxBatchSize = 50_000;

            while (true) {
                int count = migrationXML.migrate();
                if (count == 0) break;
                if (count > 0) break;
                if (!file.exists()) break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            if(migrationXML != null)
                migrationXML.close();
        }
    }

    private void finishMigration(){
        migrationXML.close();
    }

    @Test
    public void migrateClientRecord(){
        GenerateCiaData gcd = new GenerateCiaData();
        ClientInRecord clientInRecord = prepareTestData();

        try{
            gcd.testExecute(clientInRecord);
            startMigration();
            ClientRecord migratedRecord = migrationXML.getClientByCiaID(clientInRecord.id);

                assertThat(migratedRecord).isNotNull();
                assertThat(migratedRecord.id).isNotNull();
                assertThat(migratedRecord.name).isNotNull();
                assertThat(migratedRecord.surname).isNotNull();
                assertThat(migratedRecord.birthDate).isNotNull();
                assertThat(migratedRecord.charm).isNotNull();

                assertThat(migratedRecord.id).isEqualTo(clientInRecord.id);
                assertThat(migratedRecord.name).isEqualTo(clientInRecord.name);
                assertThat(migratedRecord.surname).isEqualTo(clientInRecord.surname);
                assertThat(migratedRecord.charm.name).isEqualTo("-1"); // -1 если нет такого характера в справочнике

            finishMigration();
        }
        catch (Exception e ){
            e.printStackTrace();
            System.out.println("Cannot insert data to Transition_client");
            finishMigration();
            return;
        }

       // assertThat()

    }


    @Test
    public void migrateClientAddressRecord(){
        GenerateCiaData gcd = new GenerateCiaData();
        ClientInRecord clientInRecord = prepareTestData();
        clientInRecord.addressList = new ArrayList<>();
        AddressInRecord addressInRecord = generateAddress();
        clientInRecord.addressList.add(addressInRecord);

        try{
            gcd.testExecute(clientInRecord);
            startMigration();
            ClientRecord migratedRecord = migrationXML.getClientByCiaID(clientInRecord.id);
            //
            //
            List<AddressRecord> addressRecordList =  migrationXML.getClientAdrsByCiaID(clientInRecord.id);
            //
            //
            assertThat(addressRecordList).isNotNull();
            assertThat(addressRecordList).isNotEmpty();
            assertThat(addressRecordList.get(0).flat).isEqualTo(addressInRecord.flat);
            assertThat(addressRecordList.get(0).house).isEqualTo(addressInRecord.house);
            assertThat(addressRecordList.get(0).street).isEqualTo(addressInRecord.street);

            finishMigration();
        }
        catch (Exception e ){
            e.printStackTrace();
            System.out.println("Cannot insert data to Transition_client");
            finishMigration();
            return;
        }

        // assertThat()

    }


    @Test
    public void migrateClientPhoneRecord(){
        GenerateCiaData gcd = new GenerateCiaData();
        ClientInRecord clientInRecord = prepareTestData();
        clientInRecord.phoneList = new ArrayList<>();
        PhoneInRecord phoneInRecord = generatePhone();
        clientInRecord.phoneList.add(phoneInRecord);

        try{
            gcd.testExecute(clientInRecord);
            startMigration();
            ClientRecord migratedRecord = migrationXML.getClientByCiaID(clientInRecord.id);
            //
            //
            List<Phone> phoneRecordList =  migrationXML.getClientPhnsByCiaID(clientInRecord.id);
            //
            //
            assertThat(phoneRecordList).isNotNull();
            assertThat(phoneRecordList).isNotEmpty();

            finishMigration();
        }
        catch (Exception e ){
            e.printStackTrace();
            System.out.println("Cannot insert data to Transition_client");
            finishMigration();
            return;
        }
    }

    @Test
    public void testClientPhoneNumToError(){
        GenerateCiaData gcd = new GenerateCiaData();
        ClientInRecord clientInRecord = prepareTestData();
        clientInRecord.phoneList.get(0).number = null;

        try{
            Long ciaRecordId = gcd.testExecute(clientInRecord);
            startMigration();
            ClientRecord migratedRecord = migrationXML.getClientByCiaID(clientInRecord.id);
            //
            //
            String statusMess =  gcd.getStatus(ciaRecordId);
            //
            //
            assertThat(statusMess).isNotNull();
            assertThat(statusMess).isEqualTo("ERROR");

            finishMigration();
        }
        catch (Exception e ){
            e.printStackTrace();
            System.out.println("Cannot insert data to Transition_client");
            finishMigration();
            return;
        }
    }


    @Test
    public void testClientToError(){
        GenerateCiaData gcd = new GenerateCiaData();
        ClientInRecord clientInRecord = prepareTestData();
        clientInRecord.name = null;
        clientInRecord.surname = null;
        clientInRecord.birthDate = null;

        try{
            Long ciaRecordId = gcd.testExecute(clientInRecord);
            startMigration();
            //
            //
            String statusMess =  gcd.getStatus(ciaRecordId);
            //
            //
            assertThat(statusMess).isNotNull();
            assertThat(statusMess).isEqualTo("ERROR");

            finishMigration();
        }
        catch (Exception e ){
            e.printStackTrace();
            System.out.println("Cannot insert data to Transition_client");
            finishMigration();
            return;
        }
    }


}
