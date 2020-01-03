package emum;

import java.util.Arrays;

/**
 * 枚举测试
 * @author Administrator
 *
 */
public enum Festival {
    /**
     *
     */
    NewYearsDay("元旦",1,1),
    SpringFestival("春节",1,1,true),
    WomensDay("妇女节",3,8);

    private String name;
    private int month;
    private int day;
    private boolean lunar;
    private String description;

    Festival(String name,int month, int day,boolean lunar) {
        this(name,month,day);
        this.lunar=lunar;
    }
    Festival(String name,int month, int day) {
        this.name=name;
        this.month = month;
        this.day = day;
    }
    @Override
    public String toString() {
        description=description==null?"":description;
        return name+"，日期为+"+month+"月"+day+"日，"+description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        Festival festival=NewYearsDay;
        festival.setDescription("是一年的新开始！");
        System.out.println(festival);
        System.out.println(NewYearsDay);
        System.out.println(SpringFestival.compareTo(WomensDay));
//		System.out.println(WomensDay);
//        System.out.println(ORANGE_BLOOD+ORANGE_NAVEL+ORANGE_TEMPLE+APPLE_FUJI+APPLE_PIPPIN+APPLE_GRANNY_SMITH);
        Festival[] enumConstants = Festival.class.getEnumConstants();
        System.out.println("枚举数量为"+enumConstants.length);
        Arrays.stream(enumConstants).forEach(System.out::println);
    }
//    public static final int APPLE_FUJI=0;
//    public static final int APPLE_PIPPIN=1;
//    public static final int APPLE_GRANNY_SMITH=2;
//
//    public static final int ORANGE_NAVEL = 0;
//    public static final int ORANGE_TEMPLE = 1;
//    public static final int ORANGE_BLOOD = 2;

}

