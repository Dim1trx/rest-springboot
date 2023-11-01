package com.rodrigues.restapi.mapper;

import java.util.List;

public class Mapper {
    private static org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new java.util.ArrayList<D>();
        origin.forEach(
                o -> destinationObjects.add(mapper.map(o, destination))
        );

        return destinationObjects;
    }
}
