package sample.PasswordManager;

import org.junit.Test;
import sample.SettingsService.Settings;

import static org.junit.Assert.*;

/**
 * Created by dmitry on 28.12.16.
 */
public class FileProfileRepositoryTest {
    static FileProfileRepository fileProfileRepository = new FileProfileRepository();
    @Test
    public void getSettings() throws Exception {
        assertNotNull(fileProfileRepository.getSettings());
    }

    @Test
    public void find() throws Exception {
        assertNotNull(fileProfileRepository.find(new TestQuery()));
    }

    @Test
    public void findAll() throws Exception {
        assertNull(fileProfileRepository.findAll());
    }

    @Test
    public void add() throws Exception {
        assertTrue(fileProfileRepository.add(new Profile("", "")));
    }

    @Test
    public void remove() throws Exception {
        assertFalse(fileProfileRepository.remove(new Profile("", "")));
    }

    @Test
    public void save() throws Exception {
        assertFalse(fileProfileRepository.save());
    }

    @Test
    public void init() throws Exception {
        assertTrue(fileProfileRepository.init());
    }

}