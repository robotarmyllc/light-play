/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.project;

import com.robotarmy.dmx.InvalidAddressException;
import com.robotarmy.flow.object.AFlowObject;
import com.robotarmy.flow.object.DeltaObject;
import com.robotarmy.flow.object.PaletteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.Exceptions;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author mark
 */
public class FileParser {

    private static final Logger LOG = Logger.getLogger("Project");

    static void parsePalettes(Element element, AFlowObject flowObject) {
        LOG.setLevel(Level.FINEST);

        NodeList listOfPalettes = element.getElementsByTagName("palette");
        LOG.log(Level.CONFIG, "Palette Count: {0}", listOfPalettes.getLength());

        // Roots can have palettes and deltas.
        for (int s = 0; s < listOfPalettes.getLength(); s++) {
            org.w3c.dom.Node paletteNode = listOfPalettes.item(s);

            if (paletteNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                PaletteObject o = new PaletteObject(flowObject);
                //flowObject.addChild(o);

                Element e = (Element) paletteNode;
                LOG.log(Level.CONFIG, "Name: {0}", e.getAttribute("name"));
                o.setName(e.getAttribute("name"));
                LOG.log(Level.CONFIG, "Addr: {0}", e.getAttribute("addr"));
                o.setAddress(Integer.parseInt(e.getAttribute("addr")));
                LOG.log(Level.CONFIG, "   X: {0}", e.getAttribute("x"));
                o.setX(Integer.parseInt(e.getAttribute("x")));
                LOG.log(Level.CONFIG, "   Y: {0}", e.getAttribute("y"));
                o.setY(Integer.parseInt(e.getAttribute("y")));

                parseDeltas((Element) paletteNode, o);
            }
        }
    }

    static void parseDeltas(Element element, AFlowObject flowObject) {
        LOG.setLevel(Level.FINEST);

        NodeList list = element.getChildNodes();
        //NodeList list = element.getElementsByTagName("delta");
        LOG.log(Level.CONFIG, "Delta Count: {0}", list.getLength());

        // Roots can have palettes and deltas.
        for (int s = 0; s < list.getLength(); s++) {
            org.w3c.dom.Node node = list.item(s);

            if (node.getNodeName().equals("delta") && node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                DeltaObject o = new DeltaObject(flowObject);

                Element e = (Element) node;
                LOG.log(Level.CONFIG, "Name: {0}", e.getAttribute("name"));
                o.setName(e.getAttribute("name"));
                LOG.log(Level.CONFIG, "Addr: {0}", e.getAttribute("addr"));
                try {
                    o.getAddress().setAddress(handleInt(e.getAttribute("addr")));
                } catch (InvalidAddressException ex) {
                    Exceptions.printStackTrace(ex);
                }
                LOG.log(Level.CONFIG, "   X: {0}", e.getAttribute("x"));
                o.setX(handleInt(e.getAttribute("x")));
                LOG.log(Level.CONFIG, "   Y: {0}", e.getAttribute("y"));
                o.setY(handleInt(e.getAttribute("y")));
            }
        }
    }

    private static int handleInt( String toParse ) {
        try{
            return Integer.parseInt(toParse);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
