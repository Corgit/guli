/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/6/23 15:35
 */
public class Singleton {

    private static int count = 0;
    private static Singleton instance;

    private Singleton() {
        ++count;
        System.out.println("constructor >>> " + count);
    }

    {
        ++count;
        System.out.println("instance >>> " + count);
    }

    static {
        ++count;
        System.out.println("static >>> " + count);
    }

    public static Singleton getInstance() {
        if(instance == null) {
            synchronized (Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

    public static int getCount() {
        return count;
    }
}
