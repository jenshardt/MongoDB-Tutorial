package de.derSatan.tutorial.mongoDBTutorial;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;

public class MongoDBTutorial {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");

		// List all collections in MongoDB
		MongoIterable<String> listCollectionNames = db.listCollectionNames();
		listCollectionNames.forEach(new Block<String>() {
			public void apply(final String collectionName) {
				System.out.println("Collection: " + collectionName);
			}
		});

		// Count restaurants in Köln
		//db.createCollection("Restaurants Köln");
		MongoCollection<Document> col = db.getCollection("Restaurants Köln");
		
		System.out.println("Found " + col.count() + " restaurants");

		// Insert one value
		//createNewEntry(db);

		// List all values from db
		FindIterable<Document> iterableAll = db.getCollection("Restaurants Köln").find();
		printDocument(iterableAll);

		// List restaurants with plz = 51067
		Bson eq = Filters.eq("plz", new String("51067"));
		FindIterable<Document> iterable51067 = db.getCollection("Restaurants Köln").find(eq);
		printDocument(iterable51067);
	}

	private static void createNewEntry(MongoDatabase db) {
		MongoCollection<Document> collection = db.getCollection("Restaurants Köln");
		Document address = new Document().append("street", "Hauptstrasse 1a")
										 .append("plz", "51067");
		
		collection.insertOne(new Document().append("address", address)
								 .append("cuisine", "Brauhaus")
								 .append("name", "Malzmühle"));
	}

	private static void printDocument(FindIterable<Document> iterable) {
		System.out.println("--- printDocument() for iterable '" + iterable.getClass() + "' ---");
		iterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				System.out.println(document);
			}
		});
	}
}
