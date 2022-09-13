/*
 * Test.java
 */

import com.juanjo.openDmx.OpenDmx;
import java.util.Arrays;

/**
 * The HelloWorldApp class implements an application that
 * simply displays "Hello World!" to the standard output.
 */
class Test {
//    static {
//        System.loadLibrary("lpdmx"); // Load native library at runtime
//        // hello.dll (Windows) or libhello.so (Unixes)
//    }
    
    public static void main(String[] args) {
        int universe = 0;
        System.out.println("Light Play DMX Test"); //Display the string.
        OpenDmx dmx = new OpenDmx();
        
        String[] list = dmx.getList();
        for ( String item: list){
            System.out.println(item);
        }
        
        String r232 = "FTDPERZJ";
        String r422 = "FTXQCHMH";
        String ss = r232;
        
        
        int handle = dmx.connect(ss, universe, OpenDmx.OPENDMX_TX);
        System.out.println(ss + " got handle number " + handle );

        
        for (int pass=0; pass<10; pass++) {
            for ( int i=0; i<3; i++) {
                cycleChannel(i, dmx);
            }
        }
        
        
        dmx.disconnect(handle);
        //System.out.println(Arrays.toString(dmx.getList()));

        
        
    }
    
    private static void cycleChannel(int ch, OpenDmx dmx) {
        int i=0;
        for ( i=0; i < 256; i++) {
            dmx.setValue(ch, i);
            try {
                Thread.sleep(1);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        for ( i=255; i > 0; i--) {
            dmx.setValue(ch, i);
            try {
                Thread.sleep(1);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
