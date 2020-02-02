/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.paziente;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.configs.AvatarProps;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;
import it.unitn.disi.wp.project.hms.commons.utils.FilesWriter;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Foto;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Servlet che gestisce la modifica dei dati anagrafici dell'utente {@link Paziente}
 * e l'aggiornamento della foto profilo.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 02.11.2019
 */
@MultipartConfig //serve per ricevere file in POST
@WebServlet(
    name = "modificaProfiloServlet", urlPatterns = {"/areaPrivata/profilo/modificaProfilo.html"},
    initParams = @WebInitParam(name = Attr.JSP_PAGE, value = "/pages/profilo/modificaProfilo/modificaProfilo.jsp")
)
public class ModificaProfiloServlet extends FactoryServlet {

    static public String getURL() {
        return "areaPrivata/profilo/modificaProfilo.html"; //porre l'URL definito in urlPatterns escluso lo "/" iniziale
    }

    private final String USER_IMG_PATH = "assets/img/restricted/" + Attr.USER_FOLDER.PATIENT.getName();

    private PazienteDAO pazienteDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null)
            throw new ServletException("Impossible to get dao factory");
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //============================================================================
        //  Prendo i parametri definiti in modificaProfiloModal.jsp e li salvo in una variaible di tipo String
        //============================================================================
        String contattoPrincipale = request.getParameter("contattoPrincipale"),
               contattoEmergenza = request.getParameter("contattoEmergenza");
        //  La foto non è ancora un immagine, arriva al server come oggetto di tipo Part
        Part fotoPart = request.getPart("foto"); // 'foto' corrisonde all'attributo name="foto" in modificaProfloModal.jsp
        //============================================================================

        if(fotoPart!= null && fotoPart.getSize()>1) { //significa che c'è una foto da aggiornare
            if (fotoPart.getSize() <= AvatarProps.getMaxFileSize()) { //non servono commenti
                LOG(this, "size before compression: " + Integer.valueOf((int) fotoPart.getSize()) + " KB");

                if (validateImage(fotoPart)) { //controllo se il file è effettivamente un immagine
                    LOG(this,"immagine valida");

                    if(AvatarProps.metadataRequested()) { // se i metadata sono richiesti
                        LOG(this, "avatar.properties -> metadataRequested = TRUE" );
                        Boolean dataValida = null; //postrò verificare se la data della foto corrisponde a quella attuale
                        try {
                           dataValida = dataValida(fotoPart.getInputStream());
                        } catch (ImageProcessingException e) {
                           LOG(this, e);
                           throw new ServletException(e);
                        }
                        if (dataValida == null) {
                            request.setAttribute(Attr.UPLOAD_STATUS, false);
                            request.setAttribute(Attr.ALERT_MSG, "Immagine non accettata!"); //perchè il sistema non trova la data nei metadata
                            LOG(this, LEVEL.ERROR, "Impossibile estrarre la data di scatto dai metadata!");
                        } else if (dataValida) {
                            LOG(this, "data valida");
                            try {
                                caricaFoto(request, fotoPart);
                            } catch (DAOException e) {
                                LOG(this, e);
                            }
                        } else if (!dataValida) {
                            request.setAttribute(Attr.UPLOAD_STATUS, false);
                            request.setAttribute(Attr.ALERT_MSG, "La data della foto deve essere attuale, ovvero corrispondente all'anno corrente!");
                            LOG(this, LEVEL.WARNING, "La foto non è attuale!");
                        }
                    }
                    else { // se i metadata non sono richiesti, carico l'immagine
                        LOG(this, "avatar.properties -> metadataRequested = FALSE" );
                        try {
                           caricaFoto(request, fotoPart);
                        } catch (DAOException e) {
                           LOG(this, e);
                        }
                    }
                } else {
                    request.setAttribute(Attr.UPLOAD_STATUS, false);
                    request.setAttribute(Attr.ALERT_MSG, "Immagine non riconosciuta!");
                    LOG(this, LEVEL.WARNING, "Immagine non leggibile!");
                }
            }
            else {
                request.setAttribute(Attr.UPLOAD_STATUS, false);
                request.setAttribute(Attr.ALERT_MSG, "La dimensione della foto è superiore a quella richiesta!");
                LOG(this, LEVEL.WARNING, "FOTO NON CARICATA: dimensione foto superata");
            }
        }

        Paziente paziente = (Paziente)request.getSession().getAttribute(Attr.USER);

        boolean almenoUnDatoDaAggiornare = false;

        if (!Utils.isNullOrEmpty(contattoPrincipale)){
            paziente.setContattoTelefonico(contattoPrincipale);
            almenoUnDatoDaAggiornare=true;
        }
        if (!Utils.isNullOrEmpty(contattoEmergenza)){
            paziente.setContattoEmergenza(contattoEmergenza);
            almenoUnDatoDaAggiornare=true;
        }

        if(almenoUnDatoDaAggiornare) {
            try {
                if (pazienteDAO.updateAnagrafica(paziente)) {
                    request.setAttribute(Attr.SUCCESS_MSG,"Dati aggiornati.");
                    request.getSession().setAttribute(Attr.USER,paziente);
                }
                else {
                    LOG(this,LEVEL.WARNING,"Dati non aggiornati");
                    request.setAttribute(Attr.ALERT_MSG,"Dati non aggiornati.");
                }
            } catch (DAOException e) {
                throw new ServletException(e);
            }
        }
        //============================================================================
        String root = (String)request.getSession().getAttribute(Attr.USER_ROOT_PATH);
        request.getRequestDispatcher(root + "/pages/profilo/profilo.jsp").forward(request,response);
    }

    private boolean validateImage(Part part) throws IOException {
        boolean res;

        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString(); //estraggo il nome del file
        String fileExtension = '.' + filename.substring(filename.lastIndexOf('.') + 1); //estraggo l'estensione dal nome del file

        //controllo se l'estensione del file è compresa nelle estensioni permesse
        res = AvatarProps.getAllowedExtensions().stream().anyMatch(extension -> extension.equals(fileExtension));

        if(res==true) //verifico anche se è effettivamente un immagine
            res = Utils.isImage(part.getInputStream());

        return res;
    }

    private Boolean dataValida(InputStream file) throws ImageProcessingException, IOException {

        if(file==null)
            throw new NullPointerException("Impossibile verificare dataValida di un file null");

        Metadata metadata = ImageMetadataReader.readMetadata(file);
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        if(metadata==null || directory==null)
            return null; //non esistono metadata
        else {
            Date deprecated_date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL); //La classe date è deprecata

            if(deprecated_date!= null) {
                Calendar calendar = new GregorianCalendar(); //quindi uso questa nuova classe
                calendar.setTime(deprecated_date);
                Integer fotoDate = calendar.get(Calendar.YEAR);
                //printMetadata(metadata); //se siete curiosi
                return (fotoDate == LocalDate.now().getYear()); //ritorno true se la foto corrisponde a quella attuale
            }
            else return null; //perchè la data non esiste nei metadata del file
        }
    }

    private void caricaFoto(HttpServletRequest request, Part fotoPart) throws IOException, DAOException {

        Paziente paziente = (Paziente)(request.getSession().getAttribute(Attr.USER));

        Timestamp timeStamp=new Timestamp(new Date().getTime());


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd_HH-mm-ss");
        String newFilename = sdf.format(timeStamp)+ AvatarProps.getExtension();

        Foto fotoPaziente = new Foto();
        fotoPaziente.setIdOwner(paziente.getId());
        fotoPaziente.setFilename(newFilename);
        fotoPaziente.setTimeStamp(timeStamp);

        BufferedImage image = Scalr.resize(
                ImageIO.read(fotoPart.getInputStream()), Scalr.Method.ULTRA_QUALITY, AvatarProps.getResizeSize(), Scalr.OP_ANTIALIAS);

        FilesWriter.saveImage(image, AvatarProps.getExtension(), USER_IMG_PATH  + "/" + paziente.getUsername() + "/" + newFilename);

        boolean fotoCaricata = pazienteDAO.updatePhoto(fotoPaziente);

        if (fotoCaricata) {
            paziente.setAvatarFilename(fotoPaziente.getFilename());
            request.setAttribute(Attr.UPLOAD_STATUS, true);
            request.getSession().setAttribute(Attr.USER, paziente); //DEVO aggiornare l'utente in sessione con i nuovi dati aggiornati
            LOG(this, "FOTO CARICATA");
        }
        else request.getSession().setAttribute(Attr.UPLOAD_STATUS, false);
    }

}
