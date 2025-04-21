package com.dipak.mcpserver.mongotool;

import com.dipak.mcpserver.mongotool.mongo.MongoQueryService;
import com.dipak.mcpserver.mongotool.mongo.MongoSchemaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.model.ollama.OllamaLanguageModel;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/mcp/query")
public class UserController {
    @Autowired
    MongoSchemaService mongoSchemaService;
    @Autowired
    MongoQueryService mongoQueryService;
    @Autowired
    OllamaLanguageModel mistral;
    @Autowired
    PromptSetup promptSetup;

    @PostMapping
    public ResponseEntity<?> run(@RequestBody Map<String, String> body) throws JsonProcessingException {
        String question =body.get("query");
        Map<String, Set<String>> schema = mongoSchemaService.scanSchema("sample_data");
        String prompt = promptSetup.buildPrompt(schema.toString(), question);
        String jsonQuery = String.valueOf(mistral.generate(prompt));
        List<Document> results = mongoQueryService.execute(extractQuery(jsonQuery));
        return ResponseEntity.ok(results);
    }

    private String extractQuery(String jsonQuery) {
        // Extract everything within the triple backticks
        int startIndex = jsonQuery.indexOf("```") + 3;
        int endIndex = jsonQuery.lastIndexOf("```");
        if (startIndex >= 3 && endIndex > startIndex) {
            return jsonQuery.substring(startIndex, endIndex).trim().replaceAll("mongodb","");
        }
        throw new IllegalArgumentException("No content found between triple backticks");
    }
}
