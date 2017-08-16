package com.jonas.lucene;

// mvn exec:java -Dexec.mainClass=com.jonas.lucene.App -Dexec.args="index"
// mvn exec:java -Dexec.mainClass=com.jonas.lucene.App -Dexec.args="search"
public class App {
	public static void main(String[] args) {
		if (args.length != 1)
			return;

		if (args[0].equals("index"))
			new Indexer().createIndex();
		else if (args[0].equals("search"))
			new Searcher().search();
	}
}
