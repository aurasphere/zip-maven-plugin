/*
 * MIT License
 *
 * Copyright (c) 2020 Donato Rimenti
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.aurasphere.maven.plugins.zip;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.zeroturnaround.zip.ZipUtil;

/**
 * Generates a Zip file as build output. This plugin will run automatically if
 * you set your project package type to zip after importing this plugin with the
 * "extensions" option set to true.
 *
 * @author Donato Rimenti
 * 
 */
@Mojo(name = "zip", defaultPhase = LifecyclePhase.PACKAGE)
public class ZipMojo extends AbstractMojo {

	/**
	 * The current project.
	 */
	@Component
	private MavenProject project;

	/**
	 * Input directory which contains the files that will zipped. Defaults to the
	 * build output directory.
	 */
	@Parameter(defaultValue = "${project.build.outputDirectory}", property = "inputDir")
	private File inputDirectory;

	/**
	 * Directory which will contain the generated zip file. Defaults to the Maven
	 * build directory.
	 */
	@Parameter(defaultValue = "${project.build.directory}", property = "outputDir")
	private File outputDirectory;

	/**
	 * Final name of the zip file. Defaults to the build final name attribute.
	 */
	@Parameter(defaultValue = "${project.build.finalName}", property = "zipName")
	private String zipName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException {
		getLog().info("Zipping content of directory [" + inputDirectory.getAbsolutePath() + "] to ["
				+ outputDirectory.getAbsolutePath() + File.pathSeparator + zipName + "]");

		// The input directory must exist.
		if (!inputDirectory.exists()) {
			getLog().error("Input directory [" + inputDirectory.getAbsolutePath() + "] does not exist!");
			throw new MojoExecutionException(
					"Input directory [" + inputDirectory.getAbsolutePath() + "] does not exist!");
		}

		// If the output directory does not exist will be created.
		if (!outputDirectory.exists()) {
			boolean success = outputDirectory.mkdirs();
			if (!success) {
				getLog().error("Error while creating the output directory: " + outputDirectory.getAbsolutePath());
				throw new MojoExecutionException(
						"Error while creating the output directory: " + outputDirectory.getAbsolutePath());
			}
		}

		// Builds the actual zip file.
		File artifact = new File(outputDirectory, zipName + ".zip");
		ZipUtil.pack(inputDirectory, artifact);

		// Sets the artifact file inside the project.
		project.getArtifact().setFile(artifact);
	}
}