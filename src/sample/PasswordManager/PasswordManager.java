package sample.PasswordManager;

import sample.CryptoService.CryptoProvider;
import sample.Repository.Query;
import sample.Repository.Repository;
import sample.Repository.StorageFactory;
import sample.SettingsService.Settings;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dmitry on 16.12.16.
 */
public class PasswordManager {
    Settings settings;
    CryptoProvider cryptoProvider;
    SettingableRepository<Profile> repository;
    List<Profile> profiles;
    StorageFactory storageFactory;
    public PasswordManager(Settings settings, CryptoProvider cryptoProvider, StorageFactory storageFactory){
        this.settings = settings;
        this.cryptoProvider = cryptoProvider;
        this.storageFactory = storageFactory;
        this.repository = (SettingableRepository<Profile>)this.storageFactory.createRepository();
    }
    public boolean openBase(){
        this.profiles = repository.findAll();
        if(this.profiles == null)
            return false;
        return true;
    }
    public boolean saveBase(){
        return repository.save();
    }
    public boolean createBase(){
        this.repository.getSettings().set("path", this.settings.get("pathDB"));
        this.repository.init();
        return true;
    }
    public void addProfile(String name, String password){
        this.repository.add(new Profile(name, password));
    }
    public void removeProfile(Profile profile){
        this.repository.remove(profile);
    }
    public void changeProfile(Profile profile, String name, String password){
        profiles.get(profiles.indexOf(profile)).setName(name);
        profiles.get(profiles.indexOf(profile)).setName(password);
    }
    public List<Profile> find(Profile p){
        return this.repository.find(this.storageFactory.createQuery(p));
    }
    public List<Profile> find(Query q){
        return this.repository.find(q);
    }
    public List<Profile> getProfiles(){
        return this.profiles;
    }
    public void saveSettings(){
        this.settings.save("pm.set");
    }
    public void loadSettings(){
        this.settings = Settings.load("pn.set");
    }
}
