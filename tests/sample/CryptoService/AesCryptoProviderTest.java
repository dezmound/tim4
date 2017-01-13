package sample.CryptoService;

import org.junit.Test;
import sample.SettingsService.Settings;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by dmitry on 28.12.16.
 */
public class AesCryptoProviderTest {
    AesCryptoProvider aesCryptoProvider = new AesCryptoProvider(new TestSettingsAes(new HashMap<>()));
    @Test
    public void setSettings() throws Exception {
        aesCryptoProvider.setSettings(new Settings(new HashMap<>()));
    }

    @Test
    public void encrypt() throws Exception {
        Arrays.equals(aesCryptoProvider.encrypt(new byte[]{0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1}), new byte[]{(byte)0xb6,(byte)0xae,(byte)0xaf,(byte)0xfa,(byte)0x75,(byte)0x2d,(byte)0xc0,(byte)0x8b,(byte)0x51,(byte)0x63,(byte)0x97,(byte)0x31,(byte)0x76,(byte)0x1a,(byte)0xed,(byte)0x00});
    }

    @Test
    public void decrypt() throws Exception {
        Arrays.equals(aesCryptoProvider.decrypt(new byte[]{(byte)0xb6,(byte)0xae,(byte)0xaf,(byte)0xfa,(byte)0x75,(byte)0x2d,(byte)0xc0,(byte)0x8b,(byte)0x51,(byte)0x63,(byte)0x97,(byte)0x31,(byte)0x76,(byte)0x1a,(byte)0xed,(byte)0x00}), new byte[]{0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1,0x1});
    }

}