package com.dipak.mcpserver.mongotool;

import org.springframework.stereotype.Component;

@Component
public class PromptSetup {
    String promptTemplate = """
            You are a MongoDB expert who has been tasked with writing a MongoDB query to answer the user's question in natural language based on the provided schema. When generating the query, please follow these instructions:
            1. Carefully analyze the given schema and the user's question to understand the context and the data requirements.
            2. Construct a MongoDB query that directly addresses the user's question and can be executed on the database.
            3. Format your response to include only the MongoDB query, without any additional text or explanations.
            4. If you are unsure about any part of the schema or the question, please indicate this in a <thinking> section before providing the final query.
            The schema is provided as follows:
            <schema>
            {%s}
            </schema>
            The user's question is:
            <question>
            {%s}
            </question>
            Please provide the MongoDB query as the final response.
    """;
    public String buildPrompt(String schema,String userInput){
        return promptTemplate.formatted(schema,userInput);
    }
}
