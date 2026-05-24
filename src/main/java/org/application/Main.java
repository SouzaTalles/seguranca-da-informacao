package org.application;

import org.application.Services.EmailService;
import org.application.Services.VaultService;

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
        String apiKeyValue = segredos.get("openai_api_key");

        EmailService emailService = new EmailService(emailValido, senhaValida);

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

        emailService.fecharConexao();
    }
}