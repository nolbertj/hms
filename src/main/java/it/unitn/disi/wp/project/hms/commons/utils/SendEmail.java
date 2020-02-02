/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healtcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.utils;

import it.unitn.disi.wp.project.hms.persistence.entities.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Class that sends emails using Gmail services.
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 12.11.2019
 */
public class SendEmail {

    public enum tipoMail{PWD_NUOVA, RESET_PWD, RIC_NUOVA, RIC_EROGATA, ESA_PRESCRITTO, ESA_EROGATO, PAGAMENTO }

    static public void sendMail(Object user, tipoMail tipo, String pwd){
        final String indirizzoDestinatario=((User) user).getEmail();
        final String host = "smtp.gmail.com";
        String subject = "Nuovo oggetto in HMS";
        String messaggio="";
        final String port = "465";
        final String usernameFrom="HMS.unitn@gmail.com";
        final String password = "ccylgoewmghvetbb";
        Properties props = System.getProperties();

        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.socketFactory.port", port);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.debug", "true");
        Session s = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usernameFrom, password);
            }

        });

        String nomeCognome="";

        if(user instanceof Paziente || user instanceof MedicoBase || user instanceof MedicoSpecialista){
            nomeCognome+=((AbstractPersona) user).getNome() + " " + ((AbstractPersona) user).getCognome();
        }else if(user instanceof Farmacia){
            nomeCognome+=((Farmacia) user).getNome();
        }else if(user instanceof Ssp){
            nomeCognome+=((Ssp) user).getNome();
        }

        messaggio+=" Gentile " + nomeCognome +  ",\n";
        switch(tipo){
            case RESET_PWD:
                subject+=" - richiesta di reset password";
                messaggio+="la Sua password è stata resettata. La nuova password è \n" + pwd +" \n \n" +
                        "Le ricordiamo di cambiare il prima possibile la password per motivi di sicurezza + \n";
                break;
            case PWD_NUOVA:
                subject+=" - nuova password impostata";
                messaggio+="la procedura di cambio password da Lei richiesta è andata a buon fine.\n";
                break;
            case ESA_EROGATO:
                subject+=" - nuovo esame erogato";
                messaggio+="è disponibile un nuovo referto. Acceda subito ad HMS per visualizzarlo e pagare la prestazione.\n";

                break;
            case ESA_PRESCRITTO:
                subject+=" - nuovo esame prescritto";
                messaggio+="è disponibile una nuova ricetta per un esame specialistico. Acceda subito ad HMS per visualizzarla.\n";
                break;
            case RIC_NUOVA:
                subject+=" - nuova ricetta prescritta";
                messaggio+="è disponibile una nuova ricetta farmaceutica. Acceda subito ad HMS per visualizzarla.\n";
                break;
            case RIC_EROGATA:
                subject+=" - nuova ricetta erogata";
                messaggio+="è stata erogata una ricetta farmaceutica. Acceda subito ad HMS per pagare la prestazione.\n";
                break;
            case PAGAMENTO:
                subject+=" - pagamento effettuato";
                messaggio+="Un nuovo pagamento è stato effettuato. Acceda subito ad HMS per visualizzarlo.\n";
            default:

                break;
        }
        messaggio+="\n" + "Buon proseguimento, \n \n" +
                "lo staff di HMS";

        //messaggio = messaggio.replace("\n", "<br>").replace(" ", "&nbsp;");

        try{
            Multipart multipart = new MimeMultipart("alternative");

            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(messaggio);

            multipart.addBodyPart(messageBodyPart1);

            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(usernameFrom, subject));
            msg.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(((User)user).getEmail(), nomeCognome.trim())});
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(multipart);
            Transport.send(msg);


        } catch (MessagingException | UnsupportedEncodingException e) {
            LOG(MessagingException.class, e);
        }


    }
}
