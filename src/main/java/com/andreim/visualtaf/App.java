package com.andreim.visualtaf;

import com.andreim.licensemanagement.LicenseMgmtClient;
import com.andreim.licensemanagement.StringXORer;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Hello world!
 *
 */
public class App {

    static MainFrame frm;
    static DefaultMutableTreeNode runningTestSetNode;
    static Enumeration enTestsToRun;
    static Document doc;
    static Element rootElement;
    static int globalpassedtc;
    static int globalfailedtc;

    static StringBuilder htmlReport;
    private static int traverseSuiteNum;
    private static int traverseTCNum;

    private static final Object syncObject_threadCount = new Object();
    private static int threadCount;

    

    public static void main(String[] args) throws Exception {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        frm = new MainFrame();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);

    }

    static void processExcelFile(String fileToProcess) throws FileNotFoundException, IOException, TransformerConfigurationException, TransformerException {

        FileInputStream inputStream = new FileInputStream(new File(fileToProcess));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet instrSheet = workbook.getSheet("Instructions"); //workbook.getSheetAt(0);

        // suite element
        Element elemSuite;
        Attr attr;
        synchronized (doc) {
            elemSuite = doc.createElement("suite");
            // set attribute to staff element
            attr = doc.createAttribute("name");
            attr.setValue(new File(fileToProcess).getName());
            elemSuite.setAttributeNode(attr);
            rootElement.appendChild(elemSuite);
        }

        Map<String, Integer> colNamesMap = new HashMap<>();
        Iterator<Cell> cellIterator = instrSheet.getRow(instrSheet.getFirstRowNum()).cellIterator();
        while (cellIterator.hasNext()) {
            Cell nextCell = cellIterator.next();
            colNamesMap.put(nextCell.getStringCellValue().toLowerCase(), nextCell.getColumnIndex());
        }

        Element elemTC = elemSuite;
        boolean flagSkipKeywordsInTheSameTC = false;
        int passedtc = 0, failedtc = 0;
        for (int i = instrSheet.getFirstRowNum() + 1; i <= instrSheet.getLastRowNum(); i++) {

            if (instrSheet.getRow(i) == null) {
                continue;
            }

            String tcname = instrSheet.getRow(i).getCell(colNamesMap.get("tcname")) != null ? instrSheet.getRow(i).getCell(colNamesMap.get("tcname")).getStringCellValue() : "";
            String keyword = instrSheet.getRow(i).getCell(colNamesMap.get("keyword")) != null ? instrSheet.getRow(i).getCell(colNamesMap.get("keyword")).getStringCellValue() : "";
            String input = instrSheet.getRow(i).getCell(colNamesMap.get("input")) != null ? instrSheet.getRow(i).getCell(colNamesMap.get("input")).getStringCellValue() : "";
            String expectedresult = instrSheet.getRow(i).getCell(colNamesMap.get("expectedresult")) != null ? instrSheet.getRow(i).getCell(colNamesMap.get("expectedresult")).getStringCellValue() : "";
            if (!tcname.isEmpty()) {

                if (tcname.matches("(?i)^comment|##")) {
                    continue;
                }

                if (tcname.matches("(?i)^end")) {

                    if (!flagSkipKeywordsInTheSameTC) {
                        synchronized (doc) {
                            Attr attrStatus = doc.createAttribute("status");
                            attrStatus.setValue("PASSED");
                            elemTC.setAttributeNode(attrStatus);
                        }

                        passedtc++;
                    } else {
                        failedtc++;
                    }

                    flagSkipKeywordsInTheSameTC = false;
                    elemTC = elemSuite;
                    continue;
                }

                flagSkipKeywordsInTheSameTC = false;

                synchronized (doc) {
                    elemTC = doc.createElement("testcase");

                    // set attribute to staff element
                    attr = doc.createAttribute("name");
                    attr.setValue(tcname);
                    elemTC.setAttributeNode(attr);
                    elemSuite.appendChild(elemTC);
                }

            } else if (!keyword.isEmpty()) {

                if (keyword.matches("(?i)^exit$")) {
                    
                    if( elemTC.getNodeName().equalsIgnoreCase("testcase") ) {
                        elemTC.setAttribute("status", "PASSED");
                        passedtc++;
                    }    
                    break;
                }

                if (flagSkipKeywordsInTheSameTC) {
                    continue;
                }

                Element elemStep = null;
                try {

                    synchronized (doc) {
                        elemStep = doc.createElement("step");
                        elemTC.appendChild(elemStep);
                        attr = doc.createAttribute("name");
                        attr.setValue(keyword);
                        elemStep.setAttributeNode(attr);
                        elemStep.setAttribute("input", input);
                    }

                    //parameter resolution
                    Object kparam = null;
                    if (input.contains(":")) {
                        HashMap<String, String> hm = new HashMap<String, String>();

                        String refSheetName = input.split(":")[0];
                        int rowNum = Integer.parseInt(input.split(":")[1]);
                        Sheet referencedSheet = workbook.getSheet(refSheetName);
                        if (referencedSheet == null) {
                            throw new Exception("no such datasheet '" + refSheetName + "' exists in the workbook");
                        }
                        if (referencedSheet.getRow(rowNum - 1) == null) {
                            throw new Exception("row '" + rowNum + "' is empty, no data");
                        }

                        Iterator<Cell> iter = referencedSheet.getRow(rowNum - 1).cellIterator();
                        while (iter.hasNext()) {
                            Cell c = iter.next();
                            String cellValue;
                            c.setCellType(Cell.CELL_TYPE_STRING);
                            cellValue = c.getStringCellValue();

                            hm.put(referencedSheet.getRow(0).getCell(c.getColumnIndex()).getStringCellValue().toLowerCase(),
                                    cellValue);
                        }

                        kparam = hm;
                    } else if (input.contains("=")) {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        for (String param : input.split(";")) {
                            hm.put(param.split("=")[0].toLowerCase(), param.split("=")[1]);
                        }
                        kparam = hm;
                    }

                    //invocation
                    String actualResult = executeKeyword("Keywords." + keyword, kparam);

                    if (expectedresult.isEmpty() || expectedresult.matches(actualResult)) {

                        synchronized (doc) {
                            Attr attrStatus = doc.createAttribute("status");
                            attrStatus.setValue("PASSED");
                            elemStep.setAttributeNode(attrStatus);
                        }

                    } else {
                        synchronized (doc) {
                            Attr attrStatus = doc.createAttribute("status");
                            attrStatus.setValue("FAILED");
                            elemStep.setAttributeNode(attrStatus);
                            Attr attrError = doc.createAttribute("t_error");
                            attrError.setValue("Actual value: '" + actualResult + "' mismatches expected result: '" + expectedresult + "'");
                            elemStep.setAttributeNode(attrError);

                            attrStatus = doc.createAttribute("status");
                            attrStatus.setValue("FAILED");
                            elemTC.setAttributeNode(attrStatus);
                        }

                        //skip all following keywords in the same test case
                        flagSkipKeywordsInTheSameTC = true;
                    }

                } catch (Exception ex) {
                    if (ex instanceof InvocationTargetException) {
                        ex = (Exception) ((InvocationTargetException) ex).getTargetException();
                    }

                    System.out.println("Line: " + i + " failed, keyword: " + keyword + ", " + ex.getClass().getName() + " " + ex.getMessage());

                    synchronized (doc) {
                        Attr attrStatus = doc.createAttribute("status");
                        attrStatus.setValue("FAILED");
                        elemStep.setAttributeNode(attrStatus);

                        Attr attrError = doc.createAttribute("t_error");
                        attrError.setValue("Line: " + i + " failed, " + ex.getClass().getName() + " " + ex.getMessage());
                        elemStep.setAttributeNode(attrError);

                        attrStatus = doc.createAttribute("status");
                        attrStatus.setValue("FAILED");
                        elemTC.setAttributeNode(attrStatus);
                    }

                    //skip all following keywords in the same test case
                    flagSkipKeywordsInTheSameTC = true;

                }//catch

            }//keyword

        }//rows

        synchronized (doc) {
            attr = doc.createAttribute("passedtc");
            attr.setValue(String.valueOf(passedtc));
            elemSuite.setAttributeNode(attr);
            attr = doc.createAttribute("failedtc");
            attr.setValue(String.valueOf(failedtc));
            elemSuite.setAttributeNode(attr);

            globalpassedtc += passedtc;
            globalfailedtc += failedtc;
        }
    }

    private static String executeKeyword(String keyword, Object kparam) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

        String[] commandArr = keyword.split("\\.");
        String className = "";
        for (int i = 0; i < commandArr.length - 1; i++) {
            className += commandArr[i] + ".";
        }
        className = className.substring(0, className.length() - 1);
        String methodName = commandArr[commandArr.length - 1];

        Class cls = Class.forName(className);
        Object obj = cls.newInstance();

        Class[] methClassArr = {};
        if (kparam != null) {
            methClassArr = new Class[]{kparam.getClass()};

        }

        Method method = cls.getDeclaredMethod(methodName, methClassArr);

        if (kparam == null) {
            return (String) method.invoke(obj);
        } else {
            return (String) method.invoke(obj, kparam);
        }
    }

    private static String executeKeyword(String keyword, HashMap<String, String> input) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

        String[] commandArr = keyword.split("\\.");
        String className = "";
        for (int i = 0; i < commandArr.length - 1; i++) {
            className += commandArr[i] + ".";
        }
        className = className.substring(0, className.length() - 1);
        String methodName = commandArr[commandArr.length - 1];

        Class cls = Class.forName(className);
        Object obj = cls.newInstance();

        Class[] methClassArr = {HashMap.class};
