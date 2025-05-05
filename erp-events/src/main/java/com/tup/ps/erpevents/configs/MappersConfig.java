package com.tup.ps.erpevents.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.event.EventDTO;
import com.tup.ps.erpevents.entities.*;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * ModelMapper and ObjectMapper configuration class.
 */
@Configuration
public class MappersConfig {

    /**
     * The ModelMapper bean by default.
     * @return the ModelMapper by default.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * The ModelMapper bean to merge objects.
     * @return the ModelMapper to use in updates.
     */
    @Bean("mergerMapper")
    public ModelMapper mergerMapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

    /**
     * The ObjectMapper bean.
     * @return the ObjectMapper with JavaTimeModule included.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    /**
     * The ModelMapper bean to merge strict objects.
     * @return the ModelMapper to use in updates.
     */
    @Bean("strictMapper")
    public ModelMapper strictMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        // clients: entities to ids
        modelMapper.typeMap(ClientEntity.class, ClientDTO.class)
                .addMappings(mapper -> mapper.skip(ClientDTO::setEvents));

        // events
        /*modelMapper.typeMap(EventEntity.class, EventDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getEmployees() == null ? List.of() :
                    src.getEmployees().stream().map(EmployeeEntity::getIdEmployee).toList(), EventDTO::setEmployees);

            mapper.map(src -> src.getGuests() == null ? List.of() :
                    src.getGuests().stream().map(GuestEntity::getIdGuest).toList(), EventDTO::setGuests);

            mapper.map(src -> src.getSuppliers() == null ? List.of() :
                    src.getSuppliers().stream().map(SupplierEntity::getIdSupplier).toList(), EventDTO::setSuppliers);
        });*/
        /*modelMapper.typeMap(EventEntity.class, EventDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(EventDTO::setEmployees);
                    mapper.skip(EventDTO::setSuppliers);
                    mapper.skip(EventEntity::setLocation);
                    mapper.skip(EventDTO::setGuests);
                    mapper.skip(EventDTO::setTasks);
                });*/



        return modelMapper;
    }


}
