package com.pw.ld.module2.testing.loader;

import com.pw.ld.module2.testing.Client;
import com.pw.ld.module2.testing.Messenger;
import com.pw.ld.module2.testing.template.MissingPlaceholderException;
import com.pw.ld.module2.testing.template.Template;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements Loader {

    public static String filePath;

    JSONParser parser = new JSONParser();

    public FileLoader(){
        filePath = "/";
    }

    public FileLoader(String path){
        filePath = path;
    }

    @Override
    public void run(Messenger messenger, Template template) throws IOException, ParseException, MissingPlaceholderException {
        List<Message> messages = loadMessagesFromFile(filePath);
        for(Message message : messages) {
            Client client = new Client(message.getAddress());
            client.setPlaceholders(message.getPlaceholders());
            messenger.sendMessage(client, template);
        }
    }

    public List<Message> loadMessagesFromFile(String path) throws IOException, ParseException {
        String jsonString = readFileAsString(path);
        return loadMessagesFromString(jsonString);
    }

    public List<Message> loadMessagesFromString(String json) throws ParseException {
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONArray jsonArray = (JSONArray) jsonObject.get("messages");
        List<Message> messages = new ArrayList<>();
        for (Object m : jsonArray.toArray()) {
            messages.add(loadMessageFromString(m.toString()));
        }
        return messages;
    }

    public Message loadMessageFromString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Message.class);
    }

    public String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
