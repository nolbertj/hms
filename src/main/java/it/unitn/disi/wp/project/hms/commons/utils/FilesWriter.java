/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Questa classe serve per salvare file nelle directory del progetto. Nello specifico sia all'interno
 * dei file dopo il folder <i>webapp</i> (locale), sia all'interno dei folder dopo <i>target</i> (che corrisponde al
 * folder necessario per mostrare un file nel browser quando richiesto (perchè non accede a quelli locali, cioè dopo webapp,
 * ma appunto solo alle cartelle dopo target/)
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 02.11.2019
 */
public class FilesWriter {

    static private String ROOT, TARGET_PATH, LOCAL_PATH;
    static private String servletContextName;
    static private FilesWriter instance = null;

    /**
     * Initializer method for the class
     * 
     * @param startPath
     * @param servletContextName
     * @throws InstantiationException if {@link FilesWriter} was already initialized
     */
    static public void init(String startPath, final String servletContextName) throws InstantiationException {
        if(instance == null) {
            ROOT = FilenameUtils.separatorsToUnix(startPath);
            FilesWriter.servletContextName=servletContextName;
            LOCAL_PATH = ROOT.replace("target/" + servletContextName,"src/main/webapp");
            LOCAL_PATH=LOCAL_PATH.replace("target/"+servletContextName.toUpperCase(), "src/main/webapp");
            TARGET_PATH = ROOT;

            instance = new FilesWriter();
        }
        else throw new InstantiationException("FilesWriter was already initialized.");
    }

    private FilesWriter() {
        super();
    }

    /**
     * Salva un'immagine nelle directory del progetto. <br>
     * Fa riferimento a tutto ciò che è dopo webApp/, quindi porre nel parametro {@code path} lo '/' iniziale! <br>
     * <strong>NOTA: </strong>se la path non esiste, e quindi non esiste la cartella specificata nella path,
     * verrà creata in automatico.
     *
     * @param image immagine di tipo BuffereImage
     * @param extension estensione dell'immagine (such as "png", "jpeg", etc...)
     * @param path il percorso in qui salvare l'immagine (dopo webapp/)
     *             
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @since 02.11.2019
     * @throws IOException if method couldn't store the {@link BufferedImage} image
     * @throws NullPointerException if extension is null, empty or has length = 0 or if {@link FilesWriter} wasn't initialized
     * @see Utils#isNullOrEmpty 
     * @see FilesWriter#init
    */
    static public void saveImage(final BufferedImage image, String extension, final String path) throws IOException {

        if(instance == null)
            throw new NullPointerException("FilesWriter class must be initialized with init(String startPath)!");

        if(Utils.isNullOrEmpty(extension))
            throw new NullPointerException("extension couldn't be null");

        if(extension.contains(".") || extension.charAt(0) == '.')
            extension = extension.substring(1);

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream(); //preparo il buffer in memoria

            File localOutput = new File(FilenameUtils.separatorsToUnix(LOCAL_PATH + path));
            File targetOutput = new File(FilenameUtils.separatorsToUnix(TARGET_PATH + path));


            ImageIO.write(image, extension, buffer); //creo nuovo file nel formato specificato dall'extension

            LOG(FilesWriter.class, "size after resize: " + buffer.size()/1024 + " KB");

            //cancello i file se esistono
            if(localOutput.exists()){
                localOutput.delete();
            }
            if(targetOutput.exists()){
                targetOutput.delete();
            }
            FileUtils.copyInputStreamToFile(new ByteArrayInputStream(buffer.toByteArray()), localOutput); //salvo nella cartella della wepApp
            FileUtils.copyInputStreamToFile(new ByteArrayInputStream(buffer.toByteArray()), targetOutput); //salvo nella cartella target

            LOG(FilesWriter.class, "immagine salvata in " + LOCAL_PATH + path);
            LOG(FilesWriter.class, "immagine salvata in " + TARGET_PATH + path);
        }
        catch(IOException ex){
            LOG(FilesWriter.class, LEVEL.ERROR, "Impossible to store " + image);
            throw new IOException(ex);
        }

    }

    static public void savePDF() {
        throw new UnsupportedOperationException("Da implementare!");
    }

}
