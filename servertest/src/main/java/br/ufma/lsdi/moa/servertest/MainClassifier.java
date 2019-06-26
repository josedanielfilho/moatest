package br.ufma.lsdi.moa.servertest;

public class MainClassifier {
	public static void main(String[] args) {
		String ip=args[0];
		System.out.println("ip="+ip);
	
		new Thread(new MqttSubcribe(ip)).start();
	}
}
