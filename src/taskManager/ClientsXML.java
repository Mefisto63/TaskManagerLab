package taskManager;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sergey on 30.01.15.
 */
public class ClientsXML {
    public synchronized void addClient(String login, String pass) throws JDOMException, IOException {
        SAXBuilder parser = new SAXBuilder();
        Document document = parser.build("Clients.xml");
        if (checkLogin(login, document)) {
            Element element = new Element("client");
            element.addContent(new Element("login").addContent(login));
            element.addContent(new Element("pass").addContent(pass));
            document.getRootElement().addContent(element);
            Format format = Format.getPrettyFormat();
            XMLOutputter out = new XMLOutputter(format);
            out.output(document, new FileOutputStream(new File("Clients.xml")));
        } else
            throw new IOException("Такой логин занят");
    }

    public synchronized boolean checkClient(String login, String pass) throws JDOMException, IOException {
        boolean flag = false;
        SAXBuilder parser = new SAXBuilder();
        Document document = parser.build("Clients.xml");
        List elements = document.getRootElement().getContent(new ElementFilter("client"));
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()){
            Element  element = (Element) iterator.next();
            if (login.equals(element.getChildText("login")) && pass.equals(element.getChildText("pass")))
                flag = true;
        }
        return flag;
    }

    private boolean checkLogin(String login, Document document){
        boolean flag = true;
        List elements = document.getRootElement().getContent(new ElementFilter("client"));
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()){
            Element  element = (Element) iterator.next();
            if (login.equals(element.getChildText("login")))
                flag = false;
        }
        return flag;
    }
}
