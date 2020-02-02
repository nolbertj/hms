/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet;

import com.google.zxing.WriterException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.utils.QRGen;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.UserDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Servlet that handles exporting to PDF.
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 14.11.2019
 */
@WebServlet(name = "PDFServlet", urlPatterns = {"/areaPrivata/PDFServlet"})
public class PDFServlet extends HttpServlet {

    //private final String USER_PDF_PATH="assets/pdf/" + Attr.USER_FOLDER.PATIENT.getName();
    private PazienteDAO pazienteDao;
    private UserDAO userDao;

    static public String getURL(){
        return "areaPrivata/PDFServlet";
    }

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        //userDao=daoFactory.getDAO(PazienteDAO.class);
        try {
            pazienteDao = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        Boolean isReferto=Boolean.valueOf(request.getParameter("referto"));
        Boolean isRicetta=Boolean.valueOf(request.getParameter("ricetta"));
        Boolean isPagamenti=Boolean.valueOf(request.getParameter("pagamenti"));
        Paziente p=(Paziente)request.getSession().getAttribute(Attr.USER);
        Integer idPaziente = ((Paziente)request.getSession().getAttribute(Attr.USER)).getId();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));

        if(isReferto){
            Document doc=new Document(pdfDoc, PageSize.A4);
            Integer idEsame=Integer.parseInt(request.getParameter("idEsame"));
            Referto r=null;
            try{
                r=pazienteDao.getReferto(idEsame, idPaziente);
            } catch (DAOException e) {
                LOG(this, e);
            }

            if (r!=null){

                creaReferto(r,p,doc);
                //doc.add(new Paragraph("Hello world" + idEsame));
                doc.close();

                // setting some response headers

                response.setHeader("Content-disposition", "attachment; filename=RefertoEsame_"+idEsame+"_Paziente_"+p.getUsername()+".pdf");
            }else{
                creaErrore(doc);
                doc.close();
                response.setHeader("Content-disposition", "attachment; filename=Error_404.pdf");
            }
        }else if(isRicetta) {
            Document doc = new Document(pdfDoc, PageSize.A4.rotate());
            Integer idRicetta = Integer.parseInt(request.getParameter("idRicetta"));
            Ricetta r = null;
            try {
                r = pazienteDao.getRicettaFarmaceutica(idRicetta, idPaziente);
                if(r!=null){
                    String jsonRicetta = r.giveMeJSON();
                    BufferedImage img = QRGen.getBufferedImage(jsonRicetta);
                    creaRicetta(r, p, doc, img);
                    doc.close();

                    // setting some response headers

                    response.setHeader("Content-disposition", "attachment; filename=Ricetta_" + idRicetta + "_Paziente_" + p.getUsername() + ".pdf");
                } else {
                    creaErrore(doc);
                    doc.close();
                    response.setHeader("Content-disposition", "attachment; filename=Error_404.pdf");
                }
            } catch (DAOException | WriterException e) {
                LOG(this, e);
                creaErrore(doc);
                doc.close();
                response.setHeader("Content-disposition", "attachment; filename=Error_404.pdf");
            }
        }else if(isPagamenti){
            Document doc=new Document(pdfDoc, PageSize.A4.rotate());

            try {
                Long count = pazienteDao.getCountEsamiPrescritti(idPaziente, "");
                List<Ricevuta> pagamenti=pazienteDao.pagePagamentiBySearchValue("", idPaziente, 0L, count, 1, "asc");
                creaListaPagamenti(pagamenti, p, doc);
                doc.close();
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");

                response.setHeader("Content-disposition", "attachment; filename=ListaPagamentiAl_" + sdf.format(new Date()) + "_Paziente_" + p.getUsername() + ".pdf");
            } catch (DAOException e) {
                LOG(this, e);
                creaErrore(doc);
                doc.close();
                response.setHeader("Content-disposition", "attachment; filename=Error_404.pdf");
            }

        }else{
            Document doc=new Document(pdfDoc, PageSize.A4);
            creaErrore(doc);
            doc.close();
            response.setHeader("Content-disposition", "attachment; filename=Error_404.pdf");
        }
        response.setContentType("application/pdf");
        // the contentlength
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();


    }
    private void creaErrore(Document doc){
        doc.add(new Paragraph("Qualcosa è andato storto"));
    }

    private void creaRicetta(Ricetta r, Paziente paziente, Document doc, BufferedImage qrImage) {
        final Color BLACK=new DeviceRgb(0,0,0);
        final float INTERLINEA=1.0f;


        try {
            PdfFont ffont= PdfFontFactory.createFont();
            Paragraph p=new Paragraph("Ricetta farmaceutica "+ r.getCodice());
            p.setFont(ffont).setFontSize(20).setTextAlignment(TextAlignment.CENTER).setBold();
            doc.add(p);


            Div div=new Div()
                    .setFont(ffont)
                    .setFontSize(11)
                    .setKeepTogether(true);
            Image img = new Image(ImageDataFactory.create(qrImage,  null));
            img.scaleToFit(200, 200);
            //tabella esterna: usa tutta la larghezza del foglio
            Table qr=new Table(2).useAllAvailableWidth();

            Cell c1=new Cell()
                    .add(img)
                    .setPadding(0)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER);
            qr.addCell(c1);

            Cell c2=new Cell()
                    .add(datiRicettaInCella(r, paziente))
                    .setPadding(0)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER);
            qr.addCell(c2);
            div.add(qr);
            doc.add(div);

            Div tabFarmaci=new Div()
                    .add(new Paragraph().add("Farmaci Prescritti")
                            .setFontSize(12).setBold())
                    .setFont(ffont)
                    .setFontSize(11)
                    .setKeepTogether(false);
            Table farmaci=new Table(7).useAllAvailableWidth().setKeepTogether(false);
            String[] intestazione={"Cod.", "Nome", "Descrizione", "Quantità", "Prezzo", "Totale", "Note"};

            for(int i=0;i<intestazione.length;i++){
                farmaci.addCell(new Cell()
                        .setBackgroundColor(ColorConstants.BLUE)
                        .setFontColor(ColorConstants.WHITE)
                        .setTextAlignment(TextAlignment.CENTER)
                        .add(new Paragraph()
                                .setFontSize(12)
                                .add(new Text(intestazione[i]).setBold())));
            }
            Locale locale = new Locale("it", "IT");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
            for(FarmacoPrescritto f: r.getFarmaciPrescritti()){
                farmaci.addCell(new Cell()
                        .add(new Paragraph()
                                .setFontSize(11)
                                .add(Integer.toString(f.getCodice()))));
                farmaci.addCell(new Cell()
                        .add(new Paragraph()
                                .setFontSize(11)
                                .add(f.getNome())));
                if(f.getCodice()!=0){
                    farmaci.addCell(new Cell()
                            .add(new Paragraph()
                                    .setFontSize(11)
                                    .add(f.getDescrizione())));
                }else{
                    farmaci.addCell(new Cell()
                            .add(new Paragraph()
                                    .setFontSize(11)
                                    .add("  ")));
                }

                farmaci.addCell(new Cell()
                    .add(new Paragraph()
                            .setFontSize(11)
                            .add(Integer.toString(f.getQuantita()))));
                Paragraph pPrezzo=new Paragraph().setFontSize(11);
                Paragraph pTotale=new Paragraph().setFontSize(11);


                if(r.getDataErogazione()!=null){
                    pPrezzo.add(currencyFormatter.format(f.getPrezzo()));
                    pTotale.add(currencyFormatter.format(f.getTotale()));
                }else{
                    pPrezzo.add("  ");
                    pTotale.add("  ");
                }
                farmaci.addCell(new Cell()
                        .add(pPrezzo));
                farmaci.addCell(new Cell().add(pTotale));
                if(f.getNote()!=null)
                    farmaci.addCell(new Cell()
                            .add(new Paragraph()
                                    .setFontSize(11)
                                    .add(f.getNote())));
                else
                    farmaci.addCell(new Cell()
                            .add(new Paragraph()
                                    .setFontSize(11)
                                    .add("  ")));
            }

            tabFarmaci.add(farmaci);
            doc.add(tabFarmaci);

            if(r.getDataErogazione()!=null){
                Paragraph p1=new Paragraph("TOTALE: "+ currencyFormatter.format(r.getTotale()))
                        .setBold().setTextAlignment(TextAlignment.RIGHT);
                doc.add(p1);
                Table erogante=new Table(2).useAllAvailableWidth();

                Cell dataErog=new Cell()
                        .add(tabDataErog(r))
                        .setPadding(0)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setBorder(Border.NO_BORDER);

                erogante.addCell(dataErog);
                erogante.addCell(new Cell()
                        .add(farmaciaErogante(r))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER));
                doc.add(new Div()
                        .setFont(ffont)
                        .setFontSize(11)
                        .setKeepTogether(true)
                        .add(erogante));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void creaReferto(Referto r, Paziente paziente, Document doc){
        final Color BLACK=new DeviceRgb(0,0,0);
        final float INTERLINEA=1.0f;
        try{
            //Imposto il font
            PdfFont ffont= PdfFontFactory.createFont();

            //medico esecutore
            Paragraph p=new Paragraph();
            p.setFont(ffont).setFontSize(13);
            p.add("Dr. "+r.getMedicoEsecutore() + "\n\n");
            doc.add(p);
            //nome dell'esame
            Paragraph p1=new Paragraph(r.getNomeEsame() + "\n");
            p1.setFontSize(16).setTextAlignment(TextAlignment.CENTER).setBold();
            doc.add(p1);

            /*ANAGRAFICA
            l'anagrafica è composta da un div (simile per concezione all'html)
            nel div è inclusa una tabella con 2 colonne: nella prima colonna si ha tutta l'anagrafica tranne l'età,
            che è nella seconda colonna
            Per avere il corretto allineamento dei campi della prima colonna si inserisce nella cella una tabella
            (innestata) a due colonne
            */Div div=new Div()
                    .setFont(ffont)
                    .setFontSize(11)
                    .setKeepTogether(true);

            //tabella esterna: usa tutta la larghezza del foglio
            Table anagrafica=new Table(2).useAllAvailableWidth();
            //cella per l'anagrafica a sinistra
            Cell c1=new Cell()
                    .add(anagraficaAllineataInCella(paziente));
            c1.setPadding(0);
            c1.setTextAlignment(TextAlignment.LEFT);
            c1.setBorder(Border.NO_BORDER);
            anagrafica.addCell(c1);
            //cella per l'anagrafica a destra: età
            if(paziente.getSesso().equals("F")){
                anagrafica.addCell(getCellCampoValore("Nata il:", Utils.getDateAsString(paziente.getDataNascita()),TextAlignment.RIGHT, 11));
            }else{
                anagrafica.addCell(getCellCampoValore("Nato il:", Utils.getDateAsString(paziente.getDataNascita()),TextAlignment.RIGHT, 11));
            }

            div.add(anagrafica);
            //linea finale di separazione
            SolidLine line=new SolidLine(1f);
            line.setColor(BLACK);
            LineSeparator ls=new LineSeparator(line)
                    .setMarginTop(5);
            div.add(ls);

            doc.add(div);

            //div per codice esami
            doc.add(new Div()
                    .setFont(ffont)
                    .setFontSize(11)
                    .setKeepTogether(true)
                    .add(tabCodice(r))
                    .add(ls));
            //div per anamnesi
            doc.add(new Div()
                    .setFont(ffont)
                    .setFontSize(11)
                    .setKeepTogether(true)
                    .add(tabAnamnesi(r))
                    .add(ls));

            //div per conclusioni
            doc.add(new Div()
                    .setFont(ffont)
                    .setFontSize(11)
                    .setKeepTogether(true)
                    .add(tabConclusioni(r))
                    .add(ls));

            //div per footer (data e nome medico)
            Table dataNome=new Table(2).useAllAvailableWidth();
            dataNome.setFixedPosition(doc.getLeftMargin(), doc.getBottomMargin(), doc.getPdfDocument().getDefaultPageSize().getWidth() - doc.getLeftMargin() - doc.getRightMargin());
            Cell c2=new Cell()
                    .add(tabData(r))
                    .setPadding(0)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER);
            dataNome.addCell(c2);

            dataNome.addCell(getCellTesto("Dr. " + r.getMedicoEsecutore(),TextAlignment.RIGHT, 11, false));
            doc.add(new Div()
                    .setFont(ffont)
                    .setFontSize(11)
                    .setKeepTogether(true)
                    .add(dataNome));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void creaListaPagamenti(List<Ricevuta> lista, Paziente paziente, Document doc){
        final Color BLACK=new DeviceRgb(0,0,0);
        final float INTERLINEA=1.0f;

        try {
            PdfFont ffont = PdfFontFactory.createFont();
            Paragraph p=new Paragraph("Lista Pagamenti al "+new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            p.setFont(ffont).setFontSize(20).setTextAlignment(TextAlignment.CENTER).setBold();
            doc.add(p);

            Div div1=new Div()
                    .setKeepTogether(true)
                    .setMarginBottom(10);
            //tabella esterna: usa tutta la larghezza del foglio
            Table anagrafica=new Table(2);
            //cella per l'anagrafica a sinistra
            Cell c1=new Cell()
                    .add(anagraficaAllineataInCella(paziente));
            c1.setPadding(0);
            c1.setTextAlignment(TextAlignment.LEFT);
            c1.setBorder(Border.NO_BORDER);
            anagrafica.addCell(c1);

            div1.add(anagrafica);
            doc.add(div1);


            Div div=new Div()
                    .setFont(ffont)
                    .setFontSize(11)
                    .setKeepTogether(false);

            Table tabPagamenti=new Table(6).useAllAvailableWidth().setKeepTogether(false).setTextAlignment(TextAlignment.CENTER);
            String[] intestazione={"Cod.", "Causale", "Data Erogazione", "Importo", "Stato", "Data Pagamento"};

            for(int i=0;i<intestazione.length;i++){
                tabPagamenti.addCell(new Cell()
                        .setBackgroundColor(ColorConstants.BLUE)
                        .setFontColor(ColorConstants.WHITE)
                        .setTextAlignment(TextAlignment.CENTER)
                        .add(new Paragraph()
                                .setFontSize(12)
                                .add(new Text(intestazione[i]).setBold())));
            }
            Locale locale = new Locale("it", "IT");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
            for(Ricevuta r: lista){
                tabPagamenti.addCell(new Cell()
                        .add(new Paragraph()
                                .setFontSize(11)
                                .add(Integer.toString(r.getIdRicevuta()))));
                Paragraph causale=new Paragraph().setFontSize(11);
                if(r.getIsEsame()){
                    causale.add("Esame specialistico");
                }else if(r.getIsRicetta()){
                    causale.add("Ricetta farmaceutica");
                }
                tabPagamenti.addCell(new Cell()
                        .add(causale));

                tabPagamenti.addCell(new Cell()
                            .add(new Paragraph()
                                    .setFontSize(11)
                                    .add(Utils.getDateAsString(r.getDataErogazione()))));
                tabPagamenti.addCell(new Cell()
                        .add(new Paragraph()
                                .setFontSize(11)
                                .add(currencyFormatter.format(r.getImporto()))));

                Paragraph pStato=new Paragraph().setFontSize(11);
                Paragraph pDataPagamento=new Paragraph().setFontSize(11);

                if(r.getDataPagamento()!=null){
                    pStato.add("Pagato");
                    pDataPagamento.add(Utils.getDateAsString(r.getDataPagamento()));
                }else{
                    pStato.add("Non Pagato");
                    pDataPagamento.add("  ");
                }
                tabPagamenti.addCell(new Cell().add(pStato));
                tabPagamenti.addCell(new Cell().add(pDataPagamento));
            }

            div.add(tabPagamenti);
            doc.add(div);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Cell getCellCampoValore(String campo, String valore, TextAlignment alignment, float fontSize) {
        Cell c;
        c = new Cell().add(new Paragraph()
                        .add(new Text(campo).setItalic().setBold())
                        .add(valore));
        c.setPadding(0);
        c.setTextAlignment(alignment);
        c.setFontSize(fontSize);
        c.setBorder(Border.NO_BORDER);
        return c;
    }

    private Cell getCellTesto(String testo, TextAlignment alignment, float fontSize, boolean paddingRight) {
        Cell c;
        c = new Cell().add(new Paragraph()
                .add(new Text(testo)));
        c.setPadding(0);
        if(paddingRight){
            c.setPaddingRight(4);
        }
        c.setPaddingTop(2);
        c.setPaddingBottom(2);
        c.setTextAlignment(alignment);
        c.setFontSize(fontSize);
        c.setBorder(Border.NO_BORDER);
        return c;
    }


    private Table anagraficaAllineataInCella(Paziente p){
        Table res=new Table(2).useAllAvailableWidth();

        res.addCell(getCellTesto("Paziente:", TextAlignment.LEFT, 12, true).setItalic().setBold());
        res.addCell(getCellTesto(p.getNome()+ " " + p.getCognome(), TextAlignment.LEFT, 12, false).setBold());
        res.addCell(getCellTesto("Codice fiscale:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(p.getCodFiscale(),TextAlignment.LEFT,11, false));
        res.addCell(getCellTesto("Residente a:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(p.getCittaResidenza(), TextAlignment.LEFT, 11, false));
        res.addCell(getCellTesto("Telefono:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(p.getContattoTelefonico(), TextAlignment.LEFT, 11, false));
        return res;
    }

    private Table datiRicettaInCella(Ricetta r, Paziente p){
        Table res=new Table(2).useAllAvailableWidth();


        String statoRicetta=(Utils.isNullOrEmpty(Utils.getTimestampAsString(r.getDataErogazione())) ? "NON EROGATA": "EROGATA");
        Text t=new Text(statoRicetta).setFontColor(new DeviceRgb(255, 0, 0));

        Cell c2=new Cell()
                .add(new Paragraph()
                        .add("Stato ricetta: ")
                        .setBorder(Border.NO_BORDER))
                .setPadding(0)
                .setPaddingRight(4)
                .setPaddingTop(2)
                .setPaddingBottom(2)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .setItalic()
                .setBold();
        res.addCell(c2);
        res.addCell(new Cell()
                .add(new Paragraph()
                        .add(t))
                .setPadding(0)
                .setPaddingTop(2)
                .setPaddingBottom(2)
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER));
        res.addCell(getCellTesto("Paziente:", TextAlignment.LEFT, 12, true).setItalic().setBold());
        res.addCell(getCellTesto(p.getNome()+ " " + p.getCognome(), TextAlignment.LEFT, 12, false).setBold());
        res.addCell(getCellTesto("Codice fiscale:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(p.getCodFiscale(),TextAlignment.LEFT,11, false));
        res.addCell(getCellTesto("Medico Prescrittore:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(r.getMedicoPrescrittore(), TextAlignment.LEFT, 11, false));
        res.addCell(getCellTesto("Data Prescrizione:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(Utils.getTimestampAsString(r.getDataPrescrizione()), TextAlignment.LEFT, 11, false));
        res.addCell(getCellTesto("Descrizione:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(r.getDescrizione(), TextAlignment.LEFT, 11, false));
        return res;
    }

    private Table tabAnamnesi(Referto r){
        Table res=new Table(2);
        res.addCell(getCellTesto("Anamnesi:    ", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(r.getAnamnesi(),TextAlignment.LEFT,11, false));

        return res;
    }

    private Table tabCodice(Referto r){
        Table res=new Table(2);
        res.addCell(getCellTesto("Codice esame:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(r.getIdEsame().toString(),TextAlignment.LEFT,11, false));

        return res;
    }

    private Table tabConclusioni(Referto r){
        Table res=new Table(2);
        res.addCell(getCellTesto("Conclusioni:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(r.getConclusioni(),TextAlignment.LEFT,11, false));
        return res;
    }

    private Table tabData(Referto r){
        Table res=new Table(2);
        res.addCell(getCellTesto("Data:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(Utils.getTimestampAsString(r.getDataErogazione()),TextAlignment.LEFT,11, false));
        return res;
    }

    private Table tabDataErog(Ricetta r){
        Table res=new Table(2);
        res.addCell(getCellTesto("Data erogazione:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(Utils.getTimestampAsString(r.getDataErogazione()),TextAlignment.LEFT,11, false));
        return res;
    }

    private Table farmaciaErogante(Ricetta r){
        Table res=new Table(2);
        res.addCell(getCellTesto("Farmacia erogante:", TextAlignment.LEFT, 11, true).setItalic().setBold());
        res.addCell(getCellTesto(r.getFarmaciaErogante(),TextAlignment.LEFT,11, false));
        return res;
    }
}
