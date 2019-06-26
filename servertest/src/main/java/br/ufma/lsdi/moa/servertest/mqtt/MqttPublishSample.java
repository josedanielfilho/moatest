package br.ufma.lsdi.moa.servertest.mqtt;

import java.util.Arrays;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.yahoo.labs.samoa.instances.Instance;

import br.ufma.lsdi.moa.servertest.moa.GeneratedData;

public class MqttPublishSample {
    private String topic        = "MOA Teste";
    private String content      = "Message from MqttPublishSample";
    private int qos             = 2;
 
    
    private String clientId      = UUID.randomUUID().toString();
    private MemoryPersistence persistence = new MemoryPersistence();
	private GeneratedData generatedData;
    
	public void connect() {
		generatedData=new GeneratedData();
		generatedData.start();

        try {
            MqttClient sampleClient = new MqttClient(ConfMqttBroker.broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(true);
            connOpts.setConnectionTimeout(10);
            System.out.println("Connecting to broker: "+ConfMqttBroker.broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            
           for (int i = 0; i < 1000000; i++) {
        	   Instance instance = generatedData.nextInstance();
        	   double dados[]=GeneratedData.getData(instance, instance.numAttributes());
        	   String str=Arrays.toString(dados);
        	   
        	   MqttMessage message = new MqttMessage(str.getBytes());
               message.setQos(qos);
               sampleClient.publish(topic, message);
               System.out.println("Message published");
           }
            
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }


}
