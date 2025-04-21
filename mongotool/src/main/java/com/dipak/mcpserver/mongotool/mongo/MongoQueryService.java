package com.dipak.mcpserver.mongotool.mongo;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoQueryService {
    @Autowired
    public MongoClient client;
    public List<Document> execute(String jsonQuery){
        String collectionName = jsonQuery.split("\\.")[1];
        String queryPortion= jsonQuery.substring(jsonQuery.indexOf("{"), jsonQuery.lastIndexOf("}") + 1);
        Bson query = Document.parse(queryPortion);

        return client.getDatabase("sample_data").getCollection(collectionName).find(query).into(new java.util.ArrayList<>());
    }
}
