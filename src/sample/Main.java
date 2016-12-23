package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.CryptoService.AesCryptoProvider;
import sample.PasswordManager.FileProfileQuery;
import sample.PasswordManager.FileStorageFactory;
import sample.PasswordManager.PasswordManager;
import sample.PasswordManager.Profile;
import sample.Repository.ListedQueryOr;
import sample.SettingsService.Settings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    static byte[] sha1(String input){
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] result = mDigest.digest(input.getBytes());
        return result;
    }

    public static void main(String[] args) {
//        launch(args);
        String masterKey = "encryption";
        byte[] hashMasterKey = sha1(masterKey);
        HashMap<String, Object> settings = new HashMap<>();
        HashMap<String, Object> storageFactorySettings = new HashMap<>();
        HashMap<String, Object> fileProfileRepositorySettings = new HashMap<>();
        HashMap<String, Object> aesCryptoSettings = new HashMap<>();
        aesCryptoSettings.put("key", Arrays.copyOfRange(hashMasterKey, 0, 16));
        aesCryptoSettings.put("iv", Arrays.copyOfRange(hashMasterKey, 0, 16));
        fileProfileRepositorySettings.put("path", "my.kdb");
        storageFactorySettings.put("FileProfileRepositorySettings", new Settings(fileProfileRepositorySettings));
        settings.put("AesCryptoSettings", new Settings(aesCryptoSettings));
        settings.put("FileStorageSettings", new Settings(storageFactorySettings));
        settings.put("PasswordManager", new Settings(new HashMap<>()));
        ((Settings)settings.get("PasswordManager")).set("pathDB", "my.kdb");
        AesCryptoProvider aesCryptoProvider = new AesCryptoProvider((Settings) settings.get("AesCryptoSettings"));
        FileStorageFactory fileStorageFactory = new FileStorageFactory((Settings) settings.get("FileStorageSettings"));
        PasswordManager manager = new PasswordManager((Settings) settings.get("PasswordManager"), aesCryptoProvider, fileStorageFactory);
//        manager.createBase();
//        manager.addProfile("Вася", "204564");
//        manager.addProfile("Коля", "2445");
//        manager.addProfile("Антон", "4656464");
//        manager.saveBase();
        manager.openBase();
        List<Profile> finded = manager.find(new ListedQueryOr(new FileProfileQuery(new Profile("Вася", null)), new FileProfileQuery(new Profile("Коля", null))));
        finded.get(1).encrypt(aesCryptoProvider);
        System.out.print(finded.get(1).getName());
        finded.get(1).decrypt(aesCryptoProvider);
        System.out.print(finded.get(1).getName());
    }
}
