
public class RotX {

    public static char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public static char[] ABCMAJ = "AÁÀÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();

    public static void main(String[] args) {
        System.out.println(xifraRotX("MaRi. A ", 13));
        System.out.println(desxifraRotX("ÚgÁö. G ", 13));
        forcaBrutaRotX("ÚgÁö. G ");
    }


    private static String rota(String cadena, int desplacament, boolean dreta) {
        StringBuilder novaCadena = new StringBuilder();
        int index = 0;
        if (dreta) {
            //xifra
               
                for (int i = 0; i < cadena.length(); i++) {
                    if(!Character.isLetter(cadena.charAt(i))) {
                        novaCadena.append(cadena.charAt(i)) ;
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
                            novaCadena.append(ABCMIN[(index + desplacament) - ABCMIN.length]);
                        } else {
                            novaCadena.append(ABCMIN[ index + desplacament]) ;
                        }
        
                        
                    } else {
                            for (int j = 0; j < ABCMAJ.length; j++) {
                                if (cadena.charAt(i) == ABCMAJ[j]) {
                                    index = j;
                                    break;
                                }
                            }
                            if ( (index + desplacament) >= ABCMAJ.length) {
                                novaCadena.append(ABCMAJ[(index + desplacament) - ABCMAJ.length]);
                            } else {
                                novaCadena.append(ABCMAJ[ index + desplacament]);
                            }
            
                           
                         
                     }
                }
        
           
        } else {    
           //desxifra
            for (int i = 0; i < cadena.length(); i++) {
                if(!Character.isLetter(cadena.charAt(i))) {
                    novaCadena.append(cadena.charAt(i)) ;
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
                        novaCadena.append(ABCMIN[ Math.abs(ABCMIN.length - (Math.abs(diff)))]);
                    } else {
                        novaCadena.append(ABCMIN[Math.abs(diff)]);
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
                            novaCadena.append(ABCMAJ[ Math.abs(ABCMAJ.length - (Math.abs(diff)))]);
                        } else {
                            novaCadena.append(ABCMAJ[Math.abs(diff)]);
                        }
                 
                     
                 }
            }
           
        }

        return novaCadena.toString();
    }

    private static String  xifraRotX(String cadena, int desplacament) {
        return rota(cadena,desplacament,true);
    }
   
    private static String desxifraRotX(String cadena, int desplacament) {
       return rota(cadena,desplacament,false);
    }

    private static void forcaBrutaRotX( String cadenaXifrada ) {
        //provar totes les possibilitats
        System.out.println("Desxifrant cadena...");
        System.out.println("Resultat desxifrant amb: ");
        for (int i = 0; i < RotX.ABCMIN.length; i++ ) {
            String cadenaDesxifrada = desxifraRotX(cadenaXifrada, i);
            System.out.println(i + " desplaçaments: " + cadenaDesxifrada);
        }
    }
}
