package comp1140.ass2;

public class test{
    public static void main(String[] args) {
        String a = "abc";
        String b = a;
        a = a + "c";
        System.out.println(a);
        System.out.println(b);
    }
}