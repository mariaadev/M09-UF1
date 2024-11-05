package iticbcn.xifratge;

public class AlgorismeAES extends XifradorFactory {
    @Override
    public Xifrador creaXifrador() {
        return XifradorAES;
    }
}
