package iticbcn.xifratge;
import java.util.ArrayList;
import java.util.Collections;

public class XifradorMonoalfabetic {
    public static final char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    private ArrayList<Character> abc = new ArrayList<Character>();
    private ArrayList<Character> abcPermutat = new ArrayList<Character>();
    public static final String[] EXEMPLES = {"Adrià", "Silvia", "Avinguda Amèrica", "Sebastián?", "Tinc gana."};
    
    public void main(String[] args) {
        String cadenaxifrada = "";
        String cadenadesxifrada = "";
        inicialitzarLlista();
        permutaAlfabet(abcPermutat);
        mostraExemples(cadenaxifrada,cadenadesxifrada,EXEMPLES);
    }

    private void mostraExemples(String cadenaxifrada, String cadenadesxifrada, String[] EXEMPLES) {
        System.out.println("Alfabet :" + abc);
        System.out.println("Alfabet Permutat : " + abcPermutat);
        System.out.println("-------------------");
        for (int i = 0; i < EXEMPLES.length; i++) {
            cadenaxifrada = xifraMonoAlfa(EXEMPLES[i]);
            System.out.println(cadenaxifrada);
            cadenadesxifrada = desxifraMonoAlfa(cadenaxifrada);
            System.out.println(cadenadesxifrada);
            System.out.println("-------------------");
        }
    }

    private void inicialitzarLlista() {
            for(char c : ABCMIN) {
                abc.add(c);
                abcPermutat.add(c);
            }
     }
 
    private void permutaAlfabet(ArrayList<Character> alfabet) {
       Collections.shuffle(alfabet);
    }
    
    private String processaMonoalfa(String cadena, boolean dreta) {
        StringBuilder cadenaProcessada = new StringBuilder();
            
        for (int i = 0; i < cadena.length(); i++) {
                char lletra = cadena.charAt(i);
                //index lletra cadena
                int index = dreta ? abc.indexOf(lletra) :  abcPermutat.indexOf(lletra);

                if (index == -1) {
                    //majuscula
                    lletra = Character.toLowerCase(lletra);
                    //si es xifra busquem a l'abc original , si es desxifra busquem a l' abcPermutat
                    index = dreta ? abc.indexOf(lletra) :  abcPermutat.indexOf(lletra);

                    if (index == -1) {
                        //simbol
                        cadenaProcessada.append(cadena.charAt(i));
                        continue;
                    } 
                    char caracterPermutat = Character.toUpperCase((Character)( dreta ? 
                                                                                abcPermutat.get(index) : 
                                                                                abc.get(index)));
                    cadenaProcessada.append( caracterPermutat);
                
                } else {
                    char caracterPermutat = dreta ? abcPermutat.get(index) : abc.get(index);
                    cadenaProcessada.append(caracterPermutat);
                }
                //identifica l'index de la lletra i mirar si es min o maj
                //reemplazar por el indice encontrado de la letra pero del array permutado
            }

        return cadenaProcessada.toString();
        
    }

    private String xifraMonoAlfa(String cadena) {
       return processaMonoalfa(cadena, true);
    }
    
    private String desxifraMonoAlfa(String cadena) {
        return processaMonoalfa(cadena, false);
    }

}
