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
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
        h.getPBKDF2AmbSalt(pw, salt) };
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
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(pw.getBytes());
            HexFormat hex = HexFormat.of();
            return hex.formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPBKDF2AmbSalt(String pw, String salt) {
        try {
            int iterations = 1000;
            char[] chars = pw.toCharArray();
            byte[] saltBytes = salt.getBytes();

            PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            HexFormat hex = HexFormat.of();
            return iterations + ":" + hex.formatHex(saltBytes) + ":" + hex.formatHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String forcaBruta(String alg, String hash, String salt) {
        npass = 0;
        char[] charset = "abcdefABCDEF1234567890!".toCharArray();
        char[] guess = new char[6];

        for (int i1 = 0; i1 < charset.length; i1++) {
            guess[0] = charset[i1];
            for (int i2 = 0; i2 < charset.length; i2++) {
                guess[1] = charset[i2];
                for (int i3 = 0; i3 < charset.length; i3++) {
                    guess[2] = charset[i3];
                    for (int i4 = 0; i4 < charset.length; i4++) {
                        guess[3] = charset[i4];
                        for (int i5 = 0; i5 < charset.length; i5++) {
                            guess[4] = charset[i5];
                            for (int i6 = 0; i6 < charset.length; i6++) {
                                guess[5] = charset[i6];
                                String guessStr = new String(guess).trim();

                                String hashedGuess;
                                if (alg.equals("SHA-512")) {
                                    hashedGuess = getSHA512AmbSalt(guessStr, salt);
                                } else {
                                    hashedGuess = getPBKDF2AmbSalt(guessStr, salt);
                                }

                                npass++;
                                if (hashedGuess.equals(hash)) {
                                    return guessStr;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
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
