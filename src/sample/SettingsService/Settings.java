package sample.SettingsService;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

/**
 * Created by dmitry on 16.12.16.
 */
public class Settings implements Serializable {
    protected Map<String, Object> settings;
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
    public static Settings load(String path) throws IOException{
        Settings settings = null;
        if(!Files.exists((new File(path)).toPath()))
            throw new FileNotFoundException();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path));
            settings = (Settings) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return settings;
    }
}
