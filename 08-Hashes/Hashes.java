import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.HexFormat;
import java.util.concurrent.TimeUnit;

public class Hashes {

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "!";  // Password to be cracked
        Hashes h = new Hashes();
        
        // Generar els hashes de les contrasenyes
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt), h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};
        
        // Provar els dos algoritmes de hash
        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
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

    // Variable de classe per comptar el nombre de contrasenyes provades en l'atac de força bruta
    public int npass = 0;

    // Mètode per obtenir el hash SHA-512 amb salt
    public String getSHA512AmbSalt(String pw, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());  // Afegir el "salt"
        byte[] hash = md.digest(pw.getBytes());
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash);
    }

    // Mètode per obtenir el hash PBKDF2 amb salt
    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        char[] pwChars = pw.toCharArray();
        byte[] saltBytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(pwChars, saltBytes, 1000, 128);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash);
    }

    // Mètode per realitzar un atac de força bruta
    public String forcaBruta(String alg, String hash, String salt) {
        String charset = "abcdefABCDEF1234567890!"; // Caracteres possibles per a la contrasenya
        int maxLength = 6; // Longitud màxima de la contrasenya
        char[] password = new char[maxLength]; // Array per emmagatzemar les contrasenyes generades

        // Començar l'atac de força bruta per longituds de contrasenya creixents
        for (int length = 1; length <= maxLength; length++) {
            String result = generateCombinations(charset, password, 0, length, alg, hash, salt);
            if (result != null) {
                return result; // Si es troba una coincidència, la retornem
            }
        }
        return null; // Si no es troba cap coincidència
    }

    // Mètode recursiu per generar totes les combinacions de contrasenyes fins a la longitud indicada
    private String generateCombinations(String charset, char[] password, int pos, int length, String alg, String hash, String salt) {
        if (pos == length) {
            // Provem la contrasenya generada i comprovem si coincideix amb el hash
            String candidate = new String(password);
            npass++; // Incrementar el nombre d'intents
            try {
                String generatedHash = null;
                if (alg.equals("SHA-512")) {
                    generatedHash = getSHA512AmbSalt(candidate, salt);
                } else if (alg.equals("PBKDF2")) {
                    generatedHash = getPBKDF2AmbSalt(candidate, salt);
                }
                if (generatedHash.equals(hash)) {
                    return candidate; // Retornar la contrasenya si el hash coincideix
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            // Iterem sobre tots els caràcters possibles en la posició actual
            for (int i = 0; i < charset.length(); i++) {
                password[pos] = charset.charAt(i);
                String result = generateCombinations(charset, password, pos + 1, length, alg, hash, salt);
                if (result != null) {
                    return result; // Si es troba una coincidència, la retornem
                }
            }
        }
        return null; // Si no es troba cap coincidència
    }

    // Mètode per calcular l'interval de temps utilitzant TimeUnit
    public String getInterval(long t1, long t2) {
        long diff = t2 - t1;
        
        // Convertim la diferència de temps en dies, hores, minuts, segons i mil·lisegons
        long days = TimeUnit.MILLISECONDS.toDays(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.DAYS.toMinutes(days) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.DAYS.toSeconds(days) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes);
        long millis = diff - TimeUnit.SECONDS.toMillis(seconds);

        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis", days, hours, minutes, seconds, millis);
    }
}
