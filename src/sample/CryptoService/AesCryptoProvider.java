package sample.CryptoService;

import sample.SettingsService.Settings;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dmitry on 16.12.16.
 */
public class AesCryptoProvider implements CryptoProvider {
    transient Settings settings;
    public AesCryptoProvider(Settings settings){
        this.settings = settings;
    }
    private byte[] cipherIt(byte[] data, int encryptMode){
        if(data.length == 0)
            return data;
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec((byte[])settings.get("iv"));
            SecretKeySpec secretKeySpec = new SecretKeySpec((byte[])settings.get("key"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(encryptMode, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(data);
            return encrypted;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void setSettings(Object settings) {
        this.settings = (Settings) settings;
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return cipherIt(data, Cipher.ENCRYPT_MODE);
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return cipherIt(data, Cipher.DECRYPT_MODE);
    }
}