//        String[] argsArr = input.split(";");
//        if (!argsArr[0].isEmpty()) {
//            methClassArr = new Class[argsArr.length];
//            for (int i = 0; i < methClassArr.length; i++) {
//                methClassArr[i] = String.class;
//            }
//        }

        Method method = cls.getDeclaredMethod(methodName, methClassArr);

        return (String) method.invoke(obj, input);
    }

    private static String executeKeyword(String keyword, String input) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

//        String command = "Ticker.Search.test3";
//        String methargs = "user1;pwd1";
//        String command = "Ticker.Search.test4";
//        String methargs = "user1;pwd1;email@yahoo.com";
//        String command = "Ticker.Search.enterTicker";
//        String methargs = "";
        String[] commandArr = keyword.split("\\.");
        String className = "";
        for (int i = 0; i < commandArr.length - 1; i++) {
            className += commandArr[i] + ".";
        }
        className = className.substring(0, className.length() - 1);
        String methodName = commandArr[commandArr.length - 1];

        Class cls = Class.forName(className);
        Object obj = cls.newInstance();

        Class[] methClassArr = {};
        String[] argsArr = input.split(";");
        if (!argsArr[0].isEmpty()) {
            methClassArr = new Class[argsArr.length];
            for (int i = 0; i < methClassArr.length; i++) {
                methClassArr[i] = String.class;
            }
        }

        Method method = cls.getDeclaredMethod(methodName, methClassArr);
