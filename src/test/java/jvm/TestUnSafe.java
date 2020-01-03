package jvm;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wwy
 * 测试Unsafe的一些方法
 */
public class TestUnSafe {

    /**
     几个属性的偏移值area=24,price=16,weight=12
     */
    static Class<Apple> c=Apple.class;
    static Field area;
    static Field price ;
    static Field weight;
    static Field chineseName;
    static Field japeneseName;

    static Unsafe unsafe =getUnsafe();

    static {
        try {
            area = c.getDeclaredField("area");
            price = c.getDeclaredField("price");
            weight = c.getDeclaredField("weight");
            chineseName = c.getDeclaredField("ChineseName");
            japeneseName = c.getDeclaredField("JapeneseName");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取unsafe对象用于测试
     * @return
     */
    private static Unsafe getUnsafe(){
        Field f ;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            return null;
        }
    }
    //---------------------------------对象------------------------------------//
    @Test
    /**
     * objectFieldOffset,属性在对象中的偏移量
     */
    public void getFieldOffset(){
        //objectFieldOffset，第一个为12，每隔自己占用内存大小（如Int为4字节，则下一个属性为16，double则为8,引用对象都是4个字节）
        //前12字节被头对象占有
        //属性偏移量与顺序无关，里面应该有属于jvm自己的一套排序规则，比如int排double前面，基本数据类型排引用前面
        System.out.println( "area="+unsafe.objectFieldOffset(area)+
                            "price="+unsafe.objectFieldOffset(price)+
                            "weight="+unsafe.objectFieldOffset(weight));
        //静态属性偏移量，从104开始,同样是相隔4个字节
        System.out.println("chineseName="+unsafe.staticFieldOffset(chineseName)
                            +"japeneseName="+unsafe.staticFieldOffset(japeneseName));
    }
    /**
     * staticFieldBase,返回给定的静态属性的位置
     */
    @Test
    public void getFieldBase(){
        System.out.println("chineseName="+unsafe.staticFieldBase(chineseName)
                +"japeneseName="+unsafe.staticFieldBase(japeneseName));
        //chineseName=class pojo.jvm.Apple japeneseName=class pojo.jvm.Apple
    }
    /**
     * 通过上面的偏移值获取实际对象属性的值
     */
    @Test
    public void getOffsetByObjecty(){
        //应该是400
        Apple apple = new Apple(400, "山东烟台", 8);
        System.out.println(unsafe.getInt(apple, 12L));
        System.out.println(unsafe.getDouble(apple,16L));
        System.out.println(unsafe.getObject(apple, 24L));
    }
    /**
     * 通过偏移值设置对象的属性
     */
    @Test
    public void setFieldByOffset(){
        Apple apple = new Apple(400, "山东烟台", 8);
        System.out.println("山东烟台="+apple);
        //下面两种情况都无法设置为450,而且会改变小数点后面的数，可能和double类型的各个位置的数对应
//        unsafe.putObject(apple,16L,new Double(450L));
//        unsafe.putInt(apple,16L,450);
        unsafe.putDouble(apple,16L,450L);
        unsafe.putObject(apple,24L,"山东烟台2号");
        System.out.println("山东烟台2号="+apple);
    }
    /**
     * allocateInstance()
     */
    @Test
    public void testAllocateInstance() throws InstantiationException {
        //即使构造器私有也可以构造生成实例
        Object o = unsafe.allocateInstance(c);
        if(o instanceof Apple){
            Apple apple=(Apple)o;
            apple.setPrice(10);
            apple.setArea("陕西红富士");
            apple.setWeight(500);
            System.out.println(apple);
        }
    }

    /**
     * 数组的偏移值
     */
    @Test
    public void getArrayIndexScale(){
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        System.out.println(unsafe.arrayIndexScale(ConcurrentHashMap.SimpleEntry[].class));//4,偏移值增量
        System.out.println(unsafe.arrayBaseOffset(Apple[].class));//16，数组偏移值起始值
    }

    //---------------------------------内存------------------------------------//
    @Test
    public void getMemoryInfo(){
        //获取本地内存的页数，此值为2的幂次方
        System.out.println("pageSize="+unsafe.pageSize());
        //获取本地指针的大小(单位是byte)，通常值为4或者8。常量ADDRESS_SIZE就是调用此方法
        System.out.println("addressSize=" + unsafe.addressSize());
    }
    /**
     * 分配并使用内存
     */
    @Test
    public void testMemory(){
        Apple apple = new Apple(400, "山东烟台", 8);
        long address = unsafe.allocateMemory(40L);
        System.out.println("分配的内存地址为："+address);
        String s="reentrantLockhello";
        //不能够放置对象
        unsafe.putLong(address,111111L);
        //如果重新申请的内存过大，则很有可能分配新的地址
//        long newAddress = unsafe.reallocateMemory(address, 1024L);
        long newAddress = unsafe.reallocateMemory(address, 80L);
        System.out.println("新分配的内存地址为："+newAddress);
    }

    //---------------------------------同步------------------------------------//
    @Test
    public void testMonitor() throws Exception{
        Apple apple = new Apple(400, "山东烟台", 8);
        //只要一直不释放锁，其他线程就无法获取到该资源
        unsafe.monitorEnter(apple);
        new Thread(()->apple.hello()).start();
        Thread.sleep(5000);
        System.out.println("睡眠时间结束");
        unsafe.monitorExit(apple);
    }
    //---------------------------------线程------------------------------------//
    @Test
    public void testPark() throws Exception{
        Apple apple = new Apple(400, "山东烟台", 8);
        System.out.println("阻塞"+Thread.currentThread());
        //阻塞当前线程
        unsafe.park(true,System.currentTimeMillis()+5000);
        System.out.println(apple);
    }
    @Test
    public void testPark2() throws Exception{
        Apple apple = new Apple(400, "山东烟台", 8);
        System.out.println("阻塞"+Thread.currentThread());
        //阻塞当前线程
        unsafe.park(false,0);
        System.out.println(apple);
    }
    @Test
    public void testFinal() throws Exception{
        Apple apple = new Apple(400, "山东烟台", 8);
        long offset = unsafe.staticFieldOffset(japeneseName);
        if(unsafe.compareAndSwapObject(Apple.class,offset,"りんご","日本苹果")){
            System.out.println("改变成功");
        }
        System.out.println(Apple.JapeneseName);
    }
}
