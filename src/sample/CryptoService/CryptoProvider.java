package sample.CryptoService;

/**
 * Created by dmitry on 16.12.16.
 */
public interface CryptoProvider {
    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);
}
