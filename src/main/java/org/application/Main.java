package org.application;

import org.application.Services.AiService;
import org.application.Services.EmailService;
import org.application.Services.VaultService;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        VaultService vaultService = new VaultService();
        Scanner scanner = new Scanner(System.in);

        Map<String, String> segredos = vaultService.buscarSegredos();

        String emailValido = segredos.get("email.usuario");
        String senhaValida = segredos.get("email.senha");
        String apiKeyValue = segredos.get("gemini.api.key");

        EmailService emailService = new EmailService(emailValido, senhaValida);
        AiService aiService = new AiService(apiKeyValue);

        System.out.println("Buscando e-mails não lidos...");

        List<Message> emailsNaoLidos = emailService.ListarEmailNaoLidos(10);

        if (emailsNaoLidos.isEmpty()) {
            System.out.println("Nenhum e-mail novo não lido encontrado.");
            return;
        }

        System.out.println("\n--- E-MAILS DISPONÍVEIS PARA RESPONDER ---");
        for (int i = 0; i < emailsNaoLidos.size(); i++) {
            Message msg = emailsNaoLidos.get(i);
            System.out.printf("[%d] De: %s | Assunto: %s\n", i, InternetAddress.toString(msg.getFrom()), msg.getSubject());
        }

        System.out.print("\nDigite o número do e-mail que deseja responder (ou -1 para sair): ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha >= 0 && escolha < emailsNaoLidos.size()) {
            Message emailEscolhido = emailsNaoLidos.get(escolha);
            String conteudo = emailService.obterConteudoEmail(emailEscolhido);

            System.out.println("\nBuscando sugestão da IA com a API Key recuperada do Vault...");
            String respostaIA = aiService.gerarRespostaAutomatica(conteudo);

            System.out.println("\n================ RESPOSTA GERADA ================");
            System.out.println(respostaIA);
            System.out.println("=================================================");
        } else {
            System.out.println("Operação encerrada.");
        }

        emailService.fecharConexao();
    }
}