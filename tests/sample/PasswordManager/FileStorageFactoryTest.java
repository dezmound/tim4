package sample.PasswordManager;

import org.junit.Test;
import sample.SettingsService.Settings;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by dmitry on 28.12.16.
 */
public class FileStorageFactoryTest {
    static FileStorageFactory fileStorageFactory = new FileStorageFactory(new Settings(new HashMap<>()));
    @Test
    public void getSettings() throws Exception {
        assertNotNull(fileStorageFactory.getSettings());
    }

    @Test
    public void createRepository() throws Exception {
        assertNotNull(fileStorageFactory.createRepository());
    }

    @Test
    public void createQuery() throws Exception {
        assertNotNull(fileStorageFactory.createQuery(new Profile("test", "test")));
    }

    @Test
    public void createQueryOr() throws Exception {
        assertNotNull(fileStorageFactory.createQueryAnd(fileStorageFactory.createQuery(new Profile("test", "test")), fileStorageFactory.createQuery(new Profile("test", "test"))));
    }

    @Test
    public void createQueryAnd() throws Exception {
        assertNotNull(fileStorageFactory.createQueryOr(fileStorageFactory.createQuery(new Profile("test", "test")), fileStorageFactory.createQuery(new Profile("test", "test"))));
    }

}