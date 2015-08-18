# AnyPoint Connectors Certification Verifier

The objective of this project is to automate most of the current certification rules
http://mulesoft.github.io/connector-certification-docs/.

* type: Rule id to be used. The following types are currently supported: source.pom, source.xml,source.java, structure.
* id: Unique id assigned to this rule.
* severity: Criticity of the rule. It can be 'critical', 'major' or 'minor'.
* brief: Short description of the objective of the rule. 
* description: Full description of the objective of the rule.
* section: Section within the certification documentation where this rule is declared.
* accept: If the expression is satisfied over the processed file, the **assert** expression will be executed. The type of **accept** expressions depends on the rule type. 
* assert: Assert expression to be verified over the file being processed. The type of expression depends on the rule type.
 
## Features

There are 4 types of different rules supported. New rules can be configured in the file src/main/resources/rules.json. For each rule, the following attributes must be declared:

### Rule Type 'source.pom'

This rules can be configured with a XPath expression that needs to be satisfied. Both assert and accept expresion are XPath expressions. **accept** and **assert**  must be a valid XPath expressions.

```json
{
      "type": "source.pom",
      "id": "dist_management_other",
      "severity": "critical",
      "brief": "pom.xml distributionManagement must be properly configured.",
      "description": "Premium and Select connectors define 'http://repository-master.mulesoft.org/releases/' as repository.",
      "section": "3.3 Code Compliance.",
      "accept": "not(/pom:project/pom:properties/pom:category[text()='Premium' or text()='Select'])",
      "assert": "/pom:project/pom:distributionManagement/pom:repository/pom:id[text()='mulesoft-releases'] and /pom:project/pom:distributionManagement/pom:repository/pom:url[text()='http://repository-master.mulesoft.org/releases/']"
}
```

### Rule Type 'source.xml

### Rule Type 'source.java'

### Rule Type 'structure'


## Usage

Execution could be started executing:

```
 analize.sh connector-module-path
```

## Pendings

* Support disabling errors per project. 
* Improve documentation
* Complete structure checks
* Integrate with Sonar 
* Create a Maven Mojo
* Extend support of expressions variables such as connector_name and category



