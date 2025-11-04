package org.example.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AIService {

  private final Client geminiClient;
  private final HttpClient httpClient = HttpClient.newHttpClient();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final String groqKey;

  public AIService() {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    geminiClient = Client.builder().apiKey(
        dotenv.get("GOOGLE_API_KEY")
    ).build();
    groqKey = dotenv.get("GROQ_API_KEY");
  }

  public String chat(String text) {
    return geminiClient.models.generateContent(
        "gemini-2.5-flash", text, null).text();
  }

  public String chatByGroq(String text) {
    try {
      // https://console.groq.com/docs/models
      HttpResponse<String> response = httpClient.send(
          HttpRequest.newBuilder()
              .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
              .header("Authorization", "Bearer %s".formatted(groqKey))
              .header("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(
                  objectMapper.writeValueAsString(new GroqRequest(
                      List.of(new GroqRequest.Message("user", text)), "openai/gpt-oss-120b")
                  ))
              )
              .build(),
          HttpResponse.BodyHandlers.ofString()
      );
//            return response.body();
      return objectMapper.readValue(response.body(), GroqResponse.class)
          .choices.get(0).message.content;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  record GroqRequest(List<Message> messages, String model) {

    record Message(String role, String content) {

    }
  }

  record GroqResponse(List<Choice> choices) {

    record Choice(Message message) {

      record Message(String content) {

      }
    }
  }

}
