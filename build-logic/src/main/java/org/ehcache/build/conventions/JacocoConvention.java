package org.ehcache.build.conventions;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.testing.Test;
import org.gradle.testing.jacoco.plugins.JacocoPlugin;
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension;
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension;
import org.gradle.testing.jacoco.tasks.JacocoReport;

public class JacocoConvention implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.getPlugins().apply(JacocoPlugin.class);

    project.getExtensions().configure(JacocoPluginExtension.class, jacoco -> {
      jacoco.setToolVersion("0.8.8");
    });

    project.getTasks().withType(JacocoReport.class).configureEach(jacocoReport -> {
      jacocoReport.getReports().configureEach(report -> {
        report.getRequired().set(false);
      });
    });

    project.getTasks().withType(Test.class).configureEach(test -> {
      test.getExtensions().configure(JacocoTaskExtension.class, jacoco -> {
        jacoco.getExcludes().add("org.terracotta.tripwire.*");
      });
    });
  }
}
