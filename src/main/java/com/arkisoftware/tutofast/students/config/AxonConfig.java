package com.arkisoftware.tutofast.students.config;

import com.arkisoftware.tutofast.students.command.domain.Student;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
    @Bean
    public Repository<Student> eventSourcingRepository(EventStore eventStore){
        return EventSourcingRepository.builder(Student.class)
                .eventStore(eventStore)
                .build();
    }
}
