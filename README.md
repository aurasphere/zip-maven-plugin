# zip-maven-plugin
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
			<zipName>my-zip-name</zipName>
	</configuration>
</plugin>
```

2. Set the packaging type to zip

`<packaging>zip</packaging>`
 
3. The configuration element is optional. If not supplied, the default values are:

- **inputDirectory**: `${project.build.outputDirectory}` (the target/classes directory)
- **outputDirectory**: `${project.build.directory}` (the target directory)
- **zipName**: `${project.build.finalName}` (<project-name>-<project-version>)
