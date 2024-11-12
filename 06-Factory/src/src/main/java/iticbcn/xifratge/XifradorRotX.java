package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {

    public static final char[] ABCMIN = "aáàäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public static final char[] ABCMAJ = "AÁÀÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();
    private String[] cadenes = {"MaRi. A", "PeDr.It.Ó", "Pedro MartíneZ", "ROme.o Y Ju.lieta"};
  
    public void main(String[] args) {
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
    
    private class ResultatIndex {
        int index = 0; 
        boolean esMajuscula = false; 

        public ResultatIndex (int index, boolean esMajuscula) {
            this.index = index;
            this.esMajuscula = esMajuscula;
        }
    }
    
    private  ResultatIndex buscarIndex (char caracter) {
        ResultatIndex resultat = new ResultatIndex( -1, false);
            for (int i = 0; i < ABCMIN.length; i++) {
                if (caracter == ABCMIN[i]) {
                    resultat.index = i;
                    resultat.esMajuscula = false;
                    break;
                }
            }
            for (int i = 0; i < ABCMAJ.length; i++) {
                if (caracter == ABCMAJ[i]) {
                    resultat.index = i;
                    resultat.esMajuscula = true;
                    break;
                }
            }
        return resultat; //index del caràcter i si és maj o min
    }

    private  String rota(String cadena, int desplacament, boolean dreta) {
        StringBuilder novaCadena = new StringBuilder();
        ResultatIndex resultat;
        int index;
        int diff = 0;
        boolean esMajuscula = false;
            //xifra 
                for (int i = 0; i < cadena.length(); i++) {
                    //comprovar si és maj, min o símbol

                    resultat = buscarIndex(cadena.charAt(i));
                    index = resultat.index;
                    esMajuscula = resultat.esMajuscula;

                    if (index == -1) { // és un símbol
                        novaCadena.append(cadena.charAt(i));
                        continue;
                    } 

                    if (dreta) {
                         diff = index + desplacament;
                    } else {
                        diff = index - desplacament; 
                        if (diff < 0) {
                            diff = diff + ABCMIN.length;
                        }
                    }
                   
                    if (esMajuscula) {
                        novaCadena.append(ABCMAJ[diff % ABCMAJ.length]);
                    }  else {
                        novaCadena.append(ABCMIN[diff % ABCMIN.length]);
                    }
                                    
                }

        return novaCadena.toString();
    }

    private String  xifraRotX(String cadena, int desplacament) {
        return rota(cadena,desplacament,true);
    }
   
    private String desxifraRotX(String cadena, int desplacament) {
       return rota(cadena,desplacament,false);
    }

    private void forcaBrutaRotX( String cadenaXifrada ) {
        //provar totes les possibilitats
        System.out.println("Desxifrant cadena...");
        System.out.println("Resultat desxifrant amb: ");
        for (int i = 0; i < XifradorRotX.ABCMIN.length; i++ ) {
            String cadenaDesxifrada = desxifraRotX(cadenaXifrada, i);
            System.out.println("\n" + i + ": " + cadenaDesxifrada + "\n");
        }
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        int desplacament;
        
        try {
            desplacament = Integer.parseInt(clau);

        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        if (desplacament < 0 || desplacament > 40) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        } 

        String cadenaXifrada = xifraRotX(msg, desplacament);
        
        return new TextXifrat(cadenaXifrada.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int desplacament;
        try {
            desplacament = Integer.parseInt(clau);

        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        if (desplacament < 0 || desplacament > 40) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        } 

        String cadenaDesxifrada = desxifraRotX(xifrat.toString(), desplacament);
        
        return cadenaDesxifrada;
    }
}
