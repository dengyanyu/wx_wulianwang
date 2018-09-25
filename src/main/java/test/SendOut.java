package test;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SendOut {

    //tcp://MQTT安装的服务器地址:MQTT定义的端口号
    public static final String HOST = "tcp://47.94.80.80:1883";
    //定义一个主题
    public static final String TOPIC = "topic11";
    //定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String clientid = "server11";
    private MqttMessage message;
    public static final String TOPIC1 = "topic1";
//    public static final String userName = "admin";
//    public static final String pwd = "password";
    public MqttClient client;
    private MqttTopic topic;

    public SendOut() {
        try {
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    //发布消息
    public void publish(MqttTopic topic, MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        //打印发送状态
        System.out.println("message is published completely!" + token.isComplete());
    }
    //建立连接：参数与订阅端相似
    private void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
//        options.setUserName(userName);
//        options.setPassword(pwd.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        client.setCallback(new PushCallback());
        client.connect(options);
    }
    public static void main(String[] args) throws MqttException, UnsupportedEncodingException {
        SendOut service = new SendOut();
       
    	service.topic = service.client.getTopic("jd");
        service.message = new MqttMessage();
        //确保被收到一次
        service.message.setQos(1);
        service.message.setPayload("1".getBytes("UTF-8"));
        service.publish(service.topic, service.message);
        
    }

}