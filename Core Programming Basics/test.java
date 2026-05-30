public class test
{
    static void pop(int n){
        if(n==0) return;
        System.out.print(n);
        pop(n-1);
        System.out.print(n);
        pop(n-1);
        System.out.print(n);
    }
	public static void main(String[] args) {
		pop(2);
	}
}
