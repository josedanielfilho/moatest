package br.ufma.lsdi.moa.servertest;

import br.ufma.lsdi.moa.servertest.mqtt.MqttSubcribe;

public class MainServer {
	public static void main(String[] args) {
		String ip=args[0];
		System.out.println("ip="+ip);
	
		new Thread(new MqttSubcribe(ip)).start();
	}
}
