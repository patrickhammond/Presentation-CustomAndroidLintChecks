package com.atomicrobot.demo.lint.otto;

import java.util.Arrays;
import java.util.List;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

public class OttoIssueRegistry extends IssueRegistry {
    public OttoIssueRegistry() {
    }

    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(
                ProguardAnnotationDetector.ISSUE
        );
    }
}
