package org.mule.tools.devkit.sonar;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

public class Main {

    static public void main(String argv[]) throws IOException {

        final ConnectorModuleValidator validator = ConnectorModuleValidator.create(Paths.get("."));

        final String arg = argv.length == 0 ? "-h" : argv[0];
        switch (arg) {
            case "-v": {
                final Set<Rule.Documentation> docs = validator.rulesDoc();
                System.out.println("Supported certification rules:\n");
                docs.forEach(doc -> System.out.printf("\t-> %s - %s\n", doc.getId(), doc.getBrief()));
                break;
            }
            case "-h": {
                System.out.println("Invalid argument arguments. Use ['-v']");
                break;
            }

        }

    }
}
