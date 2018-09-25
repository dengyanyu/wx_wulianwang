package com.javen.mqtt;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttUtil {

    //tcp://MQTT安装的服务器地址:MQTT定义的端口号
    public static final String HOST = "tcp://47.94.80.80:1883";
    private MqttMessage message;
    public MqttClient client;
    private MqttTopic topic;
    private static MqttUtil service;

    static{
    	 service = new MqttUtil();
    }
    
    public MqttUtil() {
        try {
            client = new MqttClient(HOST, "weixin-server", new MemoryPersistence());
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    //发布消息
    public void publish(MqttTopic topic, MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        //打印发送状态
        System.out.println("发送完毕状态是:" + token.isComplete());
    }
    
    public static void main(String[] args) throws MqttException, UnsupportedEncodingException {
       
    	String msg = "1";
    	String topic = "jd";
       
        send(msg, topic);
        
    }
	public static void send(String topic,String msg ) throws UnsupportedEncodingException, MqttException {
		//获取一个主题
		service.topic = service.client.getTopic(topic);
    	//创建消息
        service.message = new MqttMessage();
		service.message.setPayload(msg.getBytes("UTF-8"));//设置消息内容
        service.publish(service.topic, service.message);
	}

}