package sample.PasswordManager;

import org.junit.BeforeClass;
import org.junit.Test;
import sample.SettingsService.Settings;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by dmitry on 28.12.16.
 */
public class PasswordManagerTest {
    static PasswordManager passwordManager = new PasswordManager(new Settings(new HashMap<>()), new TestCryptoProvider(), new TestStorageFactory());
    @Test
    public void openBase() throws Exception {
        assertTrue("Can't open repo", passwordManager.openBase());
    }

    @Test
    public void saveBase() throws Exception {
        assertTrue("Can't open repo", passwordManager.saveBase());
    }

    @Test
    public void createBase() throws Exception {
        assertTrue("Can't open repo",passwordManager.createBase());
    }

    @Test
    public void addProfile() throws Exception {
        passwordManager.addProfile("test", "test");
    }

    @Test
    public void removeProfile() throws Exception {
        passwordManager.removeProfile(new Profile("test", "test"));
    }

    @Test
    public void changeProfile() throws Exception {
        assertTrue(passwordManager.openBase());
        assertEquals(passwordManager.changeProfile(passwordManager.getProfiles().get(0), "test", "test"), new Profile("test", "test"));
    }

    @Test
    public void find() throws Exception {
        assertFalse(passwordManager.find(new TestQuery()).isEmpty());
    }

    @Test
    public void find1() throws Exception {

    }

    @Test
    public void getProfiles() throws Exception {
        assertNotNull(passwordManager.getProfiles());
    }

    @Test
    public void getStorageFactory() throws Exception {
        assertNotNull(passwordManager.getStorageFactory());
    }

    @Test
    public void prepareProfileToQuery() throws Exception {
        assertNotNull(passwordManager.prepareProfileToQuery(new Profile("test", "test")));
    }

    @Test
    public void openChangeSave() throws Exception {
        passwordManager.openBase();
        assertEquals(passwordManager.changeProfile(passwordManager.getProfiles().get(0), "test", "test"), new Profile("test", "test"));
        assertTrue(passwordManager.saveBase());
    }
}