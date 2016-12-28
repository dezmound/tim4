package sample.PasswordManager;

import sample.CryptoService.CryptoProvider;

/**
 * Created by dmitry on 28.12.16.
 */
public class TestCryptoProvider implements CryptoProvider {
    @Override
    public void setSettings(Object settings) {
        return;
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return new byte[0];
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return new byte[0];
    }
}
