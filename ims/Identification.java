/*Vmap - A Program for creating and viewing ePortfolios
/*$Id: Identification.java,v 1.5 2005/04/12 17:50:51 veghead Exp $ */

package ims;
import java.util.Enumeration;
import java.util.Vector;
import vmap.main.XMLElement;

public class Identification {

    public String formname=null;
    public String street=null;
    public String locality=null;
    public String city=null;
    public String statepr=null;
    public String country=null;
    public String postcode=null;
    public String comment=null;


    public Identification() {
        formname=new String("");
        comment=new String("");
        street=new String("");
        locality=new String("");
        city=new String("");
        statepr=new String("");
        country=new String("");
        postcode=new String("");
    }


    public void setFormname(String formname) {
        this.formname=formname;
    }

    public String getFormname() {
        return(this.formname);
    }


    /**
     * Set the identification structure from an
     * XMLElement block
     * @param xml The identification as read into
     * and XMLElement block.
     */
    public void setXML(XMLElement xml) {
        XMLElement x;
        Vector c = xml.getChildren();
        // walk through the children
        for (int i=0;i<c.size();i++) {
            x=(XMLElement)c.get(i);
            if (x.getName().equals("identification")) {
                Vector d = x.getChildren();
                for (int j=0;j<d.size();j++) {
                    XMLElement y=(XMLElement)d.get(j);
                    if (y.getName().equals("address")) {
                        setAddressXML(y);
                    } else if (y.getName().equals("formname")) {
                        setFormNameXML(y);
                    } else if (y.getName().equals("comment")) {
                        comment=y.getContent();
                    }
                }
            }
        }
    }


    /**
     * Set the formname from an
     * XMLElement address structure
     * @param xml The formname as read into
     * an XMLElement block.
     */
    public void setFormNameXML(XMLElement xml) {
        Vector c = xml.getChildren();
        for (int i=0;i<c.size();i++) {
            XMLElement x=(XMLElement)c.get(i);
            if (x.getName().equals("text")) {
                formname=x.getContent();
            }
        }
    }


    /**
     * Set the address structure from an
     * XMLElement address structure
     * @param xml The address as read into
     * an XMLElement block.
     */
    public void setAddressXML(XMLElement xml) {
        Vector c = xml.getChildren();
        // walk through the children
        for (int i=0;i<c.size();i++) {
            XMLElement x=(XMLElement)c.get(i);
            if (x.getName().equals("street")) {
                street=x.getContent();
            } else if (x.getName().equals("locality")) {
                locality=x.getContent();
            } else if (x.getName().equals("city")) {
                city=x.getContent();
            } else if (x.getName().equals("country")) {
                country=x.getContent();
            } else if (x.getName().equals("statepr")) {
                statepr=x.getContent();
            } else if (x.getName().equals("postcode")) {
                postcode=x.getContent();
            }
        }
    }


    /**
     * Get the identification structure as XML
     * @returns the identification block as 
     *   XMLElements
     */
    public XMLElement getXML() {
        XMLElement top=new XMLElement();
        top.setName("identification");
        XMLElement xformname=new XMLElement();
        xformname.setName("formname");
        XMLElement xformnamet=new XMLElement();
        xformnamet.setName("text");
        xformnamet.setContent(formname);
        xformname.addChild(xformnamet);

        XMLElement xcomment=new XMLElement();
        xcomment.setName("comment");
        xcomment.setContent(comment);
        top.addChild(xcomment);


        XMLElement xaddress=new XMLElement();
        xaddress.setName("address");

        LIPTypeName typename=new LIPTypeName("imsdefault","Mailing");
        XMLElement xtypename=typename.getXML();
        xaddress.addChild(xtypename);
        
        XMLElement xstreet=new XMLElement();
        xstreet.setName("street");
        xstreet.setContent(street);
        xaddress.addChild(xstreet);
        
        XMLElement xlocality=new XMLElement();
        xlocality.setName("locality");
        xlocality.setContent(locality);
        xaddress.addChild(xlocality);

        XMLElement xcity=new XMLElement();
        xcity.setName("city");
        xcity.setContent(city);
        xaddress.addChild(xcity);

        XMLElement xstatepr=new XMLElement();
        xstatepr.setName("statepr");
        xstatepr.setContent(statepr);
        xaddress.addChild(xstatepr);

        XMLElement xcountry=new XMLElement();
        xcountry.setName("country");
        xcountry.setContent(country);
        xaddress.addChild(xcountry);

        XMLElement xpostcode=new XMLElement();
        xpostcode.setName("postcode");
        xpostcode.setContent(postcode);
        xaddress.addChild(xpostcode);

        top.addChild(xformname);
        top.addChild(xaddress);

        return(top);
    }
}
   
