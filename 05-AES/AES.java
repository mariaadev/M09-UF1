import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "LaClauSecretaQueVulguis";
    public static void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet",
        
        "Hola Andrés cómo está tu cuñado",
        "Àgora ïlla Ôtto"};
        for (int i = 0; i < msgs.length; i++) {
        String msg = msgs[i];
        byte[] bXifrats = null;
        String desxifrat = "";
        try {
        bXifrats = xifraAES(msg, CLAU);
        desxifrat = desxifraAES (bXifrats, CLAU);
        
        } catch (Exception e) {
        System.err.println("Error de xifrat: "
        + e.getLocalizedMessage ());
        
        }
        System.out.println("--------------------" );
        System.out.println("Msg: " + msg);
        System.out.println("Enc: " + new String(bXifrats));
        System.out.println("DEC: " + desxifrat);
        }
    }
    public static byte[] xifraAES(String msg, String clau) throws Exception {
        
        //Obtenir els bytes de l’String
        byte[] msgByte =  msg.getBytes();
        // Genera IvParameterSpec
        iv = new byte[MIDA_IV];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        
        // Genera hash
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        digest.update(CLAU.getBytes("UTF-8"));
        byte[] keyBytes = new byte[16];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORISME_XIFRAT);
        
        // Encrypt.
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(msgByte);

        // Combinar IV i part xifrada.
        byte[] encryptedIVAndText = new byte[MIDA_IV + encrypted.length];
        System.arraycopy(iv, 0, encryptedIVAndText, 0, MIDA_IV);
        System.arraycopy(encrypted, 0, encryptedIVAndText, MIDA_IV, encrypted.length);
        // return iv+msgxifrat
        return encryptedIVAndText;
    }

    public static String desxifraAES (byte[] bIvIMsgXifrat , String clau) throws Exception {
        // Extreure l'IV.
        byte[] iv = new byte[MIDA_IV];
        System.arraycopy(bIvIMsgXifrat, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extreure la part xifrada.
        int encryptedSize = bIvIMsgXifrat.length - MIDA_IV;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(bIvIMsgXifrat, MIDA_IV, encryptedBytes, 0, encryptedSize);
        
        // Fer hash de la clau
        byte[] keyBytes = new byte[16];
        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        md.update(clau.getBytes());
        System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORISME_XIFRAT);
        
        // Desxifrar.
        Cipher cipherDecrypt = Cipher.getInstance(FORMAT_AES);
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

        // return String desxifrat
        return new String(decrypted, "UTF-8");

    }

}