package myclass.function;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

public class MyHttpGet {
    private static Logger logger = LogManager.getLogger(MyHttpGet.class);
	protected static final int SLEEP_TIME = 1200;
	protected static final int RETRY_COUNT = 10;
	
    @SuppressWarnings("unused")
	private static MyHttpGet instance = new MyHttpGet();
	
    public static String getResponseXml(String requestUrl) throws Exception{
        String responseXml = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        for(int i = 0; i < RETRY_COUNT; i++){
            try{
                doc = db.parse(requestUrl);
                break;
            }catch(java.io.IOException e){
                Thread.sleep(SLEEP_TIME);
            }
        }
        DOMSource source = new DOMSource(doc);
        StringWriter swriter = new StringWriter();
        StreamResult result = new StreamResult(swriter);
        transform(source, result);
        responseXml = swriter.toString();
        
        return responseXml;
    }
    
    private static void transform(Source source, Result result) throws TransformerException{
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf = tff.newTransformer();
        tf.transform(source, result);
    }
    
    public static org.jsoup.nodes.Document getAsinListFromHtml(String requestUrl) throws Exception{
    	org.jsoup.nodes.Document document = null;
    	for(int i = 0; i < RETRY_COUNT; i++){
			try {
				document = Jsoup.connect(requestUrl).get();
		    	logger.debug("requestUrl {}", requestUrl);
				break;
			} catch (IOException e) {
				Thread.sleep(SLEEP_TIME);
			}
        }
    	return document;
    }
}
