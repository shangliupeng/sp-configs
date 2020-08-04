package m1_simple;

import com.rabbitmq.client.*;


import java.io.IOException;

public class Test2 {
    public static void main(String[] args) throws Exception{
        //连接工厂
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.126.130");
        f.setUsername("admin");
        f.setPassword("admin");
        //建立连接
        Connection c = f.newConnection();
        //建立信道
        Channel ch = c.createChannel();
        //声明队列  如果该对列已经创建过  则不会重复创建
        ch.queueDeclare("helloworld",false,false,false,null);
        System.out.println("等待接收数据");

        //收到消息后用来处理消息的回调对象
        DeliverCallback callback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                String msg = new String(delivery.getBody(), "UTF-8");
                //遍历字符串中的字符,每个点使进程暂停一秒
                for (int i = 0; i < msg.length(); i++) {
                    if (msg.charAt(i)=='.') {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                System.out.println("处理结束");
                //发送回执
                ch.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };


        //消费者获取取消时的回调对象
        CancelCallback cancel = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {

            }
        };
        ch.basicConsume("helloworld",true , callback,cancel);



    }
}
