package lambdasinaction.chap2;

import java.util.*;

public class FilteringApples{

	public static void main(String ... args){

		List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red"));	

		//第二次尝试
		// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
		List<Apple> greenApples = filterApplesByColor(inventory, "green");
		System.out.println(greenApples);
		// [Apple{color='red', weight=120}]
		List<Apple> redApples = filterApplesByColor(inventory, "red");
		System.out.println(redApples);

		//第四次尝试
		// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
		List<Apple> greenApples2 = filter(inventory, new AppleColorPredicate());
		System.out.println(greenApples2);
		// [Apple{color='green', weight=155}]
		List<Apple> heavyApples = filter(inventory, new AppleWeightPredicate());
		System.out.println(heavyApples);
		// []
		List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
		System.out.println(redAndHeavyApples);

		//第五次尝试 匿名类
		// [Apple{color='red', weight=120}]
		List<Apple> redApples2 = filter(inventory, new ApplePredicate() {
			@Override
			public boolean test(Apple a){
				return a.getColor().equals("red"); 
			}
		});
		System.out.println(redApples2);

		//第六次尝试 lambda表达式
		List lambdaApples = filter(inventory,(Apple apple) -> "green".equals(apple.getColor()));
		System.out.println(lambdaApples);

		//第七次参数，泛型通用筛选方法(筛选苹果，筛选数字)
		List<Apple> chooseApples = filter(inventory,(Apple apple) -> "green".equals(apple.getColor()));
		System.out.println("泛型筛选出苹果：" + chooseApples);
		List<Integer> totalNumbers = new ArrayList<>();
		for( int i=1;i<=100;i++) {
			totalNumbers.add(i);
		}
		List<Integer> oddNums = filter(totalNumbers,(Integer i) -> i % 2 == 0);
		System.out.println("通过泛型筛选出偶数：" + oddNums);


		//2.4
		inventory.sort((Apple apple1,Apple apple2) -> apple1.getWeight().compareTo(apple2.getWeight()));
		System.out.println("根据重量排序：" + inventory);
		inventory.sort((Apple apple1,Apple apple2) -> apple1.getColor().compareTo(apple2.getColor()));
		System.out.println("根据颜色排序：" + inventory);
		Thread t = new Thread(()->System.out.println("这是一个线程"));
	}

	public static List<Apple> filterGreenApples(List<Apple> inventory){
		List<Apple> result = new ArrayList<>();
		for(Apple apple: inventory){
			if("green".equals(apple.getColor())){
				result.add(apple);
			}
		}
		return result;
	}

	public static List<Apple> filterApplesByColor(List<Apple> inventory, String color){
		List<Apple> result = new ArrayList<>();
		for(Apple apple: inventory){
			if(apple.getColor().equals(color)){
				result.add(apple);
			}
		}
		return result;
	}

	public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight){
		List<Apple> result = new ArrayList<>();
		for(Apple apple: inventory){
			if(apple.getWeight() > weight){
				result.add(apple);
			}
		}
		return result;
	}


	public static List<Apple> filter(List<Apple> inventory, ApplePredicate p){
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory){
			if(p.test(apple)){
				result.add(apple);
			}
		}
		return result;
	}       

	public static class Apple {
		private int weight = 0;
		private String color = "";

		public Apple(int weight, String color){
			this.weight = weight;
			this.color = color;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		@Override
		public String toString() {
			return "Apple{" +
					"color='" + color + '\'' +
					", weight=" + weight +
					'}';
		}
	}

	interface ApplePredicate{

		public boolean test(Apple a);
	}

	static class AppleWeightPredicate implements ApplePredicate{
		@Override
		public boolean test(Apple apple){
			return apple.getWeight() > 150; 
		}
	}
	static class AppleColorPredicate implements ApplePredicate{
		@Override
		public boolean test(Apple apple){
			return "green".equals(apple.getColor());
		}
	}

	static class AppleRedAndHeavyPredicate implements ApplePredicate{
		@Override
		public boolean test(Apple apple){
			return "red".equals(apple.getColor()) 
					&& apple.getWeight() > 150; 
		}
	}

	//-----------------------------2.3.4 ---------------------------
	interface Predicate<T>{
		boolean test(T t);
	}

	static <T> List<T> filter(List<T> inventory,Predicate<T> predicate) {
		List<T> result = new ArrayList<>();
		for(T each : inventory){
			if(predicate.test(each)){
				result.add(each);
			}
		}
		return result;
	}


}