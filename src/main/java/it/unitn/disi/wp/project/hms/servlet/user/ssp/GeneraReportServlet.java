/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.ssp;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.SspDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.FarmacoPrescritto;
import it.unitn.disi.wp.project.hms.persistence.entities.Ricetta;
import it.unitn.disi.wp.project.hms.persistence.entities.Ssp;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.List;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Servlet che gestisce la response della pagina jsp di generazione del report e la generazione del report
 *
 * @author Alessandro Brighenti &lt;alessandro brighenti at studenti dot unitn dot it&gt;
 * @since 16.01.2020
 */
@WebServlet(
    name = "generaReportServlet", urlPatterns = {"/areaPrivata/operazioni/generaReport.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/operazioni/generaReport/generaReport.jsp")}
)
public class GeneraReportServlet extends FactoryServlet {

    private SspDAO sspDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        //userDao=daoFactory.getDAO(PazienteDAO.class);
        try {
            sspDAO = daoFactory.getDAO(SspDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
    }

    static public String getURL(){
        return "areaPrivata/operazioni/generaReport.html";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Ssp ssp=(Ssp) request.getSession().getAttribute(Attr.USER);
        Date d=Date.valueOf(request.getParameter("data"));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        String[] columns={"Id ricetta", "Data e ora erogazione", "idFarmaco", "nomeFarmaco", "Importo", "Farmacia erogante", "Medico Prescrivente", "Nome paziente"};



        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        try{
            List<Ricetta> ricette=sspDAO.getRicetteErogateReport(ssp.getId(), d);

            if(ricette.size()==0){
                request.setAttribute(Attr.WARNING_MSG,true);
                String SSP_ROOT = Attr.USER_FOLDER.SSP.getRootPath();
                request.getRequestDispatcher(SSP_ROOT + "/pages/operazioni/generaReport/generaReport.jsp").forward(request,response);
            }
            else {
                int rowNum=1;
                for (Ricetta r : ricette) {
                    for(FarmacoPrescritto f: r.getFarmaciPrescritti()){
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(r.getCodice());
                        row.createCell(1).setCellValue(Utils.getTimestampAsString(r.getDataErogazione()));
                        row.createCell(2).setCellValue(f.getCodice());
                        row.createCell(3).setCellValue(f.getNome());
                        row.createCell(4).setCellValue(f.getPrezzo());
                        row.createCell(5).setCellValue(r.getFarmaciaErogante());
                        row.createCell(6).setCellValue(r.getMedicoPrescrittore());
                        row.createCell(7).setCellValue(r.getPaziente().getNome()+ " " + r.getPaziente().getCognome());
                    }
                }

                // Resize all columns to fit the content size
                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                response.setHeader("Content-disposition", "attachment; filename=reportOf-" + d + ".xlsx");
                response.setContentType("application/ms-excel");

                ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
                workbook.write(outByteStream);
                byte[] outArray = outByteStream.toByteArray();
                response.setContentLength(outArray.length);
                OutputStream os = response.getOutputStream();

                os.write(outArray);
                os.flush();
                os.close();
            }
        } catch (DAOException e) {
            LOG(this,e);
            throw new ServletException(e);
        }
    }
}
