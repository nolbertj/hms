/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;

/**
 * Classe contenete metodi utili per lo sviluppo di questa Web-App
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 29.10.2019
 */
public final class Utils {

    private Utils() {
        super();
    }

    /**
     * @return {@code null} if string is empty, has length = 0 or equals "", false otherwise
     *
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @since 29.10.2019
     */
    static public boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty() || string.equalsIgnoreCase("");
    }

    /**
     * Verifica se un parametro ricevuto dal client è un parametro valido.<br>
     * In particolare verifica che non sia {@code undefined} o {@code [Object object]}
     * @return {@code true} se il parametro è valido, {@code false} altrimenti.
     */
    static public boolean isValidParam(String param) {
        return  (param!=null &&
                (!param.equalsIgnoreCase("undefined")) &&
                (!param.equalsIgnoreCase("[Object object]"))
        );
    }

    /**
     * Remove the specified {@code word} from {@code string} and return latter
     *
     * @param string the string that containes the {@code word}
     * @param word the word that must be removed from {@code string}
     * @return {@code null} if string is empty or not contains {@code word}, {@code string} without the {@code word} otherwise
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @since 30.10.2019
     */
    static public String removeWord(String string, String word) {
        String res = null;
        if(!isNullOrEmpty(string) && !isNullOrEmpty(word)){
            if(string.contains(word)){
                String case1 = word + " ";

                // To cover the case if the word is at the BEGINNING of the string or anywhere in the MIDDLE
                string = string.replaceAll(case1, "");

                // To cover the edge case if the word is at the END of the string
                String case2 = " " + word;
                string = string.replaceAll(case2, "");

                // To cover simple case
                string = string.replaceAll(word, "");

                res = string;
            }
        }
        return res;
    }

    static public String getDateAsString(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        if(date != null)
            return sdf.format(date);
        else return "";
    }
    static public String getTimestampAsString(Timestamp timestamp){
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if(timestamp != null)
            return sdf.format(timestamp);
        else return "";
    }

    /**
     * Rendirizza alla pagina in qui ha preso origine il metodo POST.
     *
     * @param request the string that contains the {@code word}
     * @param response the word that must be removed from {@code string}
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @since 1.11.2019
     */
    static public void redirectOrigin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String URI_THAT_SEND_POST_REQUEST = "";

        try {
            URI_THAT_SEND_POST_REQUEST = new URI(request.getHeader("referer")).getPath();
            URI_THAT_SEND_POST_REQUEST = removeWord(URI_THAT_SEND_POST_REQUEST, Attr.CP);
        } catch (URISyntaxException ex) {
            throw new ServletException(ex);
        }
        LOG(request.getServletContext(), "response.sendRedirect() to: " + URI_THAT_SEND_POST_REQUEST);
        response.sendRedirect(response.encodeRedirectURL(URI_THAT_SEND_POST_REQUEST)); //reindirizzo l'utente alla pagina da cui proviene
    }

    /**
     * Verifica se un file, supposta un immagine, sia effettivamente tale.
     *
     * @param file file da verificare
     * @return {@code true} se il {@code file} è un immagine, {@code false} altrimenti.
     * @throws IOException se il {@code file} non è leggibile
     */
    static public boolean isImage(final InputStream file) throws IOException {
        return (ImageIO.read(file) != null); //è sufficiente verificare se posso leggerlo con ImageIO
    }


    /**
     * Stampa in console tutti i metadata di un file
     *
     * @param file file (e.g. image, video or audio file)
     * @author Nolbert Juarez &lt; nolbert dot juarezvera at studenti dot unitn dot it
    */
    private void printMetadata(InputStream file) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        LOG(this,"File metadata:");
        for (Directory dir : metadata.getDirectories()) {
            String out = "\t\t";
            for (Tag tag : dir.getTags()) {
                out += dir.getName() + " - " + tag.getTagName() + " = " + tag.getDescription() + "\n";
            }
            LOG(this,out);
            if (dir.hasErrors()) {
                for (String error : dir.getErrors()) {
                    out += error;
                }
                LOG(this,LEVEL.ERROR,out);
            }
        }
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     * @author Victor Tatai
     */
    private static Set findClasses(File directory, String packageName) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     * @author Victor Tatai
     */
    public static Set<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = classLoader.getResources(path);
        Set<File> dirs = new HashSet<>();
        while (resources.hasMoreElements()) {
            URL resource = (URL)resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        Set<Class<?>> classes = new HashSet();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    /**
     * Prints in the stdout all parameters presents in an {@link HttpServletRequest} object
     * with patten {@code [key,value]}
     * @
     * @param request
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     */
    static public void printRequestParameters(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            LOG(Utils.class,"["+paramName+": "+request.getParameter(paramName) + "]");
        }
    }

    /**
     * Read the content of a file located in the <i>resources</i> directory of the project.
     * <br><br>
     * If the file is located into a sub-directory, then write the path with a slash,
     * e.g. <i>/properties/configg.properties</i>
     * <br><br>
     * <i>If the "resources" directory isn't present in the project, then create it.
     * The content will be:
     *  <ul> src/main/
     *      <li>java</li>
     *      <li>webapp</li>
     *      <li>resources</li>
     *  </ul>
     * </i>
     *
     * @param filename file name or file path
     * @return content of the file into String encoded in {@link StandardCharsets#UTF_8},
     * {@code null} if the file was not found
     *
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @see ClassLoader#getResourceAsStream
     */
    static public String getStringFromFile(String filename) {
        InputStream input = null;
        File file = null;
        String fileToString;
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            input = classloader.getResourceAsStream(filename);

            String temporaryFilename;
            if(filename.contains("/"))
                temporaryFilename = filename.substring(filename.lastIndexOf('/') + 1).trim();
            else temporaryFilename = filename;

            file = new File(temporaryFilename);

            FileUtils.copyInputStreamToFile(input,file);

            fileToString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG(Utils.class,LEVEL.ERROR,"File " + filename + " not found");
            fileToString = null;
        }
        finally {
            try {
                if(input!=null){
                    input.close();
                }
                if(file!=null){
                    file.delete();
                }
            } catch (IOException e) {
                LOG(Utils.class,LEVEL.ERROR,"Unable to close input stream or delete temporary file");
                fileToString = null;
            }
        }
        return fileToString;
    }

}