//        Object[] varargs = new Object[] { "user1", "pwd1" };
//        method.invoke(obj, varargs);        

        return (String) method.invoke(obj, argsArr[0].isEmpty() ? null : argsArr);
    }

    public static void addJarToClasspath(String s) throws Exception {
        File f = new File(s);
        URI u = f.toURI();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(urlClassLoader, new Object[]{u.toURL()});
    }

    public static DefaultMutableTreeNode findNodeInDescendants(DefaultMutableTreeNode root, String s) {
        //@SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            if (node.toString().equalsIgnoreCase(s)) {
                return node; //new TreePath(node.getPath());
            }
        }
        return null;
    }

    static void executeTestSet() {

        synchronized (syncObject_threadCount) {
            threadCount++;
        }

        while (true) {

            try {

                String fileToProcess;
                synchronized (enTestsToRun) {
                    if (enTestsToRun.hasMoreElements()) {
                        fileToProcess = ((FileItem) ((DefaultMutableTreeNode) enTestsToRun.nextElement()).getUserObject()).path;
                    } else {
                        break;
                    }
                }

                logToPanel("   Processing: " + new File(fileToProcess).getName());

                processExcelFile(fileToProcess);

            } catch (Exception ex) {
                ex.printStackTrace();
                // System.out.println(ex.getMessage());

                logToPanel("Error: " + ex.getMessage());
            }

        }//

        int copyThreadCount;
        synchronized (syncObject_threadCount) {
            threadCount--;
            copyThreadCount = threadCount;
        }

        if (copyThreadCount == 0) {
            Attr attr = doc.createAttribute("passedtc");
            attr.setValue(String.valueOf(globalpassedtc));
            rootElement.setAttributeNode(attr);
            attr = doc.createAttribute("failedtc");
            attr.setValue(String.valueOf(globalfailedtc));
            rootElement.setAttributeNode(attr);
            rootElement.setAttribute("finishtime", getCurrentTimestampForFile());

            logToPanel("Finished running test set.");

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            String resultsFile = null;
            try {
                //lic validation
                synchronized (lmc) {

                    if (!App.lmc.validateLicenseOrTrailPeriod2()) {
                    //Logger.getGlobal().severe("License or trial period invalid");
                        //System.exit(1);
                        int lll = 7 / (3 - 2 - 1);
                    }
                }

                transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                resultsFile = "TestResults/" + App.runningTestSetNode + "-Run-" + getCurrentTimestampForFile();
                StreamResult result = new StreamResult(new File(resultsFile + ".xml"));
                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);
                transformer.transform(source, result);

                //prep HTML report
                traverseXMLDOMAndGenerateHtml(rootElement);
                htmlReport.append("</body></html>");

                PrintWriter out = new PrintWriter(resultsFile + ".html", "UTF-8");
                out.print(htmlReport.toString());
                out.close();
                logToPanel("Writing results to " + resultsFile+".html\n\n");
                
                JOptionPane.showMessageDialog(frm, "Finished running test set!\nClick OK to view run report");
                File htmlFile = new File(resultsFile + ".html");
                Desktop.getDesktop().browse(htmlFile.toURI());

                //Runtime.getRuntime().exec("cmd.exe start " + resultsFile);
            } catch (Exception ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void traverseXMLDOMAndGenerateHtml(Node node) {
        // do something with the current node instead of System.out
        //System.out.println(node.getNodeName());

        if (node.getNodeName().equalsIgnoreCase("run")) {
            htmlReport.append(
                    "<h1>"
                    + "Test Set: " + node.getAttributes().getNamedItem("testsetname").getNodeValue()
                    + "</h1>"
            );

            htmlReport.append(
                    "<h3>"
                    + "Start time: " + node.getAttributes().getNamedItem("starttime").getNodeValue() + ", "
                    + "Finish time: " + node.getAttributes().getNamedItem("finishtime").getNodeValue()
                    + "</h3>"
            );

            htmlReport.append(
                    "<h2>"
                    + "TC Totals - Passed: " + node.getAttributes().getNamedItem("passedtc").getNodeValue() + ", "
                    + "Failed: " + node.getAttributes().getNamedItem("failedtc").getNodeValue()
                    + "</h2>"
            );

        }

        if (node.getNodeName().equalsIgnoreCase("suite")) {
            traverseSuiteNum++;
            String suiteColorClass = "suitewarning";
            if (Integer.parseInt(node.getAttributes().getNamedItem("passedtc").getNodeValue()) >= 1
                    && Integer.parseInt(node.getAttributes().getNamedItem("failedtc").getNodeValue()) == 0) {
                suiteColorClass = "suiteallpassed";
            } else if (Integer.parseInt(node.getAttributes().getNamedItem("passedtc").getNodeValue()) == 0
                    && Integer.parseInt(node.getAttributes().getNamedItem("failedtc").getNodeValue()) >= 1) {
                suiteColorClass = "suiteallfailed";
            }

            htmlReport.append(
                    //"<div class='suite' id='" + node.hashCode() + "' onclick=\"$('.parent" + node.hashCode() + "').toggle()\">"
                    "<div class='suite' >"
                    + "<span class='" + suiteColorClass + "' onclick=\"$(this).nextAll().toggle()\">"
                    + " + " + node.getAttributes().getNamedItem("name").getNodeValue() + ", "
                    + "Passed: " + node.getAttributes().getNamedItem("passedtc").getNodeValue() + ", "
                    + "Failed: " + node.getAttributes().getNamedItem("failedtc").getNodeValue()
                    + "</span>"
            );
        }

        if (node.getNodeName().equalsIgnoreCase("testcase")) {
            traverseTCNum++;
            htmlReport.append("<div class='testcase' style='display:none'>");
            htmlReport.append(
                    "<span class='" + node.getAttributes().getNamedItem("status").getNodeValue() + "' onclick=\"$(this).nextAll().toggle()\">"
                    + " + " + node.getAttributes().getNamedItem("name").getNodeValue() + ", "
                    + node.getAttributes().getNamedItem("status").getNodeValue()
                    + "</span>"
            );

        }

        if (node.getNodeName().equalsIgnoreCase("step")) {

            htmlReport.append("<div class='step' style='display:none'>");
            htmlReport.append(
                    "<span class='" + node.getAttributes().getNamedItem("status").getNodeValue() + "' onclick=\"$(this).nextAll().toggle()\">"
                    + node.getAttributes().getNamedItem("name").getNodeValue() + ", "
                    + node.getAttributes().getNamedItem("input").getNodeValue() + ", "
                    + node.getAttributes().getNamedItem("status").getNodeValue() + ", "
                    + (node.getAttributes().getNamedItem("t_error") != null
                    ? node.getAttributes().getNamedItem("t_error").getNodeValue() : "")
                    + "</span>"
            );

        }

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);

            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                //calls this method for all the children which is Element
                traverseXMLDOMAndGenerateHtml(currentNode);
            }
        }

        htmlReport.append("</div>");
    }

    public static String getCurrentTimestampForFile() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

    public static void logToPanel(String msg) {
        synchronized (frm.jTextArea1) {
            frm.jTextArea1.append(msg + "\r\n");
        }

    }
}
