package com.atomicrobot.demo.lint.otto;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

public class ProguardAnnotationDetector extends Detector {
    private static final String PROGUARD_CONFIGURATION = "-keepclassmembers class ** {\n" +
            "    @com.squareup.otto.Subscribe public *;\n" +
            "    @com.squareup.otto.Produce public *;\n" +
            "}";
    private static final String EXPLANATION = "Check the Otto website for the correct ProGuard configuration.";
    private static final int PRIORITY = 6;
    private static final Implementation IMPLEMENTATION = new Implementation(
            ProguardAnnotationDetector.class,
            Scope.PROGUARD_SCOPE);

    public static final Issue ISSUE = Issue.create(
            "OttoProGuard",
            "Otto has ProGuard configuration requirements",
            "Missing Otto Proguard requirements",
            EXPLANATION,
            Category.CORRECTNESS,
            PRIORITY,
            Severity.ERROR,
            IMPLEMENTATION)
            .addMoreInfo("http://square.github.io/otto/");

    @Override
    public void run(@NonNull Context context) {
        String contents = context.getContents();
        if (contents != null) {
            if (context.isEnabled(ISSUE)) {
                checkProguardConfiguration(context, contents);
            }
        }
    }

    private void checkProguardConfiguration(@NonNull Context context, String contents) {
        // FIXME - This is a very naive check as it doesn't account for spacing, order, etc.
        int index = contents.indexOf(PROGUARD_CONFIGURATION);
        if (index == -1) {
            context.report(
                    ISSUE,
                    Location.create(context.file, contents, 1),
                    EXPLANATION,
                    null);
        }
    }
}
