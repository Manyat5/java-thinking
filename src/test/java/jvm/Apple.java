package jvm;

import lombok.*;


@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Apple {
	private double price;
	private String area;
	private int weight;

	public final static String ChineseName="苹果";
	public final static String JapeneseName="りんご";

	public synchronized void hello() {
		System.out.println("i am a apple!");
	}
}

