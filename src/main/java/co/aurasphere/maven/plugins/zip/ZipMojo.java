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