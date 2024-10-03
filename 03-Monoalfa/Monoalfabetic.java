import java.util.ArrayList;
import java.util.Collections;

public class Monoalfabetic {
    public static final char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public static ArrayList<Character> abc = new ArrayList<Character>();
    public static ArrayList<Character> abcPermutat = new ArrayList<Character>();
    public static void main(String[] args) {
        inicialitzarLlista();
        permutaAlfabet(abcPermutat);
        System.out.println(abc);
        System.out.println(abcPermutat);
        System.out.println(xifraMonoAlfa("hola"));
    }

    public static void inicialitzarLlista() {
            for(char c : ABCMIN) {
                abc.add(c);
                abcPermutat.add(c);
            }
     }
 
    public static void permutaAlfabet(ArrayList<Character> alfabet) {
       Collections.shuffle(alfabet);

    }
    
    public static String xifraMonoAlfa(String cadena) {
        StringBuilder cadenaxifrada = new StringBuilder();
        for (int i = 0; i < cadena.length(); i++) {
            char lletra = cadena.charAt(i);
            //index lletra cadena
            int index = abc.indexOf(lletra);

            if (index == -1) {
                lletra = Character.toLowerCase(lletra);

                index = abc.indexOf(lletra);
                if (index == -1) {
                    //simbol
                    cadenaxifrada.append(cadena.charAt(i));
                    break;
                } 
                
                cadenaxifrada.append( (Character)(abcPermutat.get(index))) ;
              
            } else {
                cadenaxifrada.append(abcPermutat.get(index));
            }
            //identifica l'index de la lletra i mirar si es min o maj
            //reemplazar por el indice encontrado de la letra pero del array permutado
       }
       return cadenaxifrada.toString();
    }

  
        
    public static String desxifraMonoAlfa(String cadena) {
        return "";
    }

}
