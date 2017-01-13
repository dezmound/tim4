package sample.PasswordManager;

import org.junit.Test;
import sample.Repository.*;
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
        Repository repository = fileStorageFactory.createRepository();
        assertNotNull(repository);
        assertTrue(repository instanceof FileProfileRepository);
    }

    @Test
    public void createQuery() throws Exception {
        Query query = fileStorageFactory.createQuery(new Profile("test", "test"));
        assertNotNull(query);
        assertTrue(query instanceof FileProfileQuery);
    }

    @Test
    public void createQueryOr() throws Exception {
        QueryAnd queryAnd = (QueryAnd) fileStorageFactory.createQueryAnd(fileStorageFactory.createQuery(new Profile("test", "test")), fileStorageFactory.createQuery(new Profile("test", "test")));
        assertNotNull(queryAnd);
        assertTrue(queryAnd instanceof ListedQueryAnd);
    }

    @Test
    public void createQueryAnd() throws Exception {
        QueryOr queryOr = (QueryOr) fileStorageFactory.createQueryOr(fileStorageFactory.createQuery(new Profile("test", "test")), fileStorageFactory.createQuery(new Profile("test", "test")));
        assertNotNull(queryOr);
        assertTrue(queryOr instanceof ListedQueryOr);
    }

}