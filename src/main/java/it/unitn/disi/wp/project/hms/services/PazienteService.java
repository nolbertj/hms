/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.services;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.configs.AvatarProps;
import it.unitn.disi.wp.project.hms.commons.configs.RESTConfig;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.*;
import it.unitn.disi.wp.project.hms.persistence.utils.DatatableResponse;
import it.unitn.disi.wp.project.hms.persistence.utils.RESTService;
import it.unitn.disi.wp.project.hms.persistence.utils.Select2Response;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * REST Web Service of patients
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 4.11.2019
 */
@Path("pazienti")
public class PazienteService extends RESTService {

    private PazienteDAO pazienteDAO;

    /** areaPrivata/services/pazienti */
    static public String getURL() {
        return RESTConfig.getURL() + "/pazienti";
    }

    @Override
    @Context
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        if (servletContext != null) {
            DAOFactory daoFactory = (DAOFactory) this.servletContext.getAttribute(Attr.DAO_FACTORY);
            if (daoFactory == null) {
                throw new RuntimeException(new ServletException("Impossible to get dao factory for storage system"));
            }
            try {
                pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            } catch (DAOFactoryException ex) {
                throw new RuntimeException(new ServletException(ex));
            }
        }
    }

    /**
     * Ritorna una lista json di {@link Paziente} contenente solo i dati necessari per una suggestion box.
     *
     * @return lista json di {@link Paziente} convertita in {@link String}
     * @param term stringa da cercare all'interno di tutti gli attributi
     * @see Select2Response
     */
    @GET
    @Path("{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForSuggestionBox(@PathParam("term") String term) {
        Response.ResponseBuilder responseBuilder;

        if (Utils.isNullOrEmpty(term) || term.equalsIgnoreCase("undefined") || term.length() < 1)
            return Response.noContent().entity(new Select2Response()).build();

        try {
            List<Paziente> tmp = pazienteDAO.getAll(term); //ci sono troppi dati sensibili
            List<Paziente> pazienti = new ArrayList<>(); //quindi creo una lista con solo i dati necessari

            for(Paziente p:tmp) {
                Paziente paziente = new Paziente();
                paziente.setId(p.getId());
                paziente.setNome(p.getNome());
                paziente.setCognome(p.getCognome());
                paziente.setCodFiscale(p.getCodFiscale());
                pazienti.add(paziente);
            }

            responseBuilder = Response.ok().entity(new Select2Response(pazienti));
        }
        catch (DAOException ex) {
            LOG(this, LEVEL.ERROR,"errore JDBC: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    /**
     * @return lista json di tutte le {@link Foto} del {@link Paziente}
     */
    @GET
    @Path("{id: [0-9]*}/avatars")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvatars(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt = new DatatableResponse(request,response);

            Integer idPaziente = Integer.parseInt(id);

            Long conteggioFoto = pazienteDAO.getCountFoto(idPaziente);
            if (dt.getLength() < 0) {
                dt.setLength(conteggioFoto - dt.getStart());
            }
            List<Foto> listaFoto = pazienteDAO.pageListaFoto(
                idPaziente, dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir()
            );

            dt.setParams(conteggioFoto,conteggioFoto,listaFoto);

            responseBuilder = Response.ok().entity(dt);
        } catch (IOException | DAOException ex) {
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    /**
     * Ritorna il bean {@link Paziente}
     *
     * @param id {@link Paziente#getId()}
     * @return {@link Paziente}
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("id") String id) {
        Response.ResponseBuilder responseBuilder;

        if (Utils.isNullOrEmpty(id))
             return Response.noContent().build();

        Integer idPaziente;
        try {
            idPaziente = Integer.valueOf(id);
        } catch(NumberFormatException ex) {
            LOG(this,ex);
            return Response.status(422).build();
        }

        try {
            Paziente paziente = pazienteDAO.getByPrimaryKey(idPaziente);
            paziente.setPassword(null);
            responseBuilder = Response.ok().entity(paziente);
        } catch (DAOException e) {
            LOG(this,e);
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return responseBuilder.build();
    }

    /**
     * Ritorna la foto profilo attuale del {@link Paziente}
     *
     * @param id {@link Paziente#getId()}
     * @return avatar del paziente in stringa {@link Base64#toString()}
     * @see DatatypeConverter#printBase64Binary(byte[] filenameToByteArray)
     */
    @GET
    @Path("{id: [0-9]*}/avatar")
    @Produces({ "image/png", "image/jpg", "image/jpeg" })
    public Response getAvatar(@PathParam("id") String id) {

        Response.ResponseBuilder responseBuilder;

        String realPath = servletContext.getRealPath("/");
        String path = realPath;
        path += "assets/img/restricted/paziente/";

        Paziente paziente = null;
        try {
            Integer idPaziente = Integer.parseInt(id);
            paziente = pazienteDAO.getByPrimaryKey(idPaziente);
        } catch (DAOException | NumberFormatException ex) {
            LOG(this,ex);
            if(ex instanceof NumberFormatException)
                return Response.status(422).build();
            else
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        path += paziente.getUsername() + "/" + paziente.getAvatarFilename();

        String extension = AvatarProps.getExtension();
        if(extension.contains(".") || extension.charAt(0) == '.')
            extension = extension.substring(1);

        File file = new File(FilenameUtils.separatorsToUnix(path));

        if(!file.exists()) {
            String defaultIMG = realPath + "assets/img/patient.jpg";
            file = new File(FilenameUtils.separatorsToUnix(defaultIMG));
            LOG(this, LEVEL.WARNING,"File not found. Sending default patient image");
        }
        else LOG(this,"File " + paziente.getAvatarFilename() + " requested found");

        ByteArrayOutputStream baos = null;
        try {
            LOG(this,"prepare buffer for image");
            BufferedImage image = ImageIO.read(file);

            baos = new ByteArrayOutputStream();
            ImageIO.write(image, extension, baos);

            LOG(this,"sending image...");
            responseBuilder = Response.ok().entity(DatatypeConverter.printBase64Binary(baos.toByteArray()));
            LOG(this,"image sended");
        }
        catch (IOException e) {
            LOG(this, LEVEL.ERROR,"Error during reading image");
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        finally {
            if(baos!=null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    LOG(this,e);
                    responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
                }
            }
        }

        return responseBuilder.build();
    }

    @GET
    @Path("{id: [0-9]*}/esamiPrescritti/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchEsame(@PathParam("term") String term, @PathParam("id") String idPaziente_) {
        Response.ResponseBuilder responseBuilder;

        if (Utils.isNullOrEmpty(term) || term.equalsIgnoreCase("undefined") || term.length() < 1) {
            return Response.noContent().entity(new Select2Response()).build();
        }
        try {
            Integer idPaziente = Integer.parseInt(idPaziente_);
            Long totEsami  = pazienteDAO.getCountEsamiPrescritti(idPaziente,term);
            List<EsamePrescritto> esami = pazienteDAO.pageEsPrescrittiBySearchValue(
                term,idPaziente,0L,totEsami,1,"asc"
            );
            responseBuilder = Response.ok().entity(new Select2Response(esami));
        }
        catch (DAOException ex) {
            LOG(this, LEVEL.ERROR,"errore JDBC: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("{id: [0-9]*}/esamiErogabiliBySSP/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchErogabileBySSP(@PathParam("term") String term, @PathParam("id") String idPaziente_) {
        Response.ResponseBuilder responseBuilder;

        if (Utils.isNullOrEmpty(term) || term.equalsIgnoreCase("undefined") || term.length() < 1) {
            return Response.noContent().entity(new Select2Response()).build();
        }
        try {
            Integer idPaziente = Integer.parseInt(idPaziente_);
            Long totEsami  = pazienteDAO.getCountEsamiPrescritti(idPaziente,term);
            List<EsamePrescritto> esami = pazienteDAO.getEsPrescrittiErogabiliSSP(term, idPaziente);
            responseBuilder = Response.ok().entity(new Select2Response(esami));
        }
        catch (DAOException ex) {
            LOG(this, LEVEL.ERROR,"errore JDBC: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    /**
     * Ritorna tutti gli esami prescritti del {@link Paziente} in json
     *
     * @param id {@link Paziente#getId()}
     * @return {@link List<EsamePrescritto>}
     */
    @GET
    @Path("{id: [0-9]*}/esamiPrescritti")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEsamiPrescritti(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt=new DatatableResponse(request, response);


            Long conteggioEsPresc = null;
            List<EsamePrescritto> esPresc = null;

            Integer idPaziente = null;
            try {
                idPaziente = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                LOG(this,ex);
                return Response.status(422).build();
            }

            conteggioEsPresc = pazienteDAO.getCountEsamiPrescritti(idPaziente, dt.getSearchValue());
            if (dt.getLength() <0) {
                dt.setLength(conteggioEsPresc - dt.getLength());
            }
            esPresc = pazienteDAO.pageEsPrescrittiBySearchValue(dt.getSearchValue(), idPaziente, dt.getStart(),dt.getLength(), dt.getOrderColumn(), dt.getOrderDir());

            dt.setParams(conteggioEsPresc, conteggioEsPresc, esPresc);

            responseBuilder = Response.ok().entity(dt);
        }
        catch (DAOException | IOException ex) {
            LOG(this,ex);
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    /**
     * Ritorna una lista json di {@link MedicoBase} in base alla provincia di residenza del {@link Paziente}
     *
     * @param id {@link Paziente#getId()}
     * @return {@link List<MedicoBase>}
     */
    @GET
    @Path("{id: [0-9]*}/medici")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedici(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt=new DatatableResponse(request, response);

            Long conteggioMedici = null;
            List<MedicoBase> mediciBase = null;

            Integer idPaziente = null;
            try {
                idPaziente = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                LOG(this,ex);
                return Response.status(422).build();
            }

            conteggioMedici = pazienteDAO.getCountMedici(idPaziente, dt.getSearchValue());
            if (dt.getLength() <0) {
                dt.setLength(conteggioMedici - dt.getStart());
            }
            mediciBase = pazienteDAO.pageMediciBaseBySearchValue( dt.getSearchValue(), idPaziente, dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir());

            dt.setParams(conteggioMedici, conteggioMedici, mediciBase);

            responseBuilder = Response.ok().entity(dt);
        }
        catch (DAOException | IOException ex) {
            LOG(this,LEVEL.ERROR,"Impossible to access the persistence layer: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    /**
     * Ritorna tutti i referti (in json) del {@link Paziente}
     *
     * @param id {@link Paziente#getId()}
     * @return {@link List<Referto>}
     */
    @GET
    @Path("{id: [0-9]*}/referti")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReferti(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt=new DatatableResponse(request, response);

            Long conteggioReferti = null;
            List<Referto> referti = null;

            Integer idPaziente = null;
            try {
                idPaziente = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                LOG(this,ex);
                return Response.status(422).build();
            }

            conteggioReferti=pazienteDAO.getCountReferti(idPaziente, dt.getSearchValue());
            if ( dt.getLength() <0) {
                dt.setLength(conteggioReferti - dt.getStart());
            }
            referti = pazienteDAO.getRefertiOrdered(dt.getSearchValue(), idPaziente, dt.getOrderColumn(), dt.getOrderDir(), dt.getStart(), dt.getLength());

            dt.setParams(conteggioReferti, conteggioReferti, referti);

            responseBuilder = Response.ok().entity(dt);
        }
        catch (DAOException | IOException ex) {
            LOG(this,"Impossible to access the persistence layer: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    /**
     * Ritorna tutte tutte le ricette {@link Ricetta} del {@link Paziente} (in json)
     *
     * @param id {@link Paziente#getId()}
     * @return {@link List<Ricetta>}
     */
    @GET
    @Path("{id: [0-9]*}/ricetteFarmaceutiche")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRicetteFarmaceutiche(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt=new DatatableResponse(request, response);

            Long conteggioRicette = null;
            List<Ricetta> ricette = null;

            Integer idPaziente = null;
            try {
                idPaziente = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                LOG(this,ex);
                return Response.status(422).build();
            }

            conteggioRicette = pazienteDAO.getCountRicetteFarmaceutiche(idPaziente, dt.getSearchValue());
            if (dt.getLength() <0) {
                dt.setLength(conteggioRicette - dt.getStart());
            }
            ricette = pazienteDAO.pageRicetteFarmaceuticheBySearchValue(dt.getSearchValue(), idPaziente, dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir());

            dt.setParams(conteggioRicette, conteggioRicette, ricette);

            responseBuilder = Response.ok().entity(dt);
        }
        catch (DAOException | IOException ex) {
            LOG(this,"Impossible to access the persistence layer: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    /**
     * Ritorna tutte le ricette [{@link Ricetta}] <strong>non</strong> erogate,
     * ovvero quelle con {@link Ricetta#getDataErogazione()} = {@code null} in json.
     * @param id {@link Paziente#getId()}
     * @return {@link List<Ricetta>}
     */
    @GET
    @Path("{id: [0-9]*}/ricette")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRicette(@PathParam("id") String id, @QueryParam("erogate") String erogate_) {
        Response.ResponseBuilder responseBuilder;
        Boolean erogate = null;
        try {
            erogate = Boolean.getBoolean(erogate_);
        } catch (Exception e) {
            LOG(this,e);
            return Response.status(422).build();
        }

        try {
            DatatableResponse dt = new DatatableResponse(request, response);
            Long conteggioRicette = null;

            Integer idPaziente = null;
            try {
                idPaziente = Integer.valueOf(id);
            } catch (NumberFormatException ex) {
                return Response.status(422).build();
            }
            if (erogate != null) {
                if (!erogate) {
                    conteggioRicette = pazienteDAO.getCountRicetteFarmaceuticheNonErogate(idPaziente);
                    if (dt.getLength() < 0) {
                        dt.setLength(conteggioRicette - dt.getStart());
                    }
                    List<Ricetta> ricette = pazienteDAO.pageRicetteFarmaceuticheBySearchValue(dt.getSearchValue(), idPaziente, dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir());
                    List<Ricetta> ricetteNonErogate = ricette.stream().filter(r -> r.getDataErogazione() == null).collect(Collectors.toList());
                    dt.setParams(conteggioRicette, conteggioRicette, ricetteNonErogate);

                    responseBuilder = Response.ok().entity(dt);
                } else if (erogate) {
                    return this.getRicetteFarmaceutiche(id);
                } else {
                    responseBuilder = Response.noContent();
                }
            }else{
                responseBuilder = Response.noContent();
            }
        } catch(DAOException | IOException ex){
            LOG(this, "Impossible to access the persistence layer: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return responseBuilder.build();
    }

    /**
     * Ritorna una lista di {@link Ricevuta} del {@link Paziente} (in json)
     *
     * @param id {@link Paziente#getId()}
     * @return {@link List<Ricevuta>}
     */
    @GET
    @Path("{id: [0-9]*}/listaPagamenti")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaPagamenti(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt=new DatatableResponse(request, response);

            Long countPagamenti;
            List<Ricevuta> pagamenti;

            Integer idPaziente = null;
            try {
                idPaziente = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                LOG(this,ex);
                return Response.status(422).build();
            }

            countPagamenti = pazienteDAO.getCountPagamenti(idPaziente, dt.getSearchValue());
            if (dt.getLength()<0) {
                dt.setLength(countPagamenti - dt.getStart());
            }
            pagamenti = pazienteDAO.pagePagamentiBySearchValue(dt.getSearchValue(), idPaziente, dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir());

            dt.setParams(countPagamenti, countPagamenti, pagamenti);

            responseBuilder = Response.ok().entity(dt);
        }
        catch (DAOException | IOException ex) {
            LOG(this,"Impossible to access the persistence layer: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("{id: [0-9]*}/visite")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVisite(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt = new DatatableResponse(request,response);

            Integer idPaziente = Integer.parseInt(id);

            Long counter = pazienteDAO.getCountVisite(idPaziente,dt.getSearchValue());
            if (dt.getLength() < 0) {
                dt.setLength(counter - dt.getStart());
            }
            List<Visita> visite = pazienteDAO.pageVisiteBySearchValue(
                    dt.getSearchValue(),idPaziente,dt.getStart(),dt.getLength(),dt.getOrderColumn(),dt.getOrderDir()
            );

            dt.setParams(counter,counter,visite);

            responseBuilder = Response.ok().entity(dt);
        } catch (IOException | DAOException ex) {
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    /**
     * Ritorna una lista di {@link Ambulatorio} della provincia del {@link Paziente} (in json)
     *
     * @param id {@link Paziente#getId()}
     * @return {@link List<Ambulatorio>}
     */
    @GET
    @Path("{id: [0-9]*}/listaAmbulatori")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaAmbulatori(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt=new DatatableResponse(request, response);

            Long countAmbulatori;
            List<Ambulatorio> ambulatori;

            Integer idPaziente = null;
            try {
                idPaziente = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                LOG(this,ex);
                return Response.status(422).build();
            }

            countAmbulatori = pazienteDAO.getCountAmbulatori(idPaziente, dt.getSearchValue());
            if (dt.getLength()<0) {
                dt.setLength(countAmbulatori - dt.getStart());
            }
            ambulatori = pazienteDAO.pageAmbulatoriBySearchValue(idPaziente, dt.getSearchValue(), dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir());

            dt.setParams(countAmbulatori, countAmbulatori, ambulatori);

            responseBuilder = Response.ok().entity(dt);
        }
        catch (DAOException | IOException ex) {
            LOG(this,"Impossible to access the persistence layer: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

}