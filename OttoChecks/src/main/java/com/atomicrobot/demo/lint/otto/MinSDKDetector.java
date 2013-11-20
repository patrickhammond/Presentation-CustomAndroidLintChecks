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

// FIXME - I don't actually know if Otto supports versions before 4 but this lint check will
// check it anyway.
public class MinSDKDetector extends Detector implements Detector.XmlScanner {
    private static final int PRIORITY = 6;
    private static final Implementation IMPLEMENTATION = new Implementation(
            MinSDKDetector.class,
            Scope.MANIFEST_SCOPE);

    public static final Issue ISSUE = Issue.create(
            "OttoMinSDK",
            "Otto supports minSdkVersion of 4 or greater.",
            "Otto minimum SDK support",
            "Check the Otto website for details.",
            Category.CORRECTNESS,
            PRIORITY,
            Severity.ERROR,
            IMPLEMENTATION)
            .addMoreInfo("http://square.github.io/otto/");

    @Override
    public void afterCheckFile(@NonNull Context context) {
        // FIXME - With Gradle you define the min sdk in the build.gradle file instead of inside
        // the manifest.
        if (context.isEnabled(ISSUE)) {
            int minSdk = context.getMainProject().getMinSdk();
            if (minSdk < 4) {
                context.report(
                        ISSUE, Location.create(context.file),
                        "Otto doesn't support Android versions before API 4.",
                        null);
            }
        }
    }
}
