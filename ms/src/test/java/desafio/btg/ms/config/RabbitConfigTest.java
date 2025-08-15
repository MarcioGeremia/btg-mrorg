package desafio.btg.ms.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RabbitConfigTest {

    @Test
    void deveValidarMessageConverterBean() {
        RabbitConfig config = new RabbitConfig();
        Jackson2JsonMessageConverter converter = config.messageConverter();
        assertNotNull(converter);
    }

}