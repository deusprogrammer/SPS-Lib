/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpbroadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client
{
    private int port = 8888;
    private InetAddress broadcast;
    private DatagramSocket socket;
    DatagramPacket packet;

    public void run() {
        try {
            broadcast = InetAddress.getByName("192.168.2.255");
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            
            packet = new DatagramPacket("1000000000000000".getBytes(), 16, broadcast, port);
            System.out.println("Sending discovery packet.");
            socket.send(packet);
            
            System.out.println("Waiting for ip return.");
            packet = new DatagramPacket(new byte[16], 16);
            socket.receive(packet);
                        
            String ipAddress = new String(packet.getData());
            System.out.println(ipAddress);

            packet = new DatagramPacket("2010000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending activate.");
            socket.send(packet);
            
            Thread.sleep(5000);
            
            packet = new DatagramPacket("3010000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending deactivate.");
            socket.send(packet);
            
            Thread.sleep(5000);
            
            packet = new DatagramPacket("2020000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending activate.");
            socket.send(packet);
            
            Thread.sleep(5000);
            
            packet = new DatagramPacket("3020000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending deactivate.");
            socket.send(packet);
            
            Thread.sleep(5000);
            
            packet = new DatagramPacket("2030000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending activate.");
            socket.send(packet);
            
            Thread.sleep(5000);
            
            packet = new DatagramPacket("3030000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending deactivate.");
            socket.send(packet);
            
            Thread.sleep(5000);
            
            packet = new DatagramPacket("2040000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending activate.");
            socket.send(packet);
            
            Thread.sleep(5000);
            
            packet = new DatagramPacket("3040000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending deactivate.");
            socket.send(packet);
            
            packet = new DatagramPacket("4000000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending set mac.");
            socket.send(packet);
            
            packet = new DatagramPacket("5000000000000000".getBytes(), 16, InetAddress.getByName(ipAddress), port);
            System.out.println("Sending get mac.");
            socket.send(packet);
            
            System.out.println("Waiting for mac return.");
            packet = new DatagramPacket(new byte[32], 32);
            socket.receive(packet);
            
            String mac = new String(packet.getData());
            System.out.println(mac);
            
            socket.close ();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}