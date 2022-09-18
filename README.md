[![Travis](https://img.shields.io/travis/aurasphere/zip-maven-plugin.svg)](https://travis-ci.org/aurasphere/zip-maven-plugin)
[![Maven Central](https://img.shields.io/maven-central/v/co.aurasphere.maven.plugins/zip-maven-plugin.svg)](https://search.maven.org/artifact/co.aurasphere.maven.plugins/zip-maven-plugin/1.0.0/jar)
[![Javadocs](http://javadoc.io/badge/co.aurasphere.maven.plugins/zip-maven-plugin.svg)](http://javadoc.io/doc/co.aurasphere.maven.plugins/zip-maven-plugin)
[![Maintainability](https://api.codeclimate.com/v1/badges/43d564cf9ee6e93d8391/maintainability)](https://codeclimate.com/github/aurasphere/zip-maven-plugin/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/43d564cf9ee6e93d8391/test_coverage)](https://codeclimate.com/github/aurasphere/zip-maven-plugin/test_coverage)
[![Join the chat at https://gitter.im/zip-maven-plugin/community](https://badges.gitter.im/zip-maven-plugin/community.svg)](https://gitter.im/zip-maven-plugin/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Donate](https://img.shields.io/badge/Donate-PayPal-orange.svg)](https://www.paypal.com/donate/?cmd=_donations&business=8UK2BZP2K8NSS)

# Zip Maven Plugin

Simple Maven plugin for generating ZIP files.

## Usage

#### NOTE: requires Maven >= 3.5.2.

1. Add the plugin to your pom.xml:

```
<plugin>
  <groupId>co.aurasphere.maven.plugins</groupId>
  <artifactId>zip-maven-plugin</artifactId>
  <version>1.0.0</version>
  <extensions>true</extensions>
  <configuration>
    <inputDirectory>${project.basedir}/src/main/java</inputDirectory>
    <outputDirectory>C:/mydir</outputDirectory>
    <preserveRoot>true</preserveRoot>
    <rootName>my-root-name</rootName>
    <zipName>my-zip-name</zipName>
  </configuration>
</plugin>
```

2. Set the packaging type to zip

`<packaging>zip</packaging>`
 
3. The configuration element is optional. If not supplied, the default values are:

- **inputDirectory**: `${project.build.outputDirectory}` (your project target/classes directory)
- **outputDirectory**: `${project.build.directory}` (your project target directory)
- **preserveRoot**: `false` (if true, will use the input directory name as the root of the zip, if false will use [project-name]-[project-version])
- **rootName**: `${project.build.finalName}` (the name to use as the root of the zip, if provided it will cause `preserveRoot` element to be ignored)
- **zipName**: `${project.build.finalName}` ([project-name]-[project-version])

<sub>Copyright (c) 2019 Donato Rimenti</sub>
