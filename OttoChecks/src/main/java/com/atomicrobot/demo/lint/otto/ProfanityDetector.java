package com.atomicrobot.demo.lint.otto;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.ResourceXmlDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.w3c.dom.Element;

import java.util.Collection;
import java.util.Collections;

/**
 * Not an Otto check...used to demonstrate checking resources files.
 */
public class ProfanityDetector extends ResourceXmlDetector {
    private static final int PRIORITY = 6;
    private static final Implementation IMPLEMENTATION = new Implementation(
            ProfanityDetector.class,
            Scope.RESOURCE_FILE_SCOPE);

    public static final Issue ISSUE = Issue.create(
            "Profanity",
            "Profanity shouldn't be used.",
            "Bad words",
            "If it is on urbandictionary.com, it probably shouldn't be in the code.",
            Category.CORRECTNESS,
            PRIORITY,
            Severity.WARNING,
            IMPLEMENTATION)
            .addMoreInfo("urbandictionary.com");

    @Override
    public Collection<String> getApplicableElements() {
        return Collections.singletonList("string");
    }

    @Override
    public void visitElement(XmlContext context, Element element) {
        if ("iOS".equals(element.getTextContent())) {
            context.report(
                    ISSUE,
                    element,
                    context.getLocation(element),
                    "iOS is a bad word... ;-)",
                    null);
        }
    }
}


