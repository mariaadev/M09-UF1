public class RotX {

    public static char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public static char[] ABCMAJ = "AÁÀÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();

    public static void main(String[] args) {
        System.out.println(xifraRot13("MaRi. A ", 13));
        System.out.println(desxifraRot13("ÚgÁö. G ", 13));
        forcaBrutaRotX("ÚgÁö. G ");
    }


    private static String  xifraRot13(String cadena, int desplacament) {
        String cadenaXifrada = "";
        int index = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if(!Character.isLetter(cadena.charAt(i))) {
                cadenaXifrada = cadenaXifrada + cadena.charAt(i);
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
                    cadenaXifrada = cadenaXifrada + ABCMIN[(index + desplacament) - ABCMIN.length];
                } else {
                    cadenaXifrada = cadenaXifrada + ABCMIN[ index + desplacament];
                }

                
            } else {
                    for (int j = 0; j < ABCMAJ.length; j++) {
                        if (cadena.charAt(i) == ABCMAJ[j]) {
                            index = j;
                            break;
                        }
                    }
                    if ( (index + desplacament) >= ABCMAJ.length) {
                        cadenaXifrada = cadenaXifrada + ABCMAJ[(index + desplacament) - ABCMAJ.length];
                    } else {
                        cadenaXifrada = cadenaXifrada + ABCMAJ[ index + desplacament];
                    }
    
                   
                 
             }
        }
        return cadenaXifrada;
    }
    private static String desxifraRot13(String cadena, int desplacament) {
        String cadenaDesxifrada = "";
        int index = 0;

        for (int i = 0; i < cadena.length(); i++) {
            if(!Character.isLetter(cadena.charAt(i))) {
                cadenaDesxifrada = cadenaDesxifrada + cadena.charAt(i);
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
                    cadenaDesxifrada = cadenaDesxifrada + ABCMIN[ Math.abs(ABCMIN.length - (Math.abs(diff)))];
                } else {
                    cadenaDesxifrada = cadenaDesxifrada + ABCMIN[Math.abs(diff)];
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
                        cadenaDesxifrada = cadenaDesxifrada + ABCMAJ[ Math.abs(ABCMAJ.length - (Math.abs(diff)))];
                    } else {
                        cadenaDesxifrada = cadenaDesxifrada + ABCMAJ[Math.abs(diff)];
                    }
             
                 
             }
        }
        return cadenaDesxifrada;
    }

    private static String forcaBrutaRotX( String cadenaXifrada ) {
        String cadenaDesxifrada = "";
        //provar totes les possibilitats
        for (int i = 0; i < RotX.ABCMIN.length; i++ ) {
            cadenaDesxifrada = desxifraRot13(cadenaXifrada, i);
            System.out.println( "Resultat desxifrant amb " + i + " desplaçaments: " + cadenaDesxifrada);
        }
        return cadenaDesxifrada;
    }
}
