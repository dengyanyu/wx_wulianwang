package test;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PushCallback implements MqttCallback{

    //连接丢失：一般用与重连
    public void connectionLost(Throwable throwable) {
        System.out.println("丢失连接");
    }
    //消息到达：指收到消息
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload()));
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        //（发布）publish后会执行到这里,发送状态
        System.out.println("deliveryComplete---------"
                + token.isComplete());
    }
    
    
    
    public static void main(String[] args) {
        try {
            //apollo地址
            String HOST = "tcp://47.94.80.80:1883";
            //要订阅的主题
            String TOPIC1="abc";
            //指你Apollo中的用户名密码
//            String userName="admin";
//            String pwd="password";
            String clientid =UUID.randomUUID().toString().replace("-","");
            MqttClient client=new MqttClient(HOST,clientid,new MemoryPersistence());
            // MQTT的连接对象
            MqttConnectOptions options = new MqttConnectOptions();
            //设置连接参数
                //清除session回话
            options.setCleanSession(false);
//            options.setUserName(userName);
//            options.setPassword(pwd.toCharArray());
            //超时设置
            options.setConnectionTimeout(10);
            //心跳保持时间
            options.setKeepAliveInterval(20);
            //遗嘱:当该客户端端口连接时，会向whb主题发布一条信息
            options.setWill("whb","我挂了，你加油".getBytes(),1,true);
            //监听对象：自己创建
            client.setCallback(new PushCallback());
            //打开连接
            client.connect(options);
            //设置消息级别
            int[] Qos={1};
            //订阅主题
            String[] topics={TOPIC1};
            client.subscribe(topics,Qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}