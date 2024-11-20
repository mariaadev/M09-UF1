public class Rot13 {

    public static char[] abcMin = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    public static char[] abcMaj = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    public static void main(String[] args) {
        System.out.println(xifraRot13("MaRi.A "));
        System.out.println(desxifraRot13("ZnEv.N"));
    }

    private static String  xifraRot13(String cadena) {
        String cadenaXifrada = "";
        int index = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if(!Character.isLetter(cadena.charAt(i))) {
                cadenaXifrada = cadenaXifrada + cadena.charAt(i);
                continue;
            }
            if (Character.isLowerCase(cadena.charAt(i))) {
                for (int j = 0; j < abcMin.length; j++) {
                    if (cadena.charAt(i) == abcMin[j]) {
                        index = j;
                        break;
                    }
                }
                if ( (index + 13) >= abcMin.length) {
                    //resta 26 i comença per l'inici
                    cadenaXifrada = cadenaXifrada + abcMin[(index + 13) - abcMin.length];
                } else {
                    cadenaXifrada = cadenaXifrada + abcMin[ index + 13];
                }

                
            } else {
                    for (int j = 0; j < abcMaj.length; j++) {
                        if (cadena.charAt(i) == abcMaj[j]) {
                            index = j;
                            break;
                        }
                    }
                    if ( (index + 13) >= abcMaj.length) {
                        //resta 25 i comença per l'inici
                        cadenaXifrada = cadenaXifrada + abcMaj[(index + 13) - abcMaj.length];
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
            if(!Character.isLetter(cadena.charAt(i))) {
                cadenaDesxifrada = cadenaDesxifrada + cadena.charAt(i);
                continue;
            }
            if (Character.isLowerCase(cadena.charAt(i))) {
                for (int j = 0; j < abcMin.length; j++) {
                    if (cadena.charAt(i) == abcMin[j]) {
                        index = j;
                        break;
                    }
                }
                int diff = index - 13; 
                if ( diff < 0) {
                    cadenaDesxifrada = cadenaDesxifrada + abcMin[ Math.abs(abcMin.length - (Math.abs(diff)))];
                } else {
                    cadenaDesxifrada = cadenaDesxifrada + abcMin[Math.abs(diff)];
                }
   
            } else {
                    for (int j = 0; j < abcMaj.length; j++) {
                        if (cadena.charAt(i) == abcMaj[j]) {
                            index = j;
                            break;
                        }
                    }
                    int diff = index - 13; 
                    if ( diff < 0) {
                        cadenaDesxifrada = cadenaDesxifrada + abcMaj[ Math.abs(abcMaj.length - (Math.abs(diff)))];
                    } else {
                        cadenaDesxifrada = cadenaDesxifrada + abcMaj[Math.abs(diff)];
                    }
             
                 
             }
        }
        return cadenaDesxifrada;
    }
}
