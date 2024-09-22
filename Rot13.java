/*
 * xifraRot13( cadena ): Ha de substituir cada lletra (no els espais ni els signes de puntuació) per la lletra
que està 13 posicions més a la dreta en l’abecedari (si sobrepassa ha de tornar a començar).
desxifraRot13( cadena ): Ha de fer el procés invers de la funció anterior.
Crea també un main per fer algunes proves.
Recomanacions
Crea un 2 array a nivell de classe
 Un amb les lletres de l’abecedari minúscules en ordre i
 un altre amb les lletres en majúscula en ordre alfabètic
Feu-ho per simplificar el procés de desplaçament d’una lletra per un altra.
 */

public class Rot13 {

    public static char[] abcMin = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','x','y','z'};
    
    public static char[] abcMaj = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','X','Y','Z'};


    public static void main(String[] args) {
        System.out.println(xifraRot13("MaRi.A "));
        System.out.println(desxifraRot13("AnFv.N"));
    }

    private static String  xifraRot13(String cadena) {
        //ex: MaRi.A
        //+13 en la posicio
        String cadenaXifrada = "";
        int index = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if(Character.isWhitespace(cadena.charAt(i)) || !Character.isLetter(cadena.charAt(i))) {
                cadenaXifrada = cadenaXifrada + cadena.charAt(i);
                continue;
            }
            if (Character.isLowerCase(cadena.charAt(i))) {
                //caracter es en minuscula, substituim per lletra minuscula
                for (int j = 0; j < abcMin.length; j++) {
                    if (cadena.charAt(i) == abcMin[j]) {
                        index = j;
                        break;
                    }
                }
                if ( (index + 13) >= 25) {
                    //resta 26 i comença per l'inici
                    cadenaXifrada = cadenaXifrada + abcMin[(index + 13) - 25];
                } else {
                    cadenaXifrada = cadenaXifrada + abcMin[ index + 13];
                }

                
            } else {
                //substituim per lletra majúscula
                //identificar la lletra a xifrar i substituirla per la mateixa lletra + 13,
                // si s'arriba al final de l'array es torna al principi
                    for (int j = 0; j < abcMaj.length; j++) {
                        if (cadena.charAt(i) == abcMaj[j]) {
                            index = j;
                            break;
                        }
                    }
                    if ( (index + 13) >= 25) {
                        //resta 26 i comença per l'inici
                        cadenaXifrada = cadenaXifrada + abcMaj[(index + 13) - 25];
                    } else {
                        cadenaXifrada = cadenaXifrada + abcMaj[ index + 13];
                    }
    
                   
                 
             }
        }
        return cadenaXifrada;
    }

    private static String desxifraRot13(String cadena) {
        String cadenaDesxifrada = "";
        int index = 0;
        //AnFvVN
        for (int i = 0; i < cadena.length(); i++) {
            if(Character.isWhitespace(cadena.charAt(i)) || !Character.isLetter(cadena.charAt(i))) {
                cadenaDesxifrada = cadenaDesxifrada + cadena.charAt(i);
                continue;
            }
            if (Character.isLowerCase(cadena.charAt(i))) {
                //caracter es en minuscula, substituim per lletra minuscula
                for (int j = 0; j < abcMin.length; j++) {
                    if (cadena.charAt(i) == abcMin[j]) {
                        index = j;
                        break;
                    }
                }
                int diff = index - 13; 
                if ( diff < 0) {
                    //resta 26 i comença per l'inici
                    // -5
                    //començar pel final
                    cadenaDesxifrada = cadenaDesxifrada + abcMin[ Math.abs(abcMin.length - (Math.abs(diff)))];
                } else {
                    cadenaDesxifrada = cadenaDesxifrada + abcMin[Math.abs(diff)];
                }

                
            } else {
                //substituim per lletra majúscula
                //identificar la lletra a xifrar i substituirla per la mateixa lletra + 13,
                // si s'arriba al final de l'array es torna al principi
                    for (int j = 0; j < abcMaj.length; j++) {
                        if (cadena.charAt(i) == abcMaj[j]) {
                            index = j;
                            break;
                        }
                    }
                    int diff = index - 13; 
                    if ( diff < 0) {
                        //resta 26 i comença per l'inici
                        cadenaDesxifrada = cadenaDesxifrada + abcMaj[ Math.abs(abcMaj.length - (Math.abs(diff)))];
                    } else {
                        cadenaDesxifrada = cadenaDesxifrada + abcMaj[Math.abs(diff)];
                    }
    
                   
                 
             }
        }
        return cadenaDesxifrada;
    }
}