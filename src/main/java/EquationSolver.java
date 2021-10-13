import java.util.Arrays;
import java.util.Scanner;

public class EquationSolver {
    public static String solve(double a, double b, double c) {
        if (a == 0) {
            return "Not a quadratic equation";
        }
        double discSqrt = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
        double root1 = (-b + discSqrt) / (2 * a);
        double root2 = (-b - discSqrt) / (2 * a);
        return Arrays.toString(new double[]{root1, root2});
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter coefficient a: ");
        String a = reader.nextLine();
        System.out.println("Enter coefficient b: ");
        String b = reader.nextLine();
        System.out.println("Enter coefficient c: ");
        String c = reader.nextLine();
        try {
            double doublea = Double.parseDouble(a);
            double doubleb = Double.parseDouble(b);
            double doublec = Double.parseDouble(c);
            System.out.println("Roots: "+ solve(doublea, doubleb, doublec));
        } catch (Exception e){
            System.out.println("Exception occured: " + e);
        }
    }
}
