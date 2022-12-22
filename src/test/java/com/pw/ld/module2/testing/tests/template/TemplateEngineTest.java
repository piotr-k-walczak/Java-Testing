package com.pw.ld.module2.testing.tests.template;

import com.pw.ld.module2.testing.Client;
import com.pw.ld.module2.testing.template.MissingPlaceholderException;
import com.pw.ld.module2.testing.template.Template;
import com.pw.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TemplateEngineTest {

    private final TemplateEngine templateEngine = new TemplateEngine();

    @Mock
    private final Client client = new Client("mail@mail.com");

    @TestFactory
    public Collection<DynamicTest> templateEngineTests() {
        Map<String, String> placeholder = new HashMap<>();
        placeholder.put("abcd", "G");
        when(client.getPlaceholders()).thenReturn(placeholder);
        return Arrays.asList(
                DynamicTest.dynamicTest("shouldReturnCorrectMessage", () -> {
                    assertEquals("GG", templateEngine.generateMessage(new Template("G#{abcd}"), client));
                })
        );
    }

    @Test
    @DisabledOnOs(OS.MAC)
    @Tag("TemplateSuite")
    public void shouldThrowMissingPlaceholderValueException() throws MissingPlaceholderException {
        Template template = new Template("Hi #{name}. I am #{age}.' ");
        when(client.getPlaceholders()).thenReturn(new HashMap<>());
        assertThrows(MissingPlaceholderException.class, () -> {
            templateEngine.generateMessage(template, client);
        });
    }
}
