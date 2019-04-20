import com.ooad.backend.Expression;


public class HelloWorld {
    public static void main(String[] args) {
        Expression eh = new Expression("(2+3)!");
        System.out.println(eh.calculate());
    }
}
