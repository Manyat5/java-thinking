package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Apple {
	private double price;
	private String area;
	private int weight;

	final static String ChineseName="苹果";
	final static String JapeneseName="りんご";
}

