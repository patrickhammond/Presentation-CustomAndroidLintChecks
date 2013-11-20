package com.atomicrobot.demo.lint.otto;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.ast.Annotation;
import lombok.ast.AstVisitor;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.Modifiers;
import lombok.ast.Node;

public class AnnotationVisibilityDetector extends Detector implements Detector.JavaScanner {

    private static final int PRIORITY = 6;
    private static final Implementation IMPLEMENTATION = new Implementation(
            AnnotationVisibilityDetector.class,
            Scope.JAVA_FILE_SCOPE);

    public static final Issue ISSUE = Issue.create(
            "OttoAnnotationVisibility",
            "Methods tagged with @Subscribe and @Produce must have public visibility",
            "@Subscribe or @Produce method visiblity",
            "Check the Otto website for details.",
            Category.CORRECTNESS,
            PRIORITY,
            Severity.ERROR,
            IMPLEMENTATION)
            .addMoreInfo("http://square.github.io/otto/");

    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return Collections.<Class<? extends Node>>singletonList(Annotation.class);
    }

    @Override
    public AstVisitor createJavaVisitor(@NonNull JavaContext context) {
        return new AnnotationChecker(context);
    }

    private static class AnnotationChecker extends ForwardingAstVisitor {
        private final JavaContext context;
        private Set<String> matchingAnnotationTypeNames;

        public AnnotationChecker(JavaContext context) {
            this.context = context;
            matchingAnnotationTypeNames = new HashSet<String>();
            matchingAnnotationTypeNames.add("Produce");
            matchingAnnotationTypeNames.add("com.squareup.otto.Produce");
            matchingAnnotationTypeNames.add("Subscribe");
            matchingAnnotationTypeNames.add("com.squareup.otto.Subscribe");
        }

        @Override
        public boolean visitAnnotation(Annotation node) {
            if (context.isEnabled(ISSUE)) {
                String type = node.astAnnotationTypeReference().getTypeName();
                if (matchingAnnotationTypeNames.contains(type)) {
                    Modifiers modifiers = (Modifiers) node.getParent();
                    if (!modifiers.isPublic()) {
                        context.report(
                                ISSUE,
                                context.getLocation(node),
                                "Method is not public",
                                null);
                    }
                }
            }

            return super.visitAnnotation(node);
        }
    }
}
