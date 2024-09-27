
public class RotX {

    public static final char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public static final char[] ABCMAJ = "AÁÀÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();
    public static String[] cadenes = {"MaRi. A", "PeDr.It.Ó", "Pedro MartíneZ", "ROme.o Y Ju.lieta"};
   
    public static void main(String[] args) {
        String cadenaXifrada = "";
        String cadenaDesxifrada = "";
        int desplacament = 8;
        for (int i = 0; i < cadenes.length; i++) {
            System.out.println("Paraula: " + cadenes[i]);
            System.out.println("Desplaçament: " + desplacament);
            
            cadenaXifrada = xifraRotX(cadenes[i], desplacament);
            System.out.println("Cadena xifrada: " + cadenaXifrada);
            
            cadenaDesxifrada = desxifraRotX(cadenaXifrada, desplacament);
            System.out.println("Cadena desxifrada: " + cadenaDesxifrada);

            System.out.println("--------------------------");

            forcaBrutaRotX(cadenaXifrada);

        }
       
       
    }

    private static String rota(String cadena, int desplacament, boolean dreta) {
        StringBuilder novaCadena = new StringBuilder();
        int index = 0;
        if (dreta) {
            //xifra 
                for (int i = 0; i < cadena.length(); i++) {
                    boolean cadenaTrobadaMin = false;
                    boolean cadenaTrobadaMaj = false;
    
                        for (int j = 0; j < ABCMIN.length; j++) {
                            
                            if (cadena.charAt(i) == ABCMIN[j]) {
                                index = j;
                                cadenaTrobadaMin = true;
                                break;
                            } 
    
                            if(cadena.charAt(i) == ABCMAJ[j]){
                                index = j;
                                cadenaTrobadaMaj = true;
                                break;
                            }
                        }

                        int diff = index + desplacament;

                        if (cadenaTrobadaMin) {
                            novaCadena.append(ABCMIN[diff % ABCMIN.length]);
                        } else if(cadenaTrobadaMaj) {
                           novaCadena.append(ABCMAJ[diff % ABCMAJ.length]);
                        } else {
                            novaCadena.append(cadena.charAt(i)) ;
                        }      
                         
                     
                }
        
           
        } else {    
           //desxifra
            for (int i = 0; i < cadena.length(); i++) {
                boolean cadenaTrobadaMin = false;
                boolean cadenaTrobadaMaj = false;

                    for (int j = 0; j < ABCMIN.length; j++) {
                        
                        if (cadena.charAt(i) == ABCMIN[j]) {
                            index = j;
                            cadenaTrobadaMin = true;
                            break;
                        } 

                        if(cadena.charAt(i) == ABCMAJ[j]){
                            index = j;
                            cadenaTrobadaMaj = true;
                            break;
                        }
                    }

                    int diff = index - desplacament; 

                    if (diff < 0) {
                        diff = diff + ABCMIN.length;
                    }

                    if (cadenaTrobadaMin) {
                        novaCadena.append(ABCMIN[Math.abs(diff) % ABCMIN.length]);
                    } else if(cadenaTrobadaMaj) {
                       novaCadena.append(ABCMAJ[Math.abs(diff) % ABCMAJ.length]);
                    } else {
                        novaCadena.append(cadena.charAt(i)) ;
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
