public class TypeConversions {
    public static void main(String args[]) {
        int a = 100;
        double b = a;
        System.out.println("Integer to Double:" + a + "=>" + b);
        long c = a;
        System.out.println("Integer to Long:" + a + "=>" + c);

        double k = 10.56;
        int d = (int) k;
        System.out.println("Double to Integer:" + k + "=>" + d);
        long p = 58746;
        int e = (int) p;
        System.out.println("long to Integer:" + p + "=>" + e);
    }
}
