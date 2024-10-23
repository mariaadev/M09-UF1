package iticbcn.xifratge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class XifradorPolialfabetic {
    public static final char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    private ArrayList<Character> alfabet = new ArrayList<Character>();
    private long clauSecreta = 4538291;
    private Random rand;
    private int numRand; 
    
    public void main(String[] args) {
        String msgs[] = {"Test 01 àrbritre, coixí, Perímetre",
        "Test 02 Taüll, DÍA, año",
        "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];
        inicialitzarLlista();
        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }
        
        System.out.println("Desxifratge:\n-----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
            }
        }
        

    private void initRandom(long clauSecreta2) {
        //inicialitzar el generador de números random amb la clau secreta (seed)
        rand = new Random(clauSecreta2);
    }
    private void inicialitzarLlista() {
        for(char c : ABCMIN) {
            alfabet.add(c);
        }
    }

    private String processaPoliAlfa(String cadena, boolean dreta) {
        StringBuilder cadenaProcessada = new StringBuilder();
        for (int i = 0; i < cadena.length(); i++) {
            char lletra = cadena.charAt(i);
            /*Inicialitzar abcPermutat per cada lletra*/
            ArrayList<Character> abcPermutat = new ArrayList<Character>(alfabet);
            permutaAlfabet(abcPermutat);
            int index = dreta ? alfabet.indexOf(lletra) :  abcPermutat.indexOf(lletra);

            if (index == -1) {
         
                lletra = Character.toLowerCase(lletra);
                
                
                index = dreta ? alfabet.indexOf(lletra) :  abcPermutat.indexOf(lletra);
                
                if (index == -1) {
                    //simbol
                    cadenaProcessada.append(cadena.charAt(i));
                    continue;
                } 
                char caracterPermutat = Character.toUpperCase((Character)( dreta ? 
                                                                            abcPermutat.get(index) : 
                                                                            alfabet.get(index)));
                
                
                cadenaProcessada.append( caracterPermutat);
            
            } else {
                char caracterPermutat = dreta ? abcPermutat.get(index) : alfabet.get(index);
                cadenaProcessada.append(caracterPermutat);
            }

            
        }

        return cadenaProcessada.toString();
    }
    private void permutaAlfabet(ArrayList<Character> alfabet) {
        /*barrejar lletres amb aleatorietat*/
        Collections.shuffle(alfabet, rand);
    }
    private String xifraPoliAlfa( String msg ) {
        return processaPoliAlfa(msg, true);
    }
    private String desxifraPoliAlfa( String msgXifrat ) {
        return processaPoliAlfa(msgXifrat, false);
    }

}
