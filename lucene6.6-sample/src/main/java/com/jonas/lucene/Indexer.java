package com.jonas.lucene;

import java.io.IOException;
import java.nio.file.FileSystems;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;

import com.mongodb.client.MongoCollection;

public class Indexer {

	public void createIndex() {
		try {
			Analyzer luceneAnalyzer = new StandardAnalyzer();

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(luceneAnalyzer);
			IndexWriter indexWriter = new IndexWriter(
					new SimpleFSDirectory(FileSystems.getDefault().getPath("resources", "index")), indexWriterConfig);

			MongoDao mongoDao = new MongoDao();
			mongoDao.init();
			MongoCollection<org.bson.Document> mongoCollection = mongoDao.getCollection();

			for (org.bson.Document mongoDocument : mongoCollection.find()) {
				org.apache.lucene.document.Document luceneDocument = new org.apache.lucene.document.Document();

				String url = (String) mongoDocument.get("url");
				luceneDocument.add(new TextField("url", url, Field.Store.YES));
				System.out.println(url);

				String text = (String) mongoDocument.get("text");
				luceneDocument.add(new TextField("text", text, Field.Store.YES));
				indexWriter.addDocument(luceneDocument);
			}

			indexWriter.commit();
			indexWriter.close();
			mongoDao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
