package com.pw.ld.module2.testing.loader;

import com.pw.ld.module2.testing.Messenger;
import com.pw.ld.module2.testing.template.MissingPlaceholderException;
import com.pw.ld.module2.testing.template.Template;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface Loader {

    void run(Messenger messenger, Template template) throws IOException, ParseException, MissingPlaceholderException;
}
