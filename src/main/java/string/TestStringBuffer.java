package string;

public class TestStringBuffer {
	public static void main(String[] args) {
		StringBuffer sb=new StringBuffer("abc");//只在初始化确定容量
		sb.append(false);
		sb.deleteCharAt(4);
		System.out.println(sb);
//		System.out.println(sb.capacity());
	}
}
