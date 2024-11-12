package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class ClauPublica {
    private final String ALGORISME = "RSA";
    
    /*Generar un parell de claus RSA*/
    public KeyPair generaParellClausRSA() throws Exception {
        KeyPairGenerator generador = KeyPairGenerator.getInstance(ALGORISME);
        generador.initialize(2048);  /*Clau inicialitzada a 2048 bits*/
        return generador.generateKeyPair();
    }
    
    /*Xifra un missatge amb la clau pública*/
    public byte[] xifraRSA(String msg, PublicKey clauPublica) throws Exception {
        Cipher xifrat = Cipher.getInstance(ALGORISME);
        xifrat.init(Cipher.ENCRYPT_MODE, clauPublica);
        return xifrat.doFinal(msg.getBytes(StandardCharsets.UTF_8));
    }
    
    /*Desxifra un missatge amb la clau privada*/
    public String desxifraRSA(byte[] msgXifrat, PrivateKey clauPrivada)throws Exception {
        Cipher desxifrat = Cipher.getInstance(ALGORISME);
        desxifrat.init(Cipher.DECRYPT_MODE, clauPrivada);
        byte[] desxifratBytes = desxifrat.doFinal(msgXifrat);
        return new String(desxifratBytes, StandardCharsets.UTF_8);
    }
}