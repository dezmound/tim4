package sample.PasswordManager;

import sample.CryptoService.CryptoProvider;
import sample.CryptoService.Encryptable;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Created by dmitry on 16.12.16.
 */
public class Profile implements Encryptable, Serializable {
    private String name;
    private String password;

    public String getName() {
        return name;
    }
    public boolean setName(String value){
        if(value.equals(""))
            return false;
        if(value.length() > 255)
            return false;
        this.name = value;
        return true;
    }
    public String getPassword() {
        return password;
    }
    public boolean setPassword(String value){
        if(value.equals(""))
            return false;
        if(value.length() > 255)
            return false;
        if(value.length() < 6)
            return false;
        this.password = value;
        return true;
    }
    public Profile(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public void encrypt(CryptoProvider cryptoProvider) {
        this.name = new String((new BigInteger(cryptoProvider.encrypt(this.name.getBytes()))).toString(16));
        this.password = new String((new BigInteger(cryptoProvider.encrypt(this.password.getBytes()))).toString(16));
    }

    @Override
    public void decrypt(CryptoProvider cryptoProvider) {
        this.name = new String(cryptoProvider.decrypt((new BigInteger(this.name, 16)).toByteArray()));
        this.password = new String(cryptoProvider.decrypt((new BigInteger(this.password, 16)).toByteArray()));
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Profile){
            return ((Profile)o).getName().equals(this.name);
        }else {
            return super.equals(o);
        }
    }
}
