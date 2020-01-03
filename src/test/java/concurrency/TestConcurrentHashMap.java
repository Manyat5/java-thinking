package concurrency;

import jvm.Apple;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wwy
 */
public class TestConcurrentHashMap {

    public static void main(String[] args) {
        ConcurrentMap<String, Apple> concurrentMap = new ConcurrentHashMap<>();
        Apple apple = new Apple(400, "山东烟台", 8);
        Apple apple2 = new Apple(500, "山东烟台2号", 9);
        concurrentMap.put("山东烟台",apple);
        concurrentMap.put("山东烟台",apple2);
    }
}
