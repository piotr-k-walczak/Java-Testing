package com.pw.ld.module2.testing.tests;

import com.pw.ld.module2.testing.Client;
import com.pw.ld.module2.testing.MailServer;
import com.pw.ld.module2.testing.Messenger;
import com.pw.ld.module2.testing.template.MissingPlaceholderException;
import com.pw.ld.module2.testing.template.Template;
import com.pw.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MessengerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void whenMessengerSendMessage_shouldPrintMessageInConsole() throws MissingPlaceholderException {
        TemplateEngine templateEngine = new TemplateEngine();
        MailServer mailServer = new MailServer();
        Messenger messenger = new Messenger(mailServer, templateEngine);

        Client client = new Client("Adres");
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("Param", "Value");
        client.setPlaceholders(placeholders);

        Template template = new Template("V - #{Param}");
        messenger.sendMessage(client, template);

        assertEquals("Message to Adres: V - Value".trim(), outContent.toString().trim());
    }
}
