package iticbcn.xifratge;

public class ClauNoSuportada extends Exception {
    public ClauNoSuportada(String missatge) {
        /*crida a la classe pare Exception per passar el missatge */
        super(missatge);
    }
}
