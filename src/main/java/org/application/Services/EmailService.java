package org.application.Services;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailService {

    private final String emailUser;
    private final String emailPassword;


    public EmailService(String emailUser, String emailPassword) {
        this.emailUser = emailUser;
        this.emailPassword = emailPassword;
    }

    public List<Message> ListarEmailNaoLidos(int limite) throws MessagingException {
        Store store = null;
        Folder inbox = null;

        store = getImapStore();
        inbox = getFolderFromStore(store, "INBOX");

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

        try {

        }catch ()
        return naoLidos;
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
}

