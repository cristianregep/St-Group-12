package vmap.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;
import vmap.main.Vmap;

public class VMapTest extends TestCase {
	public void testIntegration() throws FileNotFoundException, IOException {
		Vmap vmap = new Vmap();

		// test extract zip
		FileInputStream zip = new FileInputStream(new File(
				"argo.user.properties.zip"));
		vmap.extractZip(zip, new File("vmap/test/props/"));
		FileInputStream extractedFile = new FileInputStream(new File(
				"vmap/test/props/argo.user.properties"));
		FileInputStream realFile = new FileInputStream(new File(
				"argo.user.properties"));
		byte[] extractedFileBytes = new byte[extractedFile.available()];
		byte[] realFileBytes = new byte[realFile.available()];
		extractedFile.read(extractedFileBytes);
		realFile.read(realFileBytes);
		Assert.assertEquals(equalByteStream(extractedFileBytes, realFileBytes),
				true);
		
		// test isApplet
		Assert.assertEquals(vmap.isApplet(),false);
		Assert.assertEquals(vmap.getPatternsFile(),vmap.patternsFile);
		Assert.assertEquals(vmap.getViewport(),vmap.scrollPane.getViewport());
		Assert.assertEquals(vmap.getVmapVersion(),vmap.version);
		Assert.assertEquals(vmap.getWinHeight(),vmap.getRootPane().getHeight());
		Assert.assertEquals(vmap.getWinWidth(),vmap.getRootPane().getWidth());
		Assert.assertEquals(vmap.getWinState(), vmap.getExtendedState());
		Assert.assertEquals(vmap.getView(), vmap.getController().getView());
		vmap.setView(vmap.getView());
		Assert.assertEquals(vmap.getView(), vmap.scrollPane.getViewport().getView());
		

	}

	private boolean equalByteStream(byte[] b1, byte[] b2) {
		if (b1.length != b2.length)
			return false;
		for (int i = 0; i < b1.length; i++) {
			if (b1[i] != b2[i])
				return false;
		}

		return true;
	}


	public void testTranspose() {
		Vmap vm = new Vmap();

		// char not in original word, no transposition should take place
		assertEquals("Output should equal input", "words",
				vm.transpose("words", 'n', "sdrow"));
		assertEquals("Output should equal input", "controller",
				vm.transpose("controller", 'a', "t"));

		// All instances of 'i' should be changed to 'a'
		assertEquals("Instances of 'i' changed for 'a'", "massassappa",
				vm.transpose("mississippi", 'i', "a"));

		// All instances of l should be changed to 'yi'
		assertEquals("Instances of 'l' changed for 'yi'", "wooyiyien",
				vm.transpose("woollen", 'l', "yi"));
	}
	
	// This test required moving 'browser_command' variable to be defined globally.
	// Test fails as default browser on Linux is Firefox and for some reason the
	// method returns terminal command 'mozilla www.bbc.co.uk/iplayer' not
	// 'firefox www.bbc.co.uk/iplayer' 
	public void testOpenDocument() throws Exception {
		Vmap vm = new Vmap();
		URL url = new URL("http://www.bbc.co.uk/iplayer/");
		vm.openDocument(url);
		assertEquals("firefox http://www.bbc.co.uk/iplayer/", vm.browser_command);
	}
	
	
}