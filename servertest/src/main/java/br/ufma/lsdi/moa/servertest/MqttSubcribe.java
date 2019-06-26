package br.ufma.lsdi.moa.servertest;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import br.ufma.lsdi.moa.servertest.moa.ClassifierData;
import br.ufma.lsdi.moa.servertest.moa.GeneratedData;
import br.ufma.lsdi.moa.servertest.mqtt.ConfMqttBroker;



public class MqttSubcribe implements Runnable,IMqttMessageListener {
	
    private String topic        = "MOA Teste";

   // private String broker       = "tcp://iot.eclipse.org:1883";
    
    private String clientId     = UUID.randomUUID().toString();
    private MemoryPersistence persistence = new MemoryPersistence();

	private ClassifierData classifier;
	String ip;
	
	public MqttSubcribe(String ip){
		this.ip=ip;
	}
	
    public void connect() {
    	classifier=new ClassifierData();
    	classifier.start();

        try {
        	String location="tcp://"+ip+":1883";
        	System.out.println("location:"+location);
            MqttClient sampleClient = new MqttClient(location, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(true);
            connOpts.setConnectionTimeout(10);
            System.out.println("Connecting to broker: "+ConfMqttBroker.broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
   
            
            sampleClient.subscribe(topic, this);
           
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
    
	public void run() {
		connect();
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		try {
			System.out.println(">>>"+message.getId());
			String str=new String(message.getPayload());
			System.out.println("<<"+str);
			String dadosStr[]=str.substring(1, str.length()-2).split(",");
			int class_id=classifier.classifier(GeneratedData.getInstance(dadosStr, 10));
			System.out.println(class_id+"<<"+classifier.acurracy());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
