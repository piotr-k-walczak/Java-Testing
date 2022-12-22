package com.pw.ld.module2.testing;

import com.pw.ld.module2.testing.loader.ConsoleLoader;
import com.pw.ld.module2.testing.loader.FileLoader;
import com.pw.ld.module2.testing.loader.Loader;
import com.pw.ld.module2.testing.template.MissingPlaceholderException;
import com.pw.ld.module2.testing.template.Template;
import com.pw.ld.module2.testing.template.TemplateEngine;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Arrays;

public class Application {

    public static void main(String[] args) {

        MailServer mailServer = new MailServer();
        TemplateEngine templateEngine = new TemplateEngine();
        Messenger messenger = new Messenger(mailServer, templateEngine);
        Template template = new Template();

        Loader loader;

        if (Arrays.asList(args).contains("-f")) {
            loader = new FileLoader();
        } else {
            loader = new ConsoleLoader();
        }

        try {
            loader.run(messenger, template);
        } catch (IOException | ParseException | MissingPlaceholderException e) {
            throw new RuntimeException(e);
        }
    }
}
