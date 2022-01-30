import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class to read a text file  
 * and give the contents of that file to the user in one massive string.
 * The class strips blank spaces and most punctuation. All characters are
 * downcased
 * Created: March 21, 2021
 * @author gtowell
 */
public class File2String {

    /** The name of the file to be read. */
    private String fileName;

    
    /** 
     * Initialize the class.
     * @param filename the name of the file containing the text to be read
     * @throws FileNotFoundException if the file does not exist
     */
    public File2String(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        if (!f.exists()) {
            throw new FileNotFoundException(fileName + " does not exist");
        }
        this.fileName = fileName;
    }
 
    /**
     * Get the entire contents of the file as one big string
     * @return the entire contents of the file
     * @throws IOException if somenthing went wrong while reading the file
     */
    public String getString() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {
            StringBuffer sb = new StringBuffer();
            while (br.ready()) {
                String line = br.readLine().trim().toLowerCase().replaceAll("[\\.\\'?!,\\-\\n\\\"]", "").replace(" ", "");
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fnf) {
            System.err.println("This should never happen because the file was confirmed to exist earlier");
        }
        return null;
    }
    public static void main(String[] args) {
        try {
            File2String f2s = new File2String("testpals.txt");
            System.out.println(f2s.getString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
