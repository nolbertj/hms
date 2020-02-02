/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user;

import com.itextpdf.kernel.pdf.CompressionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.configs.PDFProps;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.DocumentoDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.*;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Servlet che gestisce la response della pagina jsp dei documenti
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 24.11.2019
 */
@WebServlet(
    name = "documentiServlet", urlPatterns = {"/areaPrivata/documenti/altriDocumenti.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/documenti/altriDocumenti/altriDocumenti.jsp")}
)
@MultipartConfig
public class DocumentiServlet extends HttpServlet {

    static public String getURL() {
        return "areaPrivata/documenti/altriDocumenti.html";
    }

    static public final int BUFFER_SIZE = 1024*100; // non so cosa scrivere

    public enum ACTION {
        SHOW("show"), // apre una nuova pagina sul browser del documento; l'utente potrà scegliere se scaricarlo o no
        UPLOAD("upload"), // carica un nuovo documento
        UPDATE("update"), // aggiorna titolo e/o descrizione del documento nel database
        DELETE("delete"), // elimina il documento
        DOWNLOAD("download"); // l'utente scaricherà subito il documento al click

        private String value;
        ACTION(String val) {
            this.value=val;
        }
        public String getValue(){
            return value;
        }
    }

    private DocumentoDAO documentoDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null) throw new ServletException("Impossible to get dao factory");
        try {
            documentoDAO = daoFactory.getDAO(DocumentoDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        User user = (User)req.getSession().getAttribute(Attr.USER);
        String filename = null, titolo = null, descrizione = null, action = null;
        Documento documento;

        if(Utils.isValidParam(req.getParameter(ACTION.class.getSimpleName().toLowerCase()))) {
            action = req.getParameter(ACTION.class.getSimpleName().toLowerCase());
            LOG(this,"ACTION: " + action);
        }
        else {
            LOG(this,LEVEL.ERROR,"Parametro inesistente! Forse qualcuno l'ha tolto dall'html. (tentativo di hacking)");
            res.sendError(422);
            return;
        }

        try {
            ACTION switchValue=this.getEnumByValue(action);
            if(switchValue!=null) {
                switch (switchValue) {
                    case UPDATE: {
                        if (!Utils.isValidParam(req.getParameter("filename"))) {
                            res.sendError(422); //invalid input
                            LOG(this, LEVEL.WARNING, "Tentativo di hacking! Qualcuno ha tolto il parametro nell'html");
                            break;
                        }

                        filename = req.getParameter("filename");
                        if (Utils.isNullOrEmpty(filename)) { //se ad esempio la stringa è stata modificata mettendola vuota ("")
                            res.sendError(422);
                            LOG(this, LEVEL.WARNING, "Parametro vuoto!");
                            break;
                        }

                        if (Utils.isValidParam(req.getParameter("titolo"))) {
                            titolo = req.getParameter("titolo");
                            if (Utils.isNullOrEmpty(titolo)) //se l'utente non scrive niente nel tag <input>
                                titolo = null;  //viene ritornato "" quindi in questo modo
                            //potrò evitare di sovrascrievre il titolo con una stringa vuota
                        }
                        if (Utils.isValidParam(req.getParameter("descrizione"))) {
                            descrizione = req.getParameter("descrizione");
                            if (Utils.isNullOrEmpty(descrizione))
                                descrizione = null;
                        }

                        LOG(this, "UPDATING " + filename + " titolo e/o descrizione...");

                        documento = new Documento();
                        documento.setEmailProprietario(user.getEmail());
                        documento.setFilename(filename);
                        documento.setTitolo(titolo);
                        documento.setDescrizione(descrizione);

                        Boolean updated = documentoDAO.update(documento);

                        if (updated) {
                            String msg = "Documento " + filename + " aggiornato.";
                            req.setAttribute(Attr.SUCCESS_MSG, true);
                            LOG(this, msg);
                        } else {
                            req.setAttribute(Attr.ALERT_MSG, "Documento " + filename + " non aggiornato");
                            LOG(this,
                                    "Documento " + filename + " non aggiornato. " +
                                            "Possibili problemi sul database o JDBC"
                            );
                        }
                        processRequest(req, res);
                    }
                    break;
                    case SHOW: {
                        if (!Utils.isValidParam(req.getParameter("filename"))) {
                            res.sendError(422); //invalid input
                            LOG(this, LEVEL.WARNING, "Tentativo di hacking! Qualcuno ha tolto il parametro nell'html");
                            break;
                        }

                        filename = req.getParameter("filename");
                        if (Utils.isNullOrEmpty(filename)) { //se ad esempio la stringa è stata modificata mettendola vuota ("")
                            res.sendError(422);
                            LOG(this, LEVEL.WARNING, "Parametro vuoto!");
                            break;
                        }

                        LOG(this, "Sending " + filename + " to client...");
                        File file = getFile(filename, user);

                        if (!file.exists()) {
                            res.sendError(404);
                            LOG(this, LEVEL.ERROR, "File " + filename + " non trovato!");
                            break;
                        }

                        documento = new Documento();
                        documento = documentoDAO.getByPrimaryKeys(user.getEmail(), filename);
                        if (documento == null) {
                            LOG(this, "Il documento è presente nel server ma non nel database!");
                            res.sendError(500); //ma non lo devo far sapere all'utente, gli dico semplicemente "problema interno al server".
                            break;
                        }

                        OutputStream outputStream = null;
                        FileInputStream inputStream = null;
                        try {
                            res.setContentType("application/pdf");
                            res.setHeader("Content-Disposition", "inline; filename=" + filename);

                            outputStream = res.getOutputStream();
                            inputStream = new FileInputStream(file);
                            byte[] buffer = new byte[BUFFER_SIZE];
                            int bytesRead = -1;

                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }

                            LOG(this, filename + " sended successfully");
                        } catch (IOException ex) {
                            res.sendError(500);
                            LOG(this, "problema per inviare file al client");
                        } finally {
                            if (inputStream != null) inputStream.close();

                            if (outputStream != null){
                                outputStream.flush();
                                outputStream.close();
                            }
                        }
                    }
                    break;
                    case DELETE: {
                        if (!Utils.isValidParam(req.getParameter("filename"))) {
                            res.sendError(422); //invalid input
                            LOG(this, LEVEL.WARNING, "Tentativo di hacking! Qualcuno ha tolto il parametro nell'html");
                            break;
                        }

                        filename = req.getParameter("filename");
                        if (Utils.isNullOrEmpty(filename)) { //se ad esempio la stringa è stata modificata mettendola vuota ("")
                            res.sendError(422);
                            LOG(this, LEVEL.WARNING, "Parametro vuoto!");
                            break;
                        }

                        LOG(this, "DELETING " + filename + " ...");

                        Boolean deleted = documentoDAO.delete(user.getEmail(), filename);

                        if (deleted) {
                            LOG(this, "File deleted from database");

                        /*  Usare la condizione sottostante solo per il deploy ufficiale finale,
                            altrimenti verrà eliminato anche il file dentro src/main/webapp e
                            non sarà possibile recuperarlo dal cestino (ma solo col comando git checkout nomeFile)
                        if(deleteLocalFile(filename,user) && deleteTargetFile(filename,user))
                            LOG(this,"File deleted from src and target folder");

                            if (deleteTargetFile(filename, user)) {//usare questo if durante le fasi di prova ma ricordarsi
                                //di rimettere il nome del file nel database dato che non verrà eliminato il fiel dentro src/main/webapp
                                LOG(this, "File deleted");
                            } else LOG(this, LEVEL.WARNING, "File eliminato dal database ma non dal server!");
                            */
                            req.setAttribute(Attr.SUCCESS_MSG, true);
                            LOG(this, "File deleted successfully");
                        } else {
                            req.setAttribute(Attr.ALERT_MSG, true);
                            LOG(this, filename + " non trovato o file non trovato con problema database o JDBC");
                        }

                        processRequest(req, res);
                    }
                    break;
                    case UPLOAD: {
                        LOG(this, "UPLOADING a document...");
                        Part part = req.getPart("fileUpload");

                        if (part != null && part.getSize() > 1) {
                            if (part.getSize() <= PDFProps.getMaxFileSize()) {

                                filename = part.getSubmittedFileName();

                                String targetDest = getUserMediaPath(user);
                                if(targetDest!=null){
                                    String localDest = targetDest.replace(
                                            "target/" + getServletContext().getServletContextName().toLowerCase(),
                                            "src/main/webapp"
                                    );

                                    PdfReader readerOne = new PdfReader(part.getInputStream()),
                                            readerTwo = new PdfReader(part.getInputStream());

                                    PdfWriter targetWriter = new PdfWriter(FilenameUtils.separatorsToUnix(
                                            targetDest + "/" + filename)
                                    ), localWriter = new PdfWriter(FilenameUtils.separatorsToUnix(
                                            localDest + "/" + filename)
                                    );

                                    if (req.getParameter("compress").toLowerCase().equalsIgnoreCase("on")) {
                                        LOG(this, "size before compression: " + part.getSize() + " KB");
                                        targetWriter.setCompressionLevel(CompressionConstants.BEST_COMPRESSION);
                                        localWriter.setCompressionLevel(CompressionConstants.BEST_COMPRESSION);
                                    }

                                    PdfDocument localPDF = new PdfDocument(readerOne, localWriter);
                                    PdfDocument targetPDF = new PdfDocument(readerTwo, targetWriter);

                                    readerOne.close();
                                    readerTwo.close();
                                    localWriter.close();
                                    targetWriter.close();
                                    localPDF.close();
                                    targetPDF.close();

                                    documento = new Documento();
                                    documento.setEmailProprietario(user.getEmail());
                                    documento.setFilename(filename);
                                    if ((!Utils.isNullOrEmpty(req.getParameter("titolo"))) && (!Utils.isValidParam(req.getParameter("titolo"))))
                                        titolo = req.getParameter("titolo");
                                    documento.setTitolo(titolo);
                                    if ((!Utils.isNullOrEmpty(req.getParameter("descrizione")) && (!Utils.isValidParam(req.getParameter("descrizione")))))
                                        descrizione = req.getParameter("descrizione");
                                    documento.setDescrizione(descrizione);
                                    documento.setDataCaricamento(new Timestamp(new Date().getTime()));

                                    Boolean uploaded = documentoDAO.create(documento);
                                    if (uploaded) {
                                        LOG(this, "file " + filename + " uploaded in db successfully");
                                        req.setAttribute(Attr.SUCCESS_MSG, true);
                                    } else {
                                        LOG(this, LEVEL.WARNING, "file " + filename + " NON caricato sul db ma solo sul server");
                                        req.setAttribute(Attr.WARNING_MSG, true);
                                    }
                                }else{
                                    req.setAttribute(Attr.ALERT_MSG, true);
                                    LOG(this, LEVEL.WARNING, "Cartella dell'utente non trovata");
                                }
                            } else {
                                req.setAttribute(Attr.ALERT_MSG, true);
                                LOG(this, LEVEL.WARNING, "Dimensione file superiore al limite!");
                            }
                        } else {
                            res.sendError(422);
                            LOG(this,
                                    "Tentativo di hacking! " +
                                            "Non è stato possibile trovare il parametro per ricere il file. " +
                                            "Probabilmente qualcuno lo ha tolto dall'html."
                            );
                        }
                        processRequest(req, res);
                    }
                    break;
                    case DOWNLOAD: {
                        if (!Utils.isValidParam(req.getParameter("filename"))) {
                            res.sendError(422); //invalid input
                            LOG(this, LEVEL.WARNING, "Tentativo di hacking! Qualcuno ha tolto il parametro nell'html");
                            break;
                        }

                        filename = req.getParameter("filename");
                        if (Utils.isNullOrEmpty(filename)) { //se ad esempio la stringa è stata modificata mettendola vuota ("")
                            res.sendError(422);
                            LOG(this, LEVEL.WARNING, "Parametro vuoto!");
                            break;
                        }

                        File fileToDownload = getFile(filename, user);

                        if (!fileToDownload.exists()) {
                            res.sendError(404);
                            LOG(this, LEVEL.ERROR, "File " + filename + " non trovato!");
                            break;
                        } else {
                            LOG(this, "Sending " + filename + " to client for download...");
                        }

                        res.setContentType("application/pdf");
                        res.setHeader("Content-disposition", "attachment; filename=" + filename);

                        OutputStream out = res.getOutputStream();
                        FileInputStream in = new FileInputStream(fileToDownload);
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int length;
                        while ((length = in.read(buffer)) > 0) {
                            out.write(buffer, 0, length);
                        }

                        LOG(this, filename + " downloaded by client");

                        in.close();
                        out.flush();
                        out.close();
                    }
                    break;
                    default:
                        LOG(this, LEVEL.ERROR, "Tentativo di hacking! Parametro ACTION modificato nell'html.");
                        res.sendError(422);
                        break;
                }
            }
        }
        catch(DAOException ex) {
            LOG(this,ex);
            throw new ServletException(ex);
        }

    }

    private String getUserMediaPath(User user) {
        String startPath = super.getServletContext().getRealPath("/");

        if(user instanceof Paziente)
            startPath += Attr.USER_FOLDER.PATIENT.getMediaPath();
        else if(user instanceof MedicoBase)
            startPath += Attr.USER_FOLDER.BASE_DOCTOR.getMediaPath();
        else if(user instanceof MedicoSpecialista)
            startPath += Attr.USER_FOLDER.SPECIAL_DOCTOR.getMediaPath();
        else if(user instanceof Farmacia)
            startPath += Attr.USER_FOLDER.PHARMACY.getMediaPath();
        else if(user instanceof Ssp)
            startPath += Attr.USER_FOLDER.SSP.getMediaPath();
        else if(user instanceof Admin)
            startPath += Attr.USER_FOLDER.ADMIN.getMediaPath();
        else
            return null;

        startPath += "/" + user.getUsername();

        return startPath;
    }

    private boolean deleteTargetFile(String filename, User user) {
        if(getFile(filename,user).exists()) {
            getFile(filename, user).delete();
            LOG(this,"File deleted from target/");
            return true;
        }
        else {
            LOG(this,LEVEL.WARNING,"Couldn't delete file from target/");
            return false;
        }
    }

    private boolean deleteLocalFile(String filename, User user) {
        String targetPath = getFile(filename,user).getAbsolutePath();
        String localPath = targetPath.replace(
            "target/" + super.getServletContext().getServletContextName().toLowerCase(),
            "src/main/webapp"
        );

        File file = new File(FilenameUtils.separatorsToUnix(localPath));
        if(file.exists()) {
            file.delete();
            LOG(this,"File deleted from src/main/webapp");
            return true;
        }
        else {
            LOG(this,LEVEL.WARNING,"Couldn't delete file from src/main/webapp");
            return false;
        }
    }

    private File getFile(String filename, User user) {
        String startPath = getUserMediaPath(user);
        return new File(FilenameUtils.separatorsToUnix(startPath + "/" + filename));
    }

    static public ACTION getEnumByValue(String value) {
        if(value.equalsIgnoreCase(ACTION.SHOW.getValue()))
            return ACTION.SHOW;
        else if(value.equalsIgnoreCase(ACTION.UPLOAD.getValue()))
            return ACTION.UPLOAD;
        else if(value.equalsIgnoreCase(ACTION.UPDATE.getValue()))
            return ACTION.UPDATE;
        else if(value.equalsIgnoreCase(ACTION.DELETE.getValue()))
            return ACTION.DELETE;
        else if(value.equalsIgnoreCase(ACTION.DOWNLOAD.getValue()))
            return ACTION.DOWNLOAD;
        else return null;
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //==============================================================================================================
        User user = (User)req.getSession().getAttribute(Attr.USER);
        try {
            req.setAttribute(Attr.DOCUMENTI,documentoDAO.getAll(user.getEmail()));
        } catch (DAOException e) {
            LOG(this,e);
            throw new ServletException(e);
        }
        //==============================================================================================================
        String root = (String)req.getSession().getAttribute(Attr.USER_ROOT_PATH);
        req.getRequestDispatcher(root + getInitParameter(Attr.JSP_PAGE)).forward(req,res);
    }

}
