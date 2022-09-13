/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author mark
 */
public class LogOutputFormatter  extends SimpleFormatter {

    @Override
    public synchronized String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        // sb.append(record.getLoggerName());  // Maybe you want to display logger name?
        
        // Prepend the message with the class and method name if Level.FINEST
        if (    record.getLevel().intValue() == Level.FINEST.intValue()
                && record.getSourceClassName() != null) {
            sb.append("[");
            sb.append(record.getSourceClassName());
            if (record.getSourceMethodName() != null) {
                sb.append(".");
                sb.append(record.getSourceMethodName());
                sb.append("]");
            }
            sb.append(": ");
        }

        String message = formatMessage(record);
        sb.append(message);
        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (Exception ex) {
            }
        }
        return sb.toString();

    }
}