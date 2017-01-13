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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent)loader.load();
        Controller controller = (Controller)loader.getController();
        if(!controller.closed){
            controller.setStage(primaryStage);
            primaryStage.setTitle("Password manager");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        }
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
        launch(args);

//        PasswordManager manager = new PasswordManager((Settings) settings.get("PasswordManager"), aesCryptoProvider, fileStorageFactory);
////        manager.createBase();
////        manager.addProfile("Вася", "204564");
////        manager.addProfile("Коля", "2445");
////        manager.addProfile("Антон", "4656464");
////        manager.saveBase();
//        manager.openBase();
//        List<Profile> finded = manager.find(new ListedQueryOr(new FileProfileQuery(new Profile("Вася", null)), new FileProfileQuery(new Profile("Коля", null))));
//        finded.get(1).encrypt(aesCryptoProvider);
//        System.out.print(finded.get(1).getName());
//        finded.get(1).decrypt(aesCryptoProvider);
//        System.out.print(finded.get(1).getName());
    }
}
