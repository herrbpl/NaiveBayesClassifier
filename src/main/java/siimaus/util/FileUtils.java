package siimaus.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to contain custom file operations.
 * @author siimaus
 *
 */
public class FileUtils {

	/**
	 * Temporary method to load file quickly. 
	 * @param filename
	 * @return
	 */
	public static String fromFileText(String fileName)  {
		String line = null;
		String text = "";		
		try {
			FileReader fileReader = new FileReader(fileName);
	
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);	
			while ((line = bufferedReader.readLine()) != null) {
				text += line+"\n";
			}
	
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;		
		}
				
		return text;
	}

}
