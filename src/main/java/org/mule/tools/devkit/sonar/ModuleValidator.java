package org.mule.tools.devkit.sonar;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleValidator {

    final private static Logger logger = LoggerFactory.getLogger(ModuleValidator.class);

    private Set<Rule> rules;

    private ModuleValidator() {
    }

    public static ModuleValidator create() throws IOException {
        final ModuleValidator result = new ModuleValidator();
        result.init();

        return result;
    }

    public void init() throws IOException {
        this.rules = RulesFactory.load();
    }

    public void validator(final @NonNull Path rootPath) throws IOException {

        // Process rules ...
        final Stream<Path> filteredHiddenDirs = Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS).filter(childPath -> !childPath.toString().contains("/."));
        final Set<String> collect = filteredHiddenDirs.map(childPath -> {

            final Path relativePath = childPath.relativize(childPath);
            logger.debug("Processing file -> {} {}", rootPath, relativePath);

            // Filter rules ...
            final Stream<Rule> filteredRules = rules.stream().filter(rule -> rule.accepts(rootPath, childPath.relativize(childPath)));

            // Apply rules ..
            return filteredRules.filter(rule -> !rule.verify(rootPath, childPath)).map(rule -> rule.errorMessage(rootPath, childPath)).collect(Collectors.toSet());
        }).filter(set -> !set.isEmpty()).flatMap(Set::stream).collect(Collectors.toSet());

        // Generate report ...
        collect.forEach(System.err::println);
    }

}