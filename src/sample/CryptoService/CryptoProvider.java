package sample.CryptoService;

import java.io.Serializable;

/**
 * Created by dmitry on 16.12.16.
 */
public interface CryptoProvider extends Serializable {
    void setSettings(Object settings);
    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);
}
