package stream;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author wwy
 */
public class ParallStreamDemo {
    public static void main(String[] args) {
        Stream<String> stream = Arrays.stream(new String[]{"123", "455", "11"});
        stream.parallel().map(x->{
            System.out.println(Thread.currentThread()+"======"+x);
            return x;
        }).count();
    }
}
