package co.aurasphere.maven.plugins.zip;

import java.io.File;
import java.io.IOException;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.artifact.ProjectArtifact;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.internal.util.reflection.Whitebox;
import org.zeroturnaround.zip.ZipException;

/**
 * Tests for {@link ZipMojo}.
 * 
 * @author Donato Rimenti
 *
 */
public class ZipMojoTest {

	/**
	 * Plugin to test.
	 */
	private ZipMojo zipMojo = new ZipMojo();

	/**
	 * Temporary folder for the zip plugin.
	 */
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	/**
	 * Tests {@link ZipMojo#execute()} by building a zip file with an input
	 * directory that doesn't exist.
	 * 
	 * @throws MojoExecutionException if an error occurs during the plugin execution
	 * @throws IOException            if an error occurs while creating temporary
	 *                                directories
	 */
	@Test(expected = MojoExecutionException.class)
	public void testZipWithNonExistentInputDirectory() throws MojoExecutionException, IOException {
		// Mocks the internal state.
		Whitebox.setInternalState(zipMojo, "inputDirectory", new File(""));
		Whitebox.setInternalState(zipMojo, "outputDirectory", tempFolder.newFolder());

		// Executes the plugin.
		zipMojo.execute();
	}

	/**
	 * Tests {@link ZipMojo#execute()} by building a zip file with an output
	 * directory that doesn't exist.
	 * 
	 * @throws MojoExecutionException if an error occurs during the plugin execution
	 * @throws IOException            if an error occurs while creating temporary
	 *                                directories
	 */
	@Test(expected = ZipException.class)
	public void testZipWithNonExistentOutputDirectory() throws MojoExecutionException, IOException {
		// Mocks the internal state.
		Whitebox.setInternalState(zipMojo, "inputDirectory", tempFolder.newFolder());
		Whitebox.setInternalState(zipMojo, "outputDirectory", new File(tempFolder.getRoot(), "subfolder"));

		// Executes the plugin.
		zipMojo.execute();
	}

	/**
	 * Tests {@link ZipMojo#execute()} by building a zip file with an output
	 * directory that doesn't exist and can't be created.
	 * 
	 * @throws MojoExecutionException if an error occurs during the plugin execution
	 * @throws IOException            if an error occurs while creating temporary
	 *                                directories
	 */
	@Test(expected = MojoExecutionException.class)
	public void testZipErrorCreatingOutputDirectory() throws MojoExecutionException, IOException {
		// Mocks the internal state.
		Whitebox.setInternalState(zipMojo, "inputDirectory", tempFolder.newFolder());
		Whitebox.setInternalState(zipMojo, "outputDirectory", new File(""));

		// Executes the plugin.
		zipMojo.execute();
	}

	/**
	 * Tests {@link ZipMojo#execute()} by building a zip file without errors.
	 * 
	 * @throws MojoExecutionException if an error occurs during the plugin execution
	 * @throws IOException            if an error occurs while creating temporary
	 *                                directories
	 */
	@Test
	public void testZipOk() throws MojoExecutionException, IOException {
		// Creates a temporary file to zip. This is an assumption to the test.
		File inputFolder = tempFolder.newFolder();
		File zipFile = new File(inputFolder, "zipFile");
		Assume.assumeTrue(zipFile.createNewFile());

		// Mocks the internal state.
		MavenProject project = new MavenProject();
		Artifact artifact = new ProjectArtifact(project);
		project.setArtifact(artifact);
		Whitebox.setInternalState(zipMojo, "inputDirectory", inputFolder);
		Whitebox.setInternalState(zipMojo, "outputDirectory", tempFolder.getRoot());
		Whitebox.setInternalState(zipMojo, "project", project);

		// Executes the plugin.
		zipMojo.execute();

		// Checks that the zip has been succesfully created.
		Assert.assertNotNull(artifact.getFile());
	}

}