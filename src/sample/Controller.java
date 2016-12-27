package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import sample.CryptoService.AesCryptoProvider;
import sample.CryptoService.CryptoProvider;
import sample.PasswordManager.FileStorageFactory;
import sample.PasswordManager.OpenBaseException;
import sample.PasswordManager.PasswordManager;
import sample.PasswordManager.Profile;
import sample.Repository.Query;
import sample.Repository.StorageFactory;
import sample.SettingsService.Settings;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class Controller {

    public TableView<Profile> tableProfiles;
    public TextField filtersName;
    private PasswordManager manager;
    private Settings managerSettings;
    private Stage stage;

    public boolean closed = false;

    public void setStage(Stage stage){
        this.stage = stage;
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
    static byte[] sha1(byte[] input){
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] result = mDigest.digest(input);
        return result;
    }
    static String showPasswordDialog(){
        TextInputDialog dialog = new TextInputDialog("password");
        dialog.setTitle("Input your master key");
        dialog.setHeaderText("Input secret key");
        dialog.setContentText("Please enter your password:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        return null;
    }
    @FXML
    public void initialize() {
        String userInput = showPasswordDialog();
        if (userInput == null) {
            this.closed = true;
            return;
        }
        try {
            byte[] hashMasterKey = sha1(userInput);
            byte[] doubleHash = sha1(hashMasterKey);
            managerSettings = Settings.load("settings.ini");
            byte[] hashedPassword = (byte[]) managerSettings.get("hashedPassword");
            while (!Arrays.equals(hashedPassword, doubleHash)) {
                userInput = showPasswordDialog();
                if (userInput == null)
                    return;
                hashMasterKey = sha1(userInput);
                doubleHash = sha1(hashMasterKey);
                managerSettings = Settings.load("settings.ini");
                hashedPassword = (byte[]) managerSettings.get("hashedPassword");
            }
            HashMap<String, Object> aesCryptoSettings = new HashMap<>();
            aesCryptoSettings.put("key", Arrays.copyOfRange(hashMasterKey, 0, 16));
            aesCryptoSettings.put("iv", Arrays.copyOfRange(hashMasterKey, 0, 16));
            ((CryptoProvider)managerSettings.get("CryptoProvider")).setSettings(new Settings(aesCryptoSettings));
            manager = new PasswordManager((Settings) managerSettings.get("PasswordManager"), (CryptoProvider) managerSettings.get("CryptoProvider"), (StorageFactory) managerSettings.get("StorageFactory"));
            //manager.openBase();
            //manager.addProfile("Вася", "204655");
            //tableProfiles.getItems().addAll(manager.getProfiles());
            //manager.saveBase();
        } catch (IOException e) {
            byte[] hashMasterKey = sha1(userInput);
            byte[] doubleHash = sha1(hashMasterKey);
            HashMap<String, Object> settings = new HashMap<>();
            HashMap<String, Object> storageFactorySettings = new HashMap<>();
            HashMap<String, Object> fileProfileRepositorySettings = new HashMap<>();
            fileProfileRepositorySettings.put("path", "my.kdb");
            storageFactorySettings.put("FileProfileRepositorySettings", new Settings(fileProfileRepositorySettings));
            settings.put("FileStorageSettings", new Settings(storageFactorySettings));
            settings.put("PasswordManager", new Settings(new HashMap<>()));
            ((Settings) settings.get("PasswordManager")).set("pathDB", "my.kdb");
            managerSettings = new Settings(settings);
            managerSettings.set("hashedPassword", doubleHash);
            AesCryptoProvider aesCryptoProvider = new AesCryptoProvider((Settings) settings.get("AesCryptoSettings"));
            FileStorageFactory fileStorageFactory = new FileStorageFactory((Settings) settings.get("FileStorageSettings"));
            managerSettings.set("CryptoProvider", aesCryptoProvider);
            managerSettings.set("StorageFactory", fileStorageFactory);
            manager = new PasswordManager((Settings) settings.get("PasswordManager"), aesCryptoProvider, fileStorageFactory);
            managerSettings.save("settings.ini");
        }
        tableProfiles.setEditable(true);
        tableProfiles.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("name"));
        tableProfiles.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("password"));
        ((TableColumn<Profile, String>)tableProfiles.getColumns().get(0)).setCellFactory(TextFieldTableCell.forTableColumn());
        ((TableColumn<Profile, String>)tableProfiles.getColumns().get(1)).setCellFactory(TextFieldTableCell.forTableColumn());
        ((TableColumn<Profile, String>)tableProfiles.getColumns().get(0)).setOnEditCommit(t -> {
                    Profile profile = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    manager.changeProfile(profile, t.getNewValue(), profile.getPassword() );
                    profile.setName(t.getNewValue());
                }
        );
        ((TableColumn<Profile, String>)tableProfiles.getColumns().get(1)).setOnEditCommit(t -> {
                    Profile profile = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    manager.changeProfile(profile, profile.getName(), t.getNewValue() );
                    profile.setPassword(t.getNewValue());
                }
        );
    }

    public void onInitClick(){
        this.manager.createBase();
    }

    public void onOpenClick(){
        try {
            this.manager.openBase();
        } catch (OpenBaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Can't open base!");

            alert.showAndWait();
        }
        this.tableProfiles.setItems(FXCollections.observableArrayList(this.manager.getProfiles()));
    }

    public void onSaveClick(){
        this.manager.saveBase();
    }

    public void onCLoseClick(){
        try {
            stage.close();
        } catch(NullPointerException e){
            System.err.println(e.getMessage());
        }
    }

    public void onAddProfile(){
        Profile profile = new Profile("","");
        this.tableProfiles.getItems().add(profile);
        this.manager.addProfile(profile.getName(), profile.getPassword());
    }
    public void onRemoveProfile(){
        Profile profile = this.tableProfiles.getSelectionModel().getSelectedItem();
        this.tableProfiles.getItems().remove(profile);
        this.manager.removeProfile(profile);
    }
    public void onFind(){
        String[] names = this.filtersName.getText().split(" ");
        Query q1 = this.manager.getStorageFactory().createQuery(this.manager.prepareProfileToQuery(new Profile(names[0], "")));
        Query q2 = this.manager.getStorageFactory().createQuery(this.manager.prepareProfileToQuery(new Profile(names[1], "")));
        Query or = this.manager.getStorageFactory().createQueryOr(q1, q2);
        this.tableProfiles.setItems(FXCollections.observableList(this.manager.find(or)));
    }
}
