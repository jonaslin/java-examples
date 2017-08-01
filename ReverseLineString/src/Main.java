import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		String line = "This is a reverse sample";
		List<String> words = (List<String>) Arrays.asList(line.split(" "));
		Collections.reverse(words);
		System.out.println(words);
	}
}
