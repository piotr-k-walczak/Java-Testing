package com.pw.ld.module2.testing.suite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages("com.epam.ld.module2.testing.template")
@IncludeTags("TemplateSuite")
@Suite
public class TemplateTestSuite {

}
