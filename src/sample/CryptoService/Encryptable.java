package sample.CryptoService;

/**
 * Created by dmitry on 16.12.16.
 */
public interface Encryptable {
    void encrypt(CryptoProvider cryptoProvider);
    void decrypt(CryptoProvider cryptoProvider);
}
