package co.edu.uniquindio.programacion3.subastaquindio.config;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitFactory {

    private ConnectionFactory connectionFactory;
    public RabbitFactory() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost("localhost");
        this.connectionFactory.setPort(15672);
        this.connectionFactory.setUsername("guest");
        this.connectionFactory.setPassword("guest");
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
