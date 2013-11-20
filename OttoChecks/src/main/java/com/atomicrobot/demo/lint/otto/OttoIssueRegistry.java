package com.atomicrobot.demo.lint.otto;

import java.util.Arrays;
import java.util.List;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

@SuppressWarnings("unused")
public class OttoIssueRegistry extends IssueRegistry {
    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(
                ProguardAnnotationDetector.ISSUE,
                AnnotationVisibilityDetector.ISSUE,
                MinSDKDetector.ISSUE
        );
    }
}
