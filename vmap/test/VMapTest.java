package vmap.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;
import vmap.main.Vmap;

public class VMapTest extends TestCase {
	public void testIntegration() throws FileNotFoundException, IOException{
		Vmap vmap = new Vmap();
			
		FileInputStream zip = new FileInputStream(new File("RA_presentation.pptx.zip"));		
		vmap.extractZip(zip, new File("vmap/test/props/"));
		
		FileInputStream extractedFile = new FileInputStream(new File("vmap/test/RA_presentation.pptx"));
		FileInputStream realFile = new FileInputStream(new File("vmap/test/props/RA_presentation.pptx"));
		
		byte[] extractedFileBytes = new byte[extractedFile.available()];
		byte[] realFileBytes = new byte[realFile.available()];
		
		extractedFile.read(extractedFileBytes);		
		realFile.read(realFileBytes);
		
		
		Assert.assertEquals(equalByteStream(extractedFileBytes, realFileBytes), true);
	}
	
	private boolean equalByteStream(byte[] b1, byte[]b2){
		if(b1.length!=b2.length)
			return false;
		for(int i=0; i<b1.length; i++){
			if(b1[i]!=b2[i])
				return false;
		}
		
		return true;
	}
}
