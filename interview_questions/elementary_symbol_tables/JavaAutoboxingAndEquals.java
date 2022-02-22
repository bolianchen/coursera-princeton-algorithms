public class JavaAutoboxingAndEquals {
    public static void main(String[] args) {
        double a = Double.NaN;
        double b = Double.NaN;
        Double x = a;
        Double y = b;
        System.out.println(a==b);
        System.out.println(x.equals(y));

        a = +0.0;
        b = -0.0;
        x = a;
        y = b;
        System.out.println(a==b);
        System.out.println(x.equals(y));

        int i = +0;
        int j = -0;
        Integer ii = +0;
        Integer jj = -0;
        System.out.println(i==j);
        System.out.println(ii.equals(jj));

    }
}
