package br.com.jofofe.campanha.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("objectMapper")
public class ObjectMapper {

    private ModelMapper modelMapper;

    private ModelMapper getInstanceModelMapper() {
        if(modelMapper == null) {
            modelMapper = new ModelMapper();
        }
        return modelMapper;
    }

    public <T> T convert(final Object sourceOrder , final Class sourceDestino) {
        return (T) getInstanceModelMapper().map(sourceOrder, sourceDestino);
    }
}
