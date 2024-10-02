import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Monoalfabetic {
    public static final char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public static ArrayList abc = new ArrayList<Character>();
    public static ArrayList abcPermutat = new ArrayList<Character>();
    public static void main(String[] args) {
        inicialitzarLlista();
        permutaAlfabet(abcPermutat);
    }

    public static void inicialitzarLlista() {
            for(char c : ABCMIN) {
                abc.add(c);
                abcPermutat.add(c);
            }
     }
 
    public static void permutaAlfabet(ArrayList alfabet) {
       Collections.shuffle(alfabet);

    }
    
    public static String xifraMonoAlfa(String cadena) {
        StringBuilder cadenaxifrada = new StringBuilder();
        for (int i = 0; i < cadena.length(); i++) {
            
            //identifica l'index de la lletra i mirar si es min o maj
            //reemplazar por el indice encontrado de la letra pero del array permutado
       }
       return cadenaxifrada.toString();
    }

  
        
    public static String desxifraMonoAlfa(String cadena) {
        return "";
    }

}
