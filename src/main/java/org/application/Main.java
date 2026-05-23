package org.application;

import io.github.jopenlibs.vault.VaultException;
import org.application.Services.EmailService;
//import org.application.Services.VaultService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
//        VaultService vaultService = new VaultService();

        EmailService emailService = new EmailService("talles.sc@aluno.ifsc.edu.br", "ckpmqcsyvibdhmbd");

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


    }
}