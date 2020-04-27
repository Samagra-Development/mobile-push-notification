package com.samagra.odktest.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class AutoFillTask extends AsyncTask<String, Void, Void> {


    String TAG = AutoFillTask.class.getName();
    private Context context;
    private AutoFillListener listener;
    int id;


    public AutoFillTask(AutoFillListener listener, int id){
        this.listener = listener;
        this.id = id;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String formName = strings[1];
        String udise = strings[0];
        updateStarterFile(udise, formName);
        return null;
    }

    private void updateStarterFile(String udise, String formName){
        FileOutputStream fos = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(formName));
            Element element = document.getDocumentElement();

            if(document.getElementsByTagName("udise").item(0).getChildNodes().getLength() > 0){
                document.getElementsByTagName("udise").item(0).getChildNodes().item(0).setNodeValue(udise);
            }else{
                document.getElementsByTagName("udise").item(0).appendChild(document.createTextNode(udise));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            fos = new FileOutputStream(new File(formName));
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);

            listener.onAutoFillSuccess(formName, id);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            listener.onAutoFillFailure(new Exception("Failed to autofill: " + formName + " \n" + e.getMessage()));
        } catch (SAXException e) {
            e.printStackTrace();
            listener.onAutoFillFailure(new Exception("Failed to autofill: " + formName + " \n" + e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            listener.onAutoFillFailure(new Exception("Failed to autofill: " + formName + " \n" + e.getMessage()));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            listener.onAutoFillFailure(new Exception("Failed to autofill: " + formName + " \n" + e.getMessage()));
        } catch (TransformerException e) {
            e.printStackTrace();
            listener.onAutoFillFailure(new Exception("Failed to autofill: " + formName + " \n" + e.getMessage()));
        }catch(Exception e){
            e.printStackTrace();
            listener.onAutoFillFailure(new Exception("Failed to autofill: " + formName + " \n" + e.getMessage()));
        }finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onAutoFillFailure(new Exception("Failed to autofill: " + formName + " \n" + e.getMessage()));
                }
            }
        }

    }
}
