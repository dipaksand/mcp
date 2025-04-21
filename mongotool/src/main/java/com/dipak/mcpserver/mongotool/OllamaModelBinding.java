package com.dipak.mcpserver.mongotool;

import dev.langchain4j.model.ollama.OllamaLanguageModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OllamaModelBinding {
    @Bean
    public OllamaLanguageModel ollamaLanguageModel() {
        return OllamaLanguageModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma:2b")
                .temperature(0.0)
                .timeout(Duration.ofMillis(600000))
                .build();
    }
}
