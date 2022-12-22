package com.pw.ld.module2.testing.loader;

import com.pw.ld.module2.testing.Client;
import com.pw.ld.module2.testing.Messenger;
import com.pw.ld.module2.testing.template.MissingPlaceholderException;
import com.pw.ld.module2.testing.template.Template;

import java.util.Scanner;

public class ConsoleLoader implements Loader {

    @Override
    public void run(Messenger messenger, Template template) throws MissingPlaceholderException {
        Scanner reader = new Scanner(System.in);

        while (true) {
            System.out.println("Enter clients address (or EXIT to close the app): ");
            String inputValue = reader.next();

            if (inputValue.equals("EXIT")) {
                break;
            } else if (!inputValue.isEmpty()) {
                Client client = new Client(inputValue);

                for (String parameterName : template.getExpectedValues()) {
                    System.out.println("Enter value for parameter - " + parameterName);
                    inputValue = reader.next();
                    client.addPlaceholder(parameterName, inputValue);
                }

                messenger.sendMessage(client, template);
            }
        }
    }
}
