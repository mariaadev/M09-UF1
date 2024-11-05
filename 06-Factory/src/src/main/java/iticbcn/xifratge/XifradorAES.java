package iticbcn.xifratge;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class XifradorAES implements Xifrador {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    public static final int MIDA_IV = 16;
    public static final String CLAU = "LaClauSecretaQueVulguis";
    
    public  void main(String[] args) {
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
    public byte[] xifraAES(String msg, String clau) throws Exception {
        
        //Obtenir els bytes de l’String
        byte[] msgByte =  msg.getBytes();
        // Genera IvParameterSpec
        byte[] iv = generaIV();
        //Generador números random de forma segura
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        //Inicialitzador de vector a partir dels bytes especificats
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        
        // Genera hash
        SecretKeySpec secretKeySpec = generaSecretKey(clau);
        
        // Xifrador al qual li passem el format de encriptació
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        //inicialitzar xifrador a mode encriptar
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        //encriptar missatge
        byte[] encrypted = cipher.doFinal(msgByte);

        // Combinar IV i part xifrada.
        byte[] encryptedIVAndText = new byte[MIDA_IV + encrypted.length];
        //copiar iv a l'array encryptedIVandText
        System.arraycopy(iv, 0, encryptedIVAndText, 0, MIDA_IV);
        // copia el text xifrat (sense l'iv) a l'array encryptedIVandText
        System.arraycopy(encrypted, 0, encryptedIVAndText, MIDA_IV, encrypted.length);
        // return iv+msgxifrat
        return encryptedIVAndText;
    }

    private byte[] generaIV() {
        byte[] iv = new byte[MIDA_IV];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    private SecretKeySpec generaSecretKey (String CLAU) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyHash = digest.digest(CLAU.getBytes( StandardCharsets.UTF_8));
        //inicialitzar keybytes amb 16 btytes si és xifrat 
        byte[] keyBytes = new byte[16];
         //copiar el resultat del hash a l'array de keyBytes que s'utilitza com a clau per AES
        System.arraycopy(keyHash,0,keyBytes,0,keyBytes.length);
        //Construeix una clau secreta a partir de l'array de bytes.
        return new SecretKeySpec(keyBytes, ALGORISME_XIFRAT);
    }

    private String desxifraAES (byte[] bIvIMsgXifrat , String clau) throws Exception {
        // Extreure l'IV.
        byte[] iv = new byte[MIDA_IV];
        //extreure l'iv del missatge xifrat copiant-lo a l'array iv
        System.arraycopy(bIvIMsgXifrat, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extreure la part xifrada.
        int encryptedSize = bIvIMsgXifrat.length - MIDA_IV;
        byte[] encryptedBytes = new byte[encryptedSize];
       
        System.arraycopy(bIvIMsgXifrat, MIDA_IV, encryptedBytes, 0, encryptedSize);
        
        // Fer hash de la clau
        SecretKeySpec secretKeySpec = generaSecretKey(clau);    
        // Desxifrar.
        Cipher cipherDecrypt = Cipher.getInstance(FORMAT_AES);
        //Inicialitzar xifrador en mode desxifrar
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

        // convertir bytes desxifrats a String
        return new String(decrypted, StandardCharsets.UTF_8);

    }
    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
       return xifraAES(msg, clau);
    }
    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        return desxifraAES(xifrat, clau);
    }
    /*TODO: Si dona un error de xifrat ha de mostar error i sortir del programa. */
}