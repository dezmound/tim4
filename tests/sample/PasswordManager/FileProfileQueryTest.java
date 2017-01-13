package sample.PasswordManager;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by dmitry on 28.12.16.
 */
public class FileProfileQueryTest {
    FileProfileQuery fileProfileQuery = new FileProfileQuery(new Profile("test", "test"));
    @Test
    public void execute() throws Exception {
        assertNotNull(fileProfileQuery.execute(Arrays.asList(new Profile("test", "test"), new Profile("test2", "test2"))));
    }

    @Test
    public void fetch() throws Exception {
        assertNotNull(fileProfileQuery.execute(Arrays.asList(new Profile("test", "test"), new Profile("test2", "test2"))));
        assertFalse(fileProfileQuery.fetch().isEmpty());
    }

}