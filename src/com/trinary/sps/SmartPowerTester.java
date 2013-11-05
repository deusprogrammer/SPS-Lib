/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinary.sps;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deusp_000
 */
public class SmartPowerTester {
    public static void main() {
        // Discover all smart power strips
        try {
            SmartPowerSystem.discover("192.168.2.255", 8888, 60 * 1000);
        } catch (SocketException ex) {
            Logger.getLogger(SmartPowerTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SmartPowerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Power on socket 1 on each powerStrip
        try {
            for (SmartPowerStrip strip : SmartPowerSystem.getPowerStrips()) {
                    strip.powerOnSocket(1);
            }
        } catch (SocketException ex) {
            Logger.getLogger(SmartPowerTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SmartPowerTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SmartPowerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}