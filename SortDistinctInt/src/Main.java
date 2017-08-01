import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		Integer[] numbers = { 2, 1, 1, 9, 0, 7, -6, 4, 8, 2, 1, -10 };
		Arrays.sort(numbers);
		Set<Integer> s = new LinkedHashSet<Integer>(Arrays.asList(numbers));
		System.out.println(s);
	}
}
