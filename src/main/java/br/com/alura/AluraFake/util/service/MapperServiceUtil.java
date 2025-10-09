package br.com.alura.AluraFake.util.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;

public class MapperServiceUtil {

    private static ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    public static <E, D> D convertObject(E input, Class<D> outputClass){
        return getModelMapper().map(input, outputClass);
    }

    public static <E, D> List<D> convertObjects(List<E> entitys, Class<D> dtoClass){
        return entitys.stream()
                .map(entity -> convertObject(entity, dtoClass))
                .toList();
    }
}
