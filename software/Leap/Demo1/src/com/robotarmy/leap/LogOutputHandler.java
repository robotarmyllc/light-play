/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.openide.windows.IOColorPrint;
import org.openide.windows.InputOutput;

/**
 *
 * @author mark
 */
public class LogOutputHandler extends Handler {
    private final InputOutput io;

    public LogOutputHandler(InputOutput io) {
        super();
        this.io = io;
        this.setFormatter(new LogOutputFormatter());
    }

    @Override
    public void publish(LogRecord record) {
        String message = getFormatter().format(record);
        try {
            if (record.getLevel() == Level.SEVERE) {
                IOColorPrint.print(io, message, new Color(200,0,0));
            } else if (record.getLevel() == Level.WARNING) {
                IOColorPrint.print(io, message, new Color(200,150,0));
            } else if (record.getLevel() == Level.CONFIG) {
                IOColorPrint.print(io, message, Color.DARK_GRAY);
            } else if (record.getLevel() == Level.FINE) {
                IOColorPrint.print(io, message, Color.GRAY);
            } else if (record.getLevel() == Level.FINER) {
                IOColorPrint.print(io, message, Color.LIGHT_GRAY);
            } else if (record.getLevel() == Level.FINEST) {
                IOColorPrint.print(io, message, Color.BLUE);
            } else {
                io.getOut().write(message);
            }
        } catch (IOException ex) {
            io.getOut().write("! " + message);
        } catch (NullPointerException ex) {
            // IO was nulled out.
        }
    }

    @Override
    public void flush() {
        io.getOut().flush();
    }

    @Override
    public void close() throws SecurityException {
        io.getOut().close();
    }
}