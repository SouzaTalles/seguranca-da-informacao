package org.application.Services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailService {

    private final String emailUser;
    private final String emailPassword;
    private Store store;
    private Folder inbox;

    public EmailService(String emailUser, String emailPassword) {
        this.emailUser = emailUser;
        this.emailPassword = emailPassword;
    }

    public List<Message> ListarEmailNaoLidos(int limite) throws MessagingException {
        this.store = getImapStore();
        this.inbox = getFolderFromStore(store, "INBOX");

        List<Message> naoLidos = new ArrayList<>();
        Message[] messages = inbox.getMessages();

        for (int i = messages.length - 1; i >= 0; i--) {
            Message msg = messages[i];
            if (!msg.isSet(Flags.Flag.SEEN)) {
                naoLidos.add(msg);
            }
            if (naoLidos.size() == limite) {
                break;
            }
        }

        return naoLidos;
    }

    public void enviarEmail(String para, String assunto, String corpo) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUser, emailPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailUser));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
        message.setSubject(assunto);
        message.setText(corpo);

        Transport.send(message);
    }


    public String obterConteudoEmail(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    return bodyPart.getContent().toString();
                }
            }
        }
        return "[Conteúdo em formato não suportado]";
    }

    public void fecharConexao() {
        closeFolder(this.inbox);
        closeImapStore(this.store);
    }

    private Store getImapStore() throws MessagingException {
        Session session = Session.getDefaultInstance(this.getImapProperties(), null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", emailUser, emailPassword);
        return store;
    }

    private Properties getImapProperties() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        return props;
    }

    private Folder getFolderFromStore(Store store, String folderName) throws MessagingException {
        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_WRITE);
        return folder;
    }

    private void closeFolder(Folder folder) {
        if (folder != null && folder.isOpen()) {
            try {
                folder.close(true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeImapStore(Store store) {
        if (store != null && store.isConnected()) {
            try {
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}

