package com.pw.ld.module2.testing.tests.template;

import com.pw.ld.module2.testing.template.Template;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith({FileOutputExtension.class})
@ExtendWith(MockitoExtension.class)
public class TemplateTest {

    private static Stream<Arguments> getNumberOfExpectedValuesProvider() {
        return Stream.of(
                Arguments.of(1, "T #{S}"),
                Arguments.of(3, "Test #{F} - #{G} - #{Z}"),
                Arguments.of(2, "T #{S} x #{S}"),
                Arguments.of(1, "#{#{@S}}")
        );
    }

    @Tag("TemplateSuite")
    @ParameterizedTest
    @MethodSource("getNumberOfExpectedValuesProvider")
    void getNumberOfExpectedValues(int expected, String tmplt) {
        Template template = new Template(tmplt);
        assertEquals(expected, template.getNumberOfExpectedValues());
    }

    @Test
    @Tag("TemplateSuite")
    public void getExpectedValues_shouldReturn2Results() {
        Template template = new Template("Hi #{name}. I am #{age}.' ");
        List<String> expected = new ArrayList<>();
        expected.add("#{name}");
        expected.add("#{age}");
        assertEquals(expected, template.getExpectedValues());
    }
}
