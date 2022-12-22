package com.pw.ld.module2.testing.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class FileOutputExtension implements AfterAllCallback {

    private final String FILE_PATH;

    public FileOutputExtension(String filepath) {
        FILE_PATH = filepath;
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        File abcd = new File(FILE_PATH);

        String displayName = context.getDisplayName();
        String executionMode = context.getExecutionMode().toString();

        List<String> linesToSave = Arrays.asList("Name: " + displayName + " - Execution mode: " + executionMode);
        Files.write(abcd.toPath(), linesToSave);
    }
}
