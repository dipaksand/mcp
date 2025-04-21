package com.dipak.mcpserver.mongotool.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Service
public class MongoSchemaService {
    @Autowired
    public MongoClient client;

    public Map<String, Set<String>> scanSchema(String dbName) {
        Map<String, Set<String>> schema = new HashMap<>();
        MongoDatabase database = client.getDatabase(dbName);
        for (String collectionName : database.listCollectionNames()) {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document sampleDocument = collection.find().first();
            if (sampleDocument != null) {
                Set<String> fields = sampleDocument.keySet();
                schema.put(collectionName, fields);
            }
        }
        return schema;
    }
}
