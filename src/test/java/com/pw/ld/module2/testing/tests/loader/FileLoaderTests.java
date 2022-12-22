package com.pw.ld.module2.testing.tests.loader;

import com.pw.ld.module2.testing.MailServer;
import com.pw.ld.module2.testing.Messenger;
import com.pw.ld.module2.testing.loader.FileLoader;
import com.pw.ld.module2.testing.loader.Message;
import com.pw.ld.module2.testing.template.Template;
import com.pw.ld.module2.testing.template.TemplateEngine;
import com.google.gson.Gson;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class FileLoaderTests {

    MailServer mailServer = new MailServer();

    TemplateEngine templateEngine = new TemplateEngine();

    Messenger messenger = new Messenger(mailServer, templateEngine);

    Template template = new Template();

    @TempDir
    File tmpJsonFile;

    FileLoader fileLoader = new FileLoader();

    @Test
    void givenJson_shouldReturnCorrectMessage() {
        Map<String, String> pl = new HashMap<>();
        pl.put("k", "v");
        Message m1 = new Message();
        m1.setAddress("A");
        m1.setPlaceholders(pl);
        Gson gson = new Gson();
        gson.toJson(m1);

        String msg = " {\"address\":\"A\",\"placeholders\":{\"k\":\"v\"}} ";
        Message message = fileLoader.loadMessageFromString(msg);
        assertAll(
                () -> assertEquals(message.getAddress(), "A"),
                () -> assertTrue(message.getPlaceholders().containsKey("k"))
        );
    }

    @Test
    void given2ElementJsonArray_shouldCall_loadMsgFromStringTwice() throws ParseException {
        String msg = " { \"messages\" : [ {\"address\":\"A\",\"placeholders\":{\"k\":\"v\"}}, {\"address\":\"A\",\"placeholders\":{\"k\":\"v\"}} ] } ";
        FileLoader fileLoaderSpy = Mockito.spy(fileLoader);
        fileLoaderSpy.loadMessagesFromString(msg);
        Mockito.verify(fileLoaderSpy, Mockito.times(2)).loadMessageFromString(anyString());
    }

    @Test
    void given2ElementJsonArray_shouldReturn2ElementList() throws ParseException {
        String msg = " { \"messages\" : [ {\"address\":\"A\",\"placeholders\":{\"k\":\"v\"}}, {\"address\":\"A\",\"placeholders\":{\"k\":\"v\"}} ] } ";
        FileLoader fileLoaderSpy = Mockito.spy(fileLoader);
        List<Message> messageList = fileLoaderSpy.loadMessagesFromString(msg);
        assertAll(
                () -> assertEquals(2, messageList.size()),
                () -> assertEquals("A", messageList.get(1).getAddress())
        );
    }

    @Test
    void givenPath_shouldLoad2MessagesFromFile() throws IOException, ParseException {
        String path = "x.json";
        prepareJsonFile(path);
        List<Message> messageList = fileLoader.loadMessagesFromFile(path);

        assertAll(
                () -> assertTrue((new File(path)).exists()),
                () -> assertEquals(2, messageList.size()),
                () -> assertEquals("A", messageList.get(1).getAddress())
        );
    }

    void prepareJsonFile(String path) throws IOException {
        String msg = " { \"messages\" : [ {\"address\":\"A\",\"placeholders\":{\"k\":\"v\"}}, {\"address\":\"A\",\"placeholders\":{\"k\":\"v\"}} ] } ";
        tmpJsonFile = new File(path);
        Files.write(tmpJsonFile.toPath(), Collections.singleton(msg));
    }
}
