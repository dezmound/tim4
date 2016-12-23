package sample.PasswordManager;

import sample.Repository.Query;
import sample.Repository.Repository;
import sample.SettingsService.Settings;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dmitry on 16.12.16.
 */
public class FileProfileRepository implements SettingableRepository {
    Settings settings;
    List<Profile> list;
    public FileProfileRepository(){
        this.settings = new Settings(new HashMap<>());
        this.list = new ArrayList<>();
    }
    public FileProfileRepository(Settings settings){
        this.settings = settings;
        this.list = new ArrayList<>();
    }
    @Override
    public Settings getSettings(){
        return this.settings;
    }
    @Override
    public List<Profile> find(Query query) {
        return query.execute(list).fetch();
    }

    @Override
    public List<Profile> findAll() {
        if(!this.list.isEmpty())
            return this.list;
        if(this.settings.get("path") == null)
            return null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream((String)this.settings.get("path")));
            this.list = (List<Profile>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.list;
    }

    @Override
    public boolean add(Object entity) {
        return this.list.add((Profile) entity);
    }

    @Override
    public boolean remove(Object entity) {
        return this.list.remove(entity);
    }

    @Override
    public boolean save() {
        if(this.settings.get("path") == null)
            return false;
        if(this.list.isEmpty())
            return false;
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream((String)this.settings.get("path")));
            objectOutputStream.writeObject(this.list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean init() {
        if(this.settings == null)
            return false;
        list = new ArrayList<>();
        return true;
    }
}
