package resources.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @description Servlet for downloading file attachements.
 * @author jsnrice
 */
public class DownloadController extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(DownloadController.class.getName());

    /**
     * @description @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType("application/pdf");

            if (request.getParameter("downloadpdf") != null) {
                LOGGER.log(Level.INFO, "Downloading form as PDF.");
                downloadAsPDF(request, response);
            } else if (request.getParameter("downloadexcel") != null) {
                LOGGER.log(Level.INFO, "Downloading form as Excel file.");
                downloadAsExcel(request, response);
            } else {
                LOGGER.log(Level.WARNING, "Warning, did not receive request for PDF or Excel download. Something went wrong.");
            }
        } catch (Exception ex) {
            Logger.getLogger(DownloadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @description Obtain the form content and transpose into a byte stream
     * that is then recorded to a Excel (XLS) file.
     * @param request
     * @param response
     * @throws DocumentException
     * @throws IOException
     */    
    public void downloadAsExcel(HttpServletRequest request, HttpServletResponse response)
            throws DocumentException, IOException {

        // create a small spreadsheet
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        System.out.println("\n\nBefore For xls Loop");

        String name = request.getParameter("name");
        String status = request.getParameter("status");
        String total = request.getParameter("total");
        String cost = request.getParameter("cost");        
        String[] courses = request.getParameterValues("courses");
        String[] accomodations = request.getParameterValues("accomodations");
        String[] accomodationCosts = request.getParameterValues("accomodationCosts");
        
        int i;
        for (i = 0; i < courses.length; i++) {
            HSSFRow row = sheet.createRow(i);
            String course = courses[i];
            HSSFCell cellOne = row.createCell(0);
            HSSFCell cellTwo = row.createCell(1);
            cellOne.setCellValue(course);        
            cellTwo.setCellValue("$" + cost);
        }
        
        i += 1;
        for (int j = 0; j < accomodations.length; j++, i++) {
            HSSFRow row = sheet.createRow(i);
            String accomodation = accomodations[j];
            String accomodationCost = accomodationCosts[j];
            HSSFCell cellOne = row.createCell(0);
            HSSFCell cellTwo = row.createCell(1);               
            cellOne.setCellValue(accomodation);
            cellTwo.setCellValue("$" + accomodationCost);
        }
        
        // Total cost:        
        i = insertRow(1+i, wb, sheet, "Total Cost:", "$" + total);
        
        // User info:
        i = insertRow(1 + i, wb, sheet, "Name:", name);
        i = insertRow(i, wb, sheet, "Employment Status:", status);
        i = insertRow(i, wb, sheet, "Registration:", "Confirmed");

        // Write byte stream to ms-excel:
        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        wb.write(outByteStream);
        byte[] outArray = outByteStream.toByteArray();
        response.setContentType("application/ms-excel");
        response.setContentLength(outArray.length);
        response.setHeader("Expires:", "0"); // eliminates browser caching
        response.setHeader("Content-Disposition", "attachment; filename=confirmationxls.xls");
        response.getOutputStream().write(outArray);
        response.getOutputStream().flush();
    }

    /**
     * @description Given a row index (i) insert a key value pair into an Excel spreadsheet.
     * @param i
     * @param wb
     * @param sheet
     * @param firstcellcon
     * @param secondcellcon
     * @return 
     */
    public int insertRow(int i, HSSFWorkbook wb, HSSFSheet sheet, String firstcellcon, String secondcellcon)
    {
        HSSFRow row = sheet.createRow(i);
 
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(firstcellcon);
            
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(secondcellcon); 
        
        return i+1;
    }

    
    /**
     * @description Obtain the form content and transpose into a byte stream
     * that is then recorded to a PDF.
     * @param request
     * @param response
     * @throws DocumentException
     * @throws IOException
     */
    public void downloadAsPDF(HttpServletRequest request, HttpServletResponse response)
            throws DocumentException, IOException {

        Document document = new Document();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, buffer);
        String name = request.getParameter("name");
        String status = request.getParameter("status");
        String total = request.getParameter("total");
        String cost = request.getParameter("cost");

        // Build the HTML string from the form values then feed this into the HTML parser:
        String html = "<html><head></head>\n"
                + "            <h3>JOHNS HOPKINS ANNUAL DEVELOPMENT SEMINAR</h3></br>\n"
                + "            <div>Registration file for: <b>" + name + "</b></div>\n"
                + "            <div>You are registered as a <b>" + status + "</b></div>\n"
                + "            <table>\n"
                + "                <tr>\n"
                + "                    <th><b>Your Courses</b></th>\n"
                + "                    <th><b>Cost</b></th>\n"
                + "                </tr>\n"
                + "                \n";

        // Gather all the courses. The cost will always be the same for each course taught. Guest speakers are free:
        String[] courses = request.getParameterValues("courses");
        if (courses != null) {
            for (String course : courses) {
                html += "               <tr>\n"
                        + "                    <td>" + course + "</td>\n"
                        + "                    <td>" + cost + "</td>\n"
                        + "                </tr>\n";
            }
        }

        String[] accomodations = request.getParameterValues("accomodations");
        String[] accomodationCosts = request.getParameterValues("accomodationCosts");
        // We know that the accomodations and their costs are pairs and will always have the same index.
        // These two lists are therefore the same length;
        if (accomodations != null) {
            for (int i = 0; i < accomodations.length; i++) {
                html += "               <tr>\n"
                        + "                    <td>" + accomodations[i] + "</td>\n"
                        + "                    <td>" + accomodationCosts[i] + "</td>\n"
                        + "                </tr>\n";
            }
        }

        html += "                <tr>\n"
                + "                    <td><hr></hr><td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <th><b>Total</b></th><td id=\"total\">" + total + "</td>\n"
                + "                </tr>\n"
                + "            </table>"
                + "<body></body></html>";
        document.open();
        HTMLWorker htmlWorker = new HTMLWorker(document);
        htmlWorker.parse(new StringReader(html));
        document.close();

        DataOutput output = new DataOutputStream(response.getOutputStream());
        byte[] bytes = buffer.toByteArray();
        response.setContentLength(bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            output.writeByte(bytes[i]);
        }
        // Force the bytes through:
        response.getOutputStream().flush();
        LOGGER.log(Level.INFO, "PDF bytes written out to browser for downloading.");
    }
}
