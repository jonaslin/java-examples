import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		Map<String, Integer> wordCount = new HashMap<String, Integer>();

		try {
			String rootPath = System.getProperty("user.dir");
			fileReader = new FileReader(rootPath + "/resources/words");

			bufferedReader = new BufferedReader(fileReader);

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.replaceAll("\\.|,|\"|\\[|\\]|\\(|\\)", "");
				for (String word : line.split("\\s")) {
					if (word.equals(""))
						continue;

					Integer count = wordCount.get(word);
					if (count == null)
						wordCount.put(word, 1);
					else
						wordCount.put(word, count + 1);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		List<Entry<String, Integer>> sortedWordCount = new ArrayList<Entry<String, Integer>>(wordCount.entrySet());
		Collections.sort(sortedWordCount,
				(Entry<String, Integer> o1, Entry<String, Integer> o2) -> -(o1.getValue() - o2.getValue()));
		System.out.println(sortedWordCount);
	}
}
