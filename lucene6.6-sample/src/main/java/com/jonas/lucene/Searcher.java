package com.jonas.lucene;

import java.io.IOException;
import java.nio.file.FileSystems;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.SimpleFSDirectory;

public class Searcher {
	public static final String INDEX_PATH = System.getProperty("user.dir") + "/resources/index";

	public void search() {
		try {
			IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader
					.open(new SimpleFSDirectory(FileSystems.getDefault().getPath("resources", "index"))));

			Analyzer analyzer = new StandardAnalyzer();

			QueryParser parser1 = new QueryParser("text", analyzer);
			Query query1 = parser1.parse("java");

			QueryParser parser2 = new QueryParser("text", analyzer);
			Query query2 = parser2.parse("lucene");

			BooleanQuery booleanQuery = new BooleanQuery.Builder().add(query1, BooleanClause.Occur.MUST)
					.add(query2, BooleanClause.Occur.MUST).build();

			TopDocs topDocs = indexSearcher.search(booleanQuery, 10);
			System.out.println("totalHits: " + topDocs.totalHits);
			System.out.println("getMaxScore: " + topDocs.getMaxScore());
			System.out.println("scoreDocs.length: " + topDocs.scoreDocs.length);

			// ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			// for (ScoreDoc sd : scoreDocs) {
			// System.out.println(indexSearcher.doc(sd.doc));
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
