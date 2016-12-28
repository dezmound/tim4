package sample.PasswordManager;

import sample.CryptoService.CryptoProvider;
import sample.Repository.Query;
import sample.Repository.Repository;
import sample.Repository.StorageFactory;
import sample.SettingsService.Settings;

import java.awt.color.ProfileDataException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    public boolean openBase() throws OpenBaseException {
        this.profiles = repository.findAll();
        try {
            for (Profile p : this.profiles) {
                p.decrypt(cryptoProvider);
            }
        } catch (ProfileDataException ex){
            throw new OpenBaseException();
        }
        if(this.profiles == null)
            return false;
        return true;
    }
    public boolean saveBase(){
        return repository.save();
    }
    public boolean createBase(){
        this.profiles = new ArrayList<>();
        Object pathdb = this.settings.get("pathDB");
        this.repository.getSettings().set("path", pathdb);
        this.repository.init();
        return true;
    }
    public void addProfile(String name, String password){
        Profile profile = new Profile(name, password);
        this.profiles.add(new Profile(profile));
        profile.encrypt(cryptoProvider);
        this.repository.add(profile);
    }
    public void removeProfile(Profile profile){
        Profile searchable = new Profile(profile);
        searchable.encrypt(cryptoProvider);
        this.repository.remove(searchable);
    }
    public Profile changeProfile(Profile profile, String name, String password){
        this.removeProfile(profile);
        Profile newProfile = new Profile(profile);
        newProfile.setPassword(password);
        newProfile.setName(name);
        profile.setName(name);
        profile.setPassword(password);
        newProfile.encrypt(cryptoProvider);
        this.repository.add(newProfile);
        return profile;
    }
    public List<Profile> find(Profile p){
        return this.repository.find(this.storageFactory.createQuery(p));
    }
    public List<Profile> find(Query q){
        List<Profile> profiles = this.repository.find(q);
        for(Profile profile : profiles){
            profile.decrypt(this.cryptoProvider);
        }
        return profiles;
    }
    public List<Profile> getProfiles(){
        return this.profiles;
    }
    public StorageFactory getStorageFactory(){
        return this.storageFactory;
    }
    public Profile prepareProfileToQuery(Profile profile){
        profile.encrypt(this.cryptoProvider);
        return profile;
    }
}
