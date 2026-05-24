package org.application.Services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class AiService {

    private final Client client;

    public AiService(String apiKey) {
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String gerarRespostaAutomatica(String textoDoEmail) {
        try {
            String prompt = "Aja como um assistente de e-mails profissional. "
                    + "Gere uma resposta educada, prestativa e direta para o seguinte e-mail: \n\n"
                    + textoDoEmail;

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null
            );

            return response.text();
        } catch (Exception e) {
            return "Erro ao gerar resposta com a IA: " + e.getMessage();
        }
    }
}