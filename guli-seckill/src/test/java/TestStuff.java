import java.time.LocalDateTime;
import java.util.Date;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/25 11:21
 */
public class TestStuff {

    public static void main(String[] args) {

        int i = 1, j = -16;
        System.out.println(i <<= 2);
        System.out.println(i >> 1);
        System.out.println(j >> 1);
        System.out.println(j >>> 1);

        char a = 'k', b = '1';
        int c = a & b;
        System.out.println("c >> " + c);

        String z = "abc";
        System.out.println(z+a);
        System.out.println(z+i);
        System.out.println(i+z);
        System.out.println(a+b);
        System.out.println(i+a);

        int n = 12_000_000;
        System.out.println(n+111);

        Date now = new Date();
        Date t = new Date();
        System.out.println("now >> " + now);
        System.out.println("t >> " + t);
        System.out.println(now.after(t));
        System.out.println(now.before(t));

    }
}
