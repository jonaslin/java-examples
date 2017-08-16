package com.jonas.lucene;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDao {

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private MongoCollection<Document> mongoCollection;

	public void init() {
		mongoClient = new MongoClient("localhost", 27017);
		mongoDatabase = mongoClient.getDatabase("test");
		mongoCollection = mongoDatabase.getCollection("collection1");
	}

	public void insert(String url, String text, String html) {
		Document document = new Document();
		document.append("url", url);
		document.append("text", text);
		document.append("html", html);
		mongoCollection.insertOne(document);
	}

	public MongoCollection<Document> getCollection() {
		return mongoCollection;
	}

	public void close() {
		mongoClient.close();

		mongoCollection = null;
		mongoDatabase = null;
		mongoClient = null;
	}
}
