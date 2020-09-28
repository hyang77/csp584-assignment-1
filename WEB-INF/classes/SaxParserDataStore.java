
/*********


http://www.saxproject.org/

SAX is the Simple API for XML, originally a Java-only API. 
SAX was the first widely adopted API for XML in Java, and is a �de facto� standard. 
The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java. 

The following URL from Oracle is the JAVA documentation for the API

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html


*********/
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import  java.io.StringReader;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


////////////////////////////////////////////////////////////

/**************

SAX parser use callback function  to notify client object of the XML document structure. 
You should extend DefaultHandler and override the method when parsin the XML document

***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
	Tv tv;
	SoundSystem soundsystem;
	Phone phone;
//    Console console;
//    Game game;
//    Tablet tablet;
    Accessory accessory;
//    static HashMap<String,Console> consoles;
//    static HashMap<String,Game> games;
//    static HashMap<String,Tablet> tablets;
    static HashMap<String,Tv> tvs;
    static HashMap<String,SoundSystem> soundsystems;
    static HashMap<String,Phone> phones;
    static HashMap<String,Accessory> accessories;
    String consoleXmlFileName;
	HashMap<String,String> accessoryHashMap;
    String elementValueRead;
	String currentElement="";
    public SaxParserDataStore()
	{
	}
	public SaxParserDataStore(String consoleXmlFileName) {
    this.consoleXmlFileName = consoleXmlFileName;
//	consoles = new HashMap<String, Console>();
//	games=new  HashMap<String, Game>();
//	tablets=new HashMap<String, Tablet>();
    tvs = new HashMap<String, Tv>();
	soundsystems=new  HashMap<String, SoundSystem>();
	phones=new HashMap<String, Phone>();
	accessories=new HashMap<String, Accessory>();
	accessoryHashMap=new HashMap<String,String>();
	parseDocument();
    }

   //parse the xml using sax parser to get the data
    private void parseDocument() 
	{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try 
		{
	    SAXParser parser = factory.newSAXParser();
	    parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}

   

////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document. 
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.  
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////
	
	// when xml start element is parsed store the id into respective hashmap for console,games etc 
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("tv")) 
		{
			currentElement="tv";
			tv = new Tv();
            tv.setId(attributes.getValue("id"));
		}
        if (elementName.equalsIgnoreCase("soundsystem"))
		{
			currentElement="soundsystem";
			soundsystem = new SoundSystem();
            soundsystem.setId(attributes.getValue("id"));
        }
        if (elementName.equalsIgnoreCase("phone"))
		{
			currentElement="phone";
			phone= new Phone();
            phone.setId(attributes.getValue("id"));
        }
        if (elementName.equals("accessory") &&  !currentElement.equals("tv"))
		{
			currentElement="accessory";
			accessory=new Accessory();
			accessory.setId(attributes.getValue("id"));
	    }


    }
	// when xml end element is parsed store the data into respective hashmap for console,games etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equals("tv")) {
			tvs.put(tv.getId(),tv);
			return;
        }
 
        if (element.equals("soundsystem")) {	
			soundsystems.put(soundsystem.getId(),soundsystem);
			return;
        }
        if (element.equals("phone")) {	  
			phones.put(phone.getId(),phone);
			return;
        }
        if (element.equals("accessory") && currentElement.equals("accessory")) {
			accessories.put(accessory.getId(),accessory);       
			return; 
        }
		if (element.equals("accessory") && currentElement.equals("tv")) 
		{
			accessoryHashMap.put(elementValueRead,elementValueRead);
		}
      	if (element.equalsIgnoreCase("accessories") && currentElement.equals("tv")) {
			tv.setAccessories(accessoryHashMap);
			accessoryHashMap=new HashMap<String,String>();
			return;
		}
        if (element.equalsIgnoreCase("image")) {
		    if(currentElement.equals("tv"))
				tv.setImage(elementValueRead);
        	if(currentElement.equals("soundsystem"))
				soundsystem.setImage(elementValueRead);
            if(currentElement.equals("phone"))
				phone.setImage(elementValueRead);
            if(currentElement.equals("accessory"))
				accessory.setImage(elementValueRead);          
			return;
        }
        

		if (element.equalsIgnoreCase("discount")) {
            if(currentElement.equals("tv"))
				tv.setDiscount(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("soundsystem"))
				soundsystem.setDiscount(Double.parseDouble(elementValueRead));
            if(currentElement.equals("phone"))
				phone.setDiscount(Double.parseDouble(elementValueRead));
            if(currentElement.equals("accessory"))
				accessory.setDiscount(Double.parseDouble(elementValueRead));          
			return;
	    }


		if (element.equalsIgnoreCase("condition")) {
            if(currentElement.equals("tv"))
				tv.setCondition(elementValueRead);
        	if(currentElement.equals("soundsystem"))
				soundsystem.setCondition(elementValueRead);
            if(currentElement.equals("phone"))
				phone.setCondition(elementValueRead);
            if(currentElement.equals("accessory"))
				accessory.setCondition(elementValueRead);          
			return;  
		}

		if (element.equalsIgnoreCase("manufacturer")) {
            if(currentElement.equals("tv"))
				tv.setRetailer(elementValueRead);
        	if(currentElement.equals("soundsystem"))
				soundsystem.setRetailer(elementValueRead);
            if(currentElement.equals("phone"))
				phone.setRetailer(elementValueRead);
            if(currentElement.equals("accessory"))
				accessory.setRetailer(elementValueRead);          
			return;
		}

        if (element.equalsIgnoreCase("name")) {
            if(currentElement.equals("tv"))
				tv.setName(elementValueRead);
        	if(currentElement.equals("soundsystem"))
				soundsystem.setName(elementValueRead);
            if(currentElement.equals("phone"))
				phone.setName(elementValueRead);
            if(currentElement.equals("accessory"))
				accessory.setName(elementValueRead);          
			return;
	    }
	
        if(element.equalsIgnoreCase("price")){
			if(currentElement.equals("tv"))
				tv.setPrice(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("soundsystem"))
				soundsystem.setPrice(Double.parseDouble(elementValueRead));
            if(currentElement.equals("phone"))
				phone.setPrice(Double.parseDouble(elementValueRead));
            if(currentElement.equals("accessory"))
				accessory.setPrice(Double.parseDouble(elementValueRead));          
			return;
        }

	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////
	
    //call the constructor to parse the xml and get product details
    public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME+"/webapps/Assignment_1/ProductCatalog.xml");
    }

    public static void save() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            doc.setXmlStandalone(true);
            Element root = doc.createElementNS("", "ProductCatalog");
            doc.appendChild(root);

            // TvCatalog
            Element tvCatalog = doc.createElement("TvCatalog");
            for (Map.Entry<String,Tv> entry : tvs.entrySet()) {
                Element tvElement = doc.createElement("tv");
                String id = entry.getKey();
                Tv tv = entry.getValue();
                tvElement.setAttribute("id", id);
                tvElement.appendChild(createNodeWithText(doc, "name", tv.getName()));
                tvElement.appendChild(createNodeWithText(doc, "price", String.valueOf(tv.getPrice())));
                tvElement.appendChild(createNodeWithText(doc, "image", tv.getImage()));
                tvElement.appendChild(createNodeWithText(doc, "manufacturer", tv.getRetailer()));
                tvElement.appendChild(createNodeWithText(doc, "condition", tv.getCondition()));
                tvElement.appendChild(createNodeWithText(doc, "discount", String.valueOf(tv.getDiscount())));

                Element accessories = doc.createElement("accessories");
                for (Map.Entry<String, String> accessory :  tv.getAccessories().entrySet()) {
                    accessories.appendChild(createNodeWithText(doc, "accessory", accessory.getValue()));
                }
                tvElement.appendChild(accessories);
                tvCatalog.appendChild(tvElement);
            }

            root.appendChild(tvCatalog);

			// SoundSystemCatalog
			
			Element soundsystemCatalog = doc.createElement("SoundSystemCatalog");
            for (Map.Entry<String,SoundSystem> entry : soundsystems.entrySet()) {
                Element soundsystemElement = doc.createElement("soundsystem");
                String id = entry.getKey();
                SoundSystem soundsystem = entry.getValue();
                soundsystemElement.setAttribute("id", id);
                soundsystemElement.appendChild(createNodeWithText(doc, "name", soundsystem.getName()));
                soundsystemElement.appendChild(createNodeWithText(doc, "price", String.valueOf(soundsystem.getPrice())));
                soundsystemElement.appendChild(createNodeWithText(doc, "image", soundsystem.getImage()));
                soundsystemElement.appendChild(createNodeWithText(doc, "manufacturer", soundsystem.getRetailer()));
                soundsystemElement.appendChild(createNodeWithText(doc, "condition", soundsystem.getCondition()));
				soundsystemElement.appendChild(createNodeWithText(doc, "discount", String.valueOf(soundsystem.getDiscount())));
				
                soundsystemCatalog.appendChild(soundsystemElement);
            }

            root.appendChild(soundsystemCatalog);

			//PhoneCatalog
			
			Element phoneCatalog = doc.createElement("PhoneCatalog");
            for (Map.Entry<String,Phone> entry : phones.entrySet()) {
                Element phoneElement = doc.createElement("phone");
                String id = entry.getKey();
                Phone phone = entry.getValue();
                phoneElement.setAttribute("id", id);
                phoneElement.appendChild(createNodeWithText(doc, "name", phone.getName()));
                phoneElement.appendChild(createNodeWithText(doc, "price", String.valueOf(phone.getPrice())));
                phoneElement.appendChild(createNodeWithText(doc, "image", phone.getImage()));
                phoneElement.appendChild(createNodeWithText(doc, "manufacturer", phone.getRetailer()));
                phoneElement.appendChild(createNodeWithText(doc, "condition", phone.getCondition()));
				phoneElement.appendChild(createNodeWithText(doc, "discount", String.valueOf(phone.getDiscount())));
				
                phoneCatalog.appendChild(phoneElement);
            }

            root.appendChild(phoneCatalog);

			//AccessoryCatalog
			Element accessoryCatalog = doc.createElement("AccessoryCatalog");
            for (Map.Entry<String,Accessory> entry : accessories.entrySet()) {
                Element accessoryElement = doc.createElement("accessory");
                String id = entry.getKey();
                Accessory accessory = entry.getValue();
                accessoryElement.setAttribute("id", id);
                accessoryElement.appendChild(createNodeWithText(doc, "name", accessory.getName()));
                accessoryElement.appendChild(createNodeWithText(doc, "price", String.valueOf(accessory.getPrice())));
                accessoryElement.appendChild(createNodeWithText(doc, "image", accessory.getImage()));
                accessoryElement.appendChild(createNodeWithText(doc, "manufacturer", accessory.getRetailer()));
                accessoryElement.appendChild(createNodeWithText(doc, "condition", accessory.getCondition()));
				accessoryElement.appendChild(createNodeWithText(doc, "discount", String.valueOf(accessory.getDiscount())));
				
                accessoryCatalog.appendChild(accessoryElement);
            }

            root.appendChild(accessoryCatalog);

            // Save DOM XML to file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            String TOMCAT_HOME = System.getProperty("catalina.home");
            // Change to the same file name once completed
            FileWriter writer = new FileWriter(new File(TOMCAT_HOME+"/webapps/Assignment_1/ProductCatalog.xml"));
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);

            System.out.println("\nXML Saved Successfully..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Element createNodeWithText(Document doc, String tag, String value) {
        Element node = doc.createElement(tag);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
