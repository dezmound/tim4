package sample.SettingsService;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

/**
 * Created by dmitry on 16.12.16.
 */
public class Settings {
    private Map<String, Object> settings;
    public Settings(Map<String, Object> settings){
        this.settings = settings;
    }
    public Object get(String property){
        return this.settings.get(property);
    }
    public void set(String key, Object value){
        this.settings.put(key, value);
    }
    public void save(String path){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Settings load(String path){
        Settings settings = null;
        if(!Files.exists((new File(path)).toPath()))
            return settings;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path));
            settings = (Settings) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return settings;
    }
}
