import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.HexFormat;
import java.util.concurrent.TimeUnit;

public class Hashes {

    int npass = 0;
    public static void main(String[] args) throws Exception {
         //Valors inicials: sal (salt) i contrasenya (pw) a provar
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";

        Hashes h = new Hashes();
        //Generació dels hashes amb SHA-512 i PBKDF2
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
                            h.getPBKDF2AmbSalt(pw, salt) 
                            };
         //Variables per guardar la contrasenya trobada i els algorismes a utilitzar
        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};
        
        for(int i=0; i< aHashes.length; i++){
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n",aHashes[i]);
            System.out.printf("---------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");
            
            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();
            
            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---------------------------\n\n");
        }
    }

   

    public String getSHA512AmbSalt(String pw, String salt) {
        try {
             //Inicialització de l'algoritme SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            //Afegir la sal als bytes de l'entrada
            md.update(salt.getBytes());
            byte[] bytes = md.digest(pw.getBytes());
            //Convertir el resultat a hexadecimal per facilitar-ne la lectura
            HexFormat hex = HexFormat.of();
            return hex.formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPBKDF2AmbSalt(String pw, String salt) {
        try {
            //Nombre d'iteracions del procés PBKDF2
            int iterations = 1000; 
            //Convertir la contrasenya a caràcters
            char[] chars = pw.toCharArray();
            byte[] saltBytes = salt.getBytes();

            //Definir l'especificació de clau (clau, sal, iteracions, longitud)
            PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, iterations, 64 * 8);
            
            //Inicialitzar l'algorisme PBKDF2
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            
            //Generar el hash amb l'algorisme i retornem el resultat en hexadecimal
            byte[] hash = skf.generateSecret(spec).getEncoded();
            HexFormat hex = HexFormat.of();
            return iterations + ":" + hex.formatHex(saltBytes) + ":" + hex.formatHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String forcaBruta(String alg, String hash, String salt) {
        npass = 0; /*contador intents */
        char[] charset = "abcdefABCDEF1234567890!".toCharArray(); 
        char[] guess = new char[6]; 
    
        /* Provar contrasenyes del  1 a 6*/
        for (int len = 1; len <= 6; len++) {
            for (int i1 = 0; i1 < charset.length; i1++) {
                guess[0] = charset[i1];
                if (len == 1 && testPw(alg, hash, salt, guess, len)) return new String(guess, 0, len);
    
                for (int i2 = 0; i2 < charset.length; i2++) {
                    guess[1] = charset[i2];
                    if (len == 2 && testPw(alg, hash, salt, guess, len)) return new String(guess, 0, len);
    
                    for (int i3 = 0; i3 < charset.length; i3++) {
                        guess[2] = charset[i3];
                        if (len == 3 && testPw(alg, hash, salt, guess, len)) return new String(guess, 0, len);
    
                        for (int i4 = 0; i4 < charset.length; i4++) {
                            guess[3] = charset[i4];
                            if (len == 4 && testPw(alg, hash, salt, guess, len)) return new String(guess, 0, len);
    
                            for (int i5 = 0; i5 < charset.length; i5++) {
                                guess[4] = charset[i5];
                                if (len == 5 && testPw(alg, hash, salt, guess, len)) return new String(guess, 0, len);
    
                                for (int i6 = 0; i6 < charset.length; i6++) {
                                    guess[5] = charset[i6];
                                    if (len == 6 && testPw(alg, hash, salt, guess, len)) return new String(guess, 0, len);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null; // Si no se encuentra ninguna coincidencia
    }
    
    private boolean testPw(String alg, String hash, String salt, char[] guess, int len) {
        //Contrasenya amb la longitud correcta
        String guessStr = new String(guess, 0, len); 
    
        String hashedGuess;
        if (alg.equals("SHA-512")) {
            hashedGuess = getSHA512AmbSalt(guessStr, salt); 
        } else {
            hashedGuess = getPBKDF2AmbSalt(guessStr, salt); 
        }
    
        npass++;
        //Comparar el hash generat amb el hash objectiu
        return hashedGuess.equals(hash); 
    }
    

    public String getInterval(long t1, long t2) {
        long millis = t2 - t1;
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.SECONDS.toMillis(seconds);
        
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis", 
                             days, hours, minutes, seconds, millis);
    }
}
