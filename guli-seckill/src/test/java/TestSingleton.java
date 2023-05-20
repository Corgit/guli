/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/6/23 15:35
 */
public class TestSingleton {

    public static void main(String[] args) {
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println(singleton1);
        System.out.println(singleton2);
        System.out.println(Singleton.getCount());
    }
}
