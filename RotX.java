public class RotX {

    public static char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public static char[] ABCMAJ = "AÁÀÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();

    public static void main(String[] args) {
        System.out.println(xifraRotX("MaRi. A ", 13));
        System.out.println(desxifraRotX("ÚgÁö. G ", 13));
        forcaBrutaRotX("ÚgÁö. G ");
    }


    private static String  xifraRotX(String cadena, int desplacament) {
        StringBuilder cadenaXifrada = new StringBuilder();
        int index = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if(!Character.isLetter(cadena.charAt(i))) {
                cadenaXifrada.append(cadena.charAt(i)) ;
                continue;
            }
            if (Character.isLowerCase(cadena.charAt(i))) {
                for (int j = 0; j < ABCMIN.length; j++) {
                    if (cadena.charAt(i) == ABCMIN[j]) {
                        index = j;
                        break;
                    }
                }
                if ( (index + desplacament) >= ABCMIN.length) {
                    cadenaXifrada.append(ABCMIN[(index + desplacament) - ABCMIN.length]);
                } else {
                    cadenaXifrada.append(ABCMIN[ index + desplacament]) ;
                }

                
            } else {
                    for (int j = 0; j < ABCMAJ.length; j++) {
                        if (cadena.charAt(i) == ABCMAJ[j]) {
                            index = j;
                            break;
                        }
                    }
                    if ( (index + desplacament) >= ABCMAJ.length) {
                        cadenaXifrada.append(ABCMAJ[(index + desplacament) - ABCMAJ.length]);
                    } else {
                        cadenaXifrada.append(ABCMAJ[ index + desplacament]);
                    }
    
                   
                 
             }
        }
        return cadenaXifrada.toString();
    }
    private static String desxifraRotX(String cadena, int desplacament) {
        StringBuilder cadenaDesxifrada = new StringBuilder();
        int index = 0;

        for (int i = 0; i < cadena.length(); i++) {
            if(!Character.isLetter(cadena.charAt(i))) {
                cadenaDesxifrada.append(cadena.charAt(i)) ;
                continue;
            }
            if (Character.isLowerCase(cadena.charAt(i))) {
                for (int j = 0; j < ABCMIN.length; j++) {
                    if (cadena.charAt(i) == ABCMIN[j]) {
                        index = j;
                        break;
                    }
                }
                int diff = index - desplacament; 
                if ( diff < 0) {
                    cadenaDesxifrada.append(ABCMIN[ Math.abs(ABCMIN.length - (Math.abs(diff)))]);
                } else {
                    cadenaDesxifrada.append(ABCMIN[Math.abs(diff)]);
                }
   
            } else {
                    for (int j = 0; j < ABCMAJ.length; j++) {
                        if (cadena.charAt(i) == ABCMAJ[j]) {
                            index = j;
                            break;
                        }
                    }
                    int diff = index - desplacament; 
                    if ( diff < 0) {
                        cadenaDesxifrada.append(ABCMAJ[ Math.abs(ABCMAJ.length - (Math.abs(diff)))]);
                    } else {
                        cadenaDesxifrada.append(ABCMAJ[Math.abs(diff)]);
                    }
             
                 
             }
        }
        return cadenaDesxifrada.toString();
    }

    private static String forcaBrutaRotX( String cadenaXifrada ) {
        StringBuilder cadenaDesxifrada = new StringBuilder();
        //provar totes les possibilitats
        System.out.println("Desxifrant cadena...");
        System.out.println("Resultat desxifrant amb: ");
        for (int i = 0; i < RotX.ABCMIN.length; i++ ) {
            cadenaDesxifrada.append(desxifraRotX(cadenaXifrada, i));
            System.out.println(i + " desplaçaments: " + cadenaDesxifrada);
        }
        return cadenaDesxifrada.toString();
    }
}
