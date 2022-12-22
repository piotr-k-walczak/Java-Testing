package com.pw.ld.module2.testing.bdd;

import com.pw.ld.module2.testing.MailServer;
import com.pw.ld.module2.testing.Messenger;
import com.pw.ld.module2.testing.loader.FileLoader;
import com.pw.ld.module2.testing.template.MissingPlaceholderException;
import com.pw.ld.module2.testing.template.Template;
import com.pw.ld.module2.testing.template.TemplateEngine;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileMessegingStepDefs {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    @TempDir
    File tmpJsonFile;
    private Exception exception;
    private String FILE_PATH = "file.json";

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Given("file does not exist")
    public void file_does_not_exist() {
        FILE_PATH = "no_file.json";
    }

    @Given("file contains 2 correct messages")
    public void file_contains_2_correct_messages() throws IOException {
        FILE_PATH = "file.json";
        String msg = " { \"messages\" : [ {\"address\":\"A\",\"placeholders\":{\"k\":\"v\"}}, {\"address\":\"A\",\"placeholders\":{\"k\":\"v\"}} ] } ";
        File jsonFile = new File(tmpJsonFile, FILE_PATH);
        Files.write(jsonFile.toPath(), Collections.singleton(msg));
    }

    @Given("file contains incorrect messages")
    public void file_contains_incorrect_messages() throws IOException {
        FILE_PATH = "file.json";
        String msg = " { \"messages\" : [ {\"address\":\"A\",\"placeholders\":{\"inc\":\"v\"}}, {\"address\":\"A\",\"placeholders\":{\"inc\":\"v\"}} ] } ";
        File jsonFile = new File(tmpJsonFile, FILE_PATH);
        Files.write(jsonFile.toPath(), Collections.singleton(msg));
    }

    @When("I want to send messages from file")
    public void i_want_to_send_messages_from_file() {
        MailServer mailServer = new MailServer();
        TemplateEngine templateEngine = new TemplateEngine();
        Messenger messenger = new Messenger(mailServer, templateEngine);
        Template template = new Template("Key: #{k}");
        FileLoader fileLoader = new FileLoader(FILE_PATH);
        try {
            fileLoader.run(messenger, template);
        } catch (IOException | ParseException | MissingPlaceholderException e) {
            exception = e;
        }
    }

    @Then("{string} should be thrown")
    public void exception_should_be_thrown(String expectedException) throws ClassNotFoundException {
        assertEquals(expectedException, exception.getClass().getSimpleName());
    }

    @Then("{int} messages should be sent")
    public void messages_should_be_sent(int numberOfMessages) {
        assertEquals(2, Arrays.stream(outContent.toString().split("\n")).count());
    }
}
