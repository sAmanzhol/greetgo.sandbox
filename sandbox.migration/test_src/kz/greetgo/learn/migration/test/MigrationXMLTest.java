package kz.greetgo.learn.migration.test;

import kz.greetgo.learn.migration.__prepare__.GenerateCiaData;
import kz.greetgo.learn.migration.__prepare__.core.models.AddressInRecord;
import kz.greetgo.learn.migration.__prepare__.core.models.AddressType;
import kz.greetgo.learn.migration.__prepare__.core.models.ClientInRecord;
import kz.greetgo.learn.migration.core.models.ClientRecord;
import kz.greetgo.learn.migration.core.MigrationXML;
import kz.greetgo.learn.migration.interfaces.ConnectionConfig;
import kz.greetgo.learn.migration.util.ConfigFiles;
import kz.greetgo.learn.migration.util.ConnectionUtils;
import org.testng.TestNG;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

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

        AddressInRecord addressInRecordReg = new AddressInRecord();
        addressInRecordReg.street   = "TESTStreet";
        addressInRecordReg.house    = "TESTHouse";
        addressInRecordReg.flat     = "TESTFlat";
        addressInRecordReg.type     = AddressType.REGISTER.toString();

        AddressInRecord addressInRecordFact = new AddressInRecord();
        addressInRecordFact.street   = "TESTStreet";
        addressInRecordFact.house    = "TESTHouse";
        addressInRecordFact.flat     = "TESTFlat";
        addressInRecordFact.type     = AddressType.FACT.toString();

        clientInRecord.addressList.add(addressInRecordReg);
        clientInRecord.addressList.add(addressInRecordFact);

        return clientInRecord;
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
            ClientRecord migratedRecord = migrationXML.getByCiaID(clientInRecord.id);

                assertThat(migratedRecord).isNotNull();
                assertThat(migratedRecord.id).isNotNull();
                assertThat(migratedRecord.name).isNotNull();
                assertThat(migratedRecord.surname).isNotNull();
                assertThat(migratedRecord.birthDate).isNotNull();
                assertThat(migratedRecord.charm).isNotNull();

                assertThat(migratedRecord.id).isEqualTo(clientInRecord.id);
                assertThat(migratedRecord.name).isEqualTo(clientInRecord.name);
                assertThat(migratedRecord.surname).isEqualTo(clientInRecord.surname);
                assertThat(migratedRecord.charm.name).isEqualTo(clientInRecord.charm);

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
    public void checkAddressToError(){

    }


}
