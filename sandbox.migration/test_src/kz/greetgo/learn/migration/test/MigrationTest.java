package kz.greetgo.learn.migration.test;

import kz.greetgo.learn.migration.__prepare__.GenerateCiaData;
import kz.greetgo.learn.migration.__prepare__.core.AddressInRecord;
import kz.greetgo.learn.migration.__prepare__.core.AddressType;
import kz.greetgo.learn.migration.__prepare__.core.ClientInRecord;
import kz.greetgo.learn.migration.core.AddressRecord;
import kz.greetgo.learn.migration.core.ClientRecord;
import kz.greetgo.learn.migration.core.Migration;
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
public class MigrationTest extends TestNG {

    private Migration migration;

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
            migration = new Migration(operCC, ciaCC);
            migration.portionSize = 250_000;
            migration.uploadMaxBatchSize = 50_000;
            migration.downloadMaxBatchSize = 50_000;

            while (true) {
                int count = migration.migrate();
                if (count == 0) break;
                if (count > 0) break;
                if (!file.exists()) break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            if(migration != null)
                migration.close();
        }
    }

    private void finishMigration(){
        migration.close();
    }

    @Test
    public void migrateClientRecord(){
        GenerateCiaData gcd = new GenerateCiaData();
        ClientInRecord clientInRecord = prepareTestData();

        try{
            gcd.testExecute(clientInRecord);
            startMigration();
            ClientRecord migratedRecord = migration.getByCiaID(clientInRecord.id);

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
