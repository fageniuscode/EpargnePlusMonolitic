package com.fageniuscode.epargneplus.api.services;

import com.sun.mail.smtp.SMTPTransport;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static com.fageniuscode.epargneplus.api.constants.EmailConstant.*;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;

@Service
public class EmailService {

    /***
     * envoie un e-mail contenant un nouveau mot de passe à une adresse e-mail
     * spécifiée en utilisant le serveur SMTP de Google (Gmail).
     * Les paramètres nécessaires pour envoyer l'e-mail sont le prénom de l'utilisateur,
     * le nouveau mot de passe et l'adresse e-mail de l'utilisateur.
     * Plus précisément, la méthode sendNewPasswordEmail crée un objet Message contenant le contenu
     * de l'e-mail à envoyer, en utilisant la méthode createEmail.
     * Ensuite, elle établit une connexion SMTP avec le serveur SMTP de Gmail en utilisant les informations
     * d'authentification fournies (nom d'utilisateur et mot de passe). Enfin, elle envoie l'e-mail en
     * utilisant la méthode sendMessage de l'objet SMTPTransport, puis ferme la connexion avec la méthode close.
     * @param firstName
     * @param password
     * @param email
     * @throws MessagingException
     */
    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException {
        Message message = createEmail(firstName, password, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    /***
     * crée et retourne un objet Message représentant un email à envoyer. Les paramètres de l'email incluent
     * le prénom du destinataire, le mot de passe de son nouveau compte et son adresse email.
     * Le contenu de l'email comprend un message de bienvenue personnalisé pour le destinataire, avec son nom, son mot de passe et une signature de l'équipe de support.
     * L'email est envoyé à l'adresse email fournie en tant que paramètre, et une copie est envoyée à l'adresse email définie par la constante CC_EMAIL.
     * La méthode utilise la classe MimeMessage pour construire l'email et la session de courrier électronique définie dans getEmailSession() pour envoyer l'email.
     * Si une exception de type MessagingException est levée pendant l'envoi de l'email, elle est renvoyée à l'appelant.
     * @param firstName
     * @param password
     * @param email
     * @return
     * @throws MessagingException
     */
    private Message createEmail(String firstName, String password, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Hello " + firstName + ", \n \n Your new account password is: " + password + "\n \n The Support Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    /***
     * crée une session d'e-mail en utilisant les propriétés définies dans les objets de la classe Properties. La session est créée en utilisant les informations de connexion pour le serveur SMTP de Gmail.
     * La première ligne crée un nouvel objet Properties qui stocke les propriétés système actuelles.
     * La deuxième ligne définit l'hôte SMTP à utiliser en tant que serveur SMTP de Gmail.
     * La troisième ligne active l'authentification SMTP.
     * La quatrième ligne définit le port SMTP à utiliser en tant que port par défaut.
     * La cinquième ligne active la prise en charge de STARTTLS (Transport Layer Security) pour la connexion sécurisée.
     * La dernière ligne crée une nouvelle instance de la classe Session en utilisant les propriétés et un gestionnaire d'authentification nul (ce qui signifie que l'authentification par défaut sera utilisée).
     * @return
     */
    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }

}
