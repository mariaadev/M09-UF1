
public class RotX {

    public static final char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public static final char[] ABCMAJ = "AÁÀÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();
    public static String[] cadenes = {"MaRi. A", "PeDr.It.Ó", "Pedro MartíneZ", "ROme.o Y Ju.lieta"};
    public static boolean esMajuscula = false;
    public static void main(String[] args) {
        String cadenaXifrada = "";
        String cadenaDesxifrada = "";
        int desplacament = 8;
        for (int i = 0; i < cadenes.length; i++) {
            System.out.println("\n--------------------------\n");
            System.out.println("Paraula: " + cadenes[i]);
            System.out.println("Desplaçament: " + desplacament);
            
            cadenaXifrada = xifraRotX(cadenes[i], desplacament);
            System.out.println("Cadena xifrada: " + cadenaXifrada);
            
            cadenaDesxifrada = desxifraRotX(cadenaXifrada, desplacament);
            System.out.println("Cadena desxifrada: " + cadenaDesxifrada);

            System.out.println("\n--------------------------\n");

            forcaBrutaRotX(cadenaXifrada);
            //provar amb diferents desplaçaments
            desplacament+=2;

        }
       
       
    }
    private static int buscarIndex (char caracter) {
        int index = -1;
            for (int i = 0; i < ABCMIN.length; i++) {
                if (caracter == ABCMIN[i]) {
                    index = i;
                    esMajuscula = false;
                    break;
                }
            }
            for (int i = 0; i < ABCMAJ.length; i++) {
                if (caracter == ABCMAJ[i]) {
                    index = i;
                    esMajuscula = true;
                    break;
                }
            }
        return index;
    }

    private static String rota(String cadena, int desplacament, boolean dreta) {
        StringBuilder novaCadena = new StringBuilder();
        int index = 0;
        if (dreta) {
            //xifra 
                for (int i = 0; i < cadena.length(); i++) {
                    //comprovar si és maj, min o símbol
                    index = buscarIndex(cadena.charAt(i));

                    if (index == -1) {
                        novaCadena.append(cadena.charAt(i));
                        continue;
                    } 

                    int diff = index + desplacament;

                    if (esMajuscula) {
                        novaCadena.append(ABCMAJ[diff % ABCMAJ.length]);
                    }  else {
                        novaCadena.append(ABCMIN[diff % ABCMIN.length]);
                    }
                                    
                }
           
        } else {    
           //desxifra
            for (int i = 0; i < cadena.length(); i++) {
                    index = buscarIndex(cadena.charAt(i));

                    if (index == -1) {
                        novaCadena.append(cadena.charAt(i));
                        continue;
                    } 

                    int diff = index - desplacament; 

                    if (diff < 0) {
                        diff = diff + ABCMIN.length;
                    }

                    if (esMajuscula) {
                        novaCadena.append(ABCMAJ[Math.abs(diff) % ABCMAJ.length]);
                    } else {
                       novaCadena.append(ABCMIN[Math.abs(diff) % ABCMIN.length]);
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
