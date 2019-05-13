package cn.neday.iflytek.util.crypto;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

public class AES {

    private static final String CIPHER_MODE_PADDING = "AES/CBC/PKCS7Padding";
    private static final int HASH_ITERATIONS = 10000;
    private static final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";
    private static final int KEY_LENGTH = 256;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private byte[] iv = {10, 1, 11, 5, 4, 15, 7, 9, 23, 3, 1, 6, 8, 12, 13, 91};
    private byte[] salt = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private IvParameterSpec IV = new IvParameterSpec(this.iv);

    private byte[] encrypt(String paramString, SecretKey paramSecretKey, IvParameterSpec paramIvParameterSpec, byte[] paramArrayOfByte) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher localCipher = Cipher.getInstance(paramString);
            localCipher.init(1, paramSecretKey, paramIvParameterSpec);
            return localCipher.doFinal(paramArrayOfByte);
        } catch (NoSuchAlgorithmException nsae) {
            logger.error("no cipher getinstance support for " + paramString);
            return null;
        } catch (NoSuchPaddingException nspe) {
            logger.error("no cipher getinstance support for padding " + paramString);
            return null;
        } catch (InvalidKeyException e) {
            logger.error("invalid key exception");
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            logger.error("invalid algorithm parameter exception");
            return null;
        } catch (IllegalBlockSizeException e) {
            logger.error("illegal block size exception");
            return null;
        } catch (BadPaddingException e) {
            logger.error("bad padding exception");
            return null;
        }
    }

    public String encrypt(byte[] paramArrayOfByte, String paramString) {
        return Base64Encoder.encode(encrypt(CIPHER_MODE_PADDING, getKey(paramString), this.IV, paramArrayOfByte));
    }

    private SecretKeySpec getKey(String paramString) {
        char[] arrayOfChar = paramString.toCharArray();
        PBEKeySpec localPBEKeySpec = new PBEKeySpec(arrayOfChar, this.salt, HASH_ITERATIONS, KEY_LENGTH);
        try {
            Object localObject = SecretKeyFactory.getInstance(KEY_GENERATION_ALG).generateSecret(localPBEKeySpec);
            return new SecretKeySpec(((SecretKey) localObject).getEncoded(), "AES");
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}