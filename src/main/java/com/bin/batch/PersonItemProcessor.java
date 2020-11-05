package com.bin.batch;

import com.bin.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * 处理数据(使用ItemProcessor)
 */
public class PersonItemProcessor implements ItemProcessor<Person, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public String process(Person person){
        String greeting = "Hello " + person.getFirstName() + " |+| " + person.getLastName() + "!";
        LOGGER.info("converting '{}' into '{}'", person, greeting);
        return greeting;
    }

}
