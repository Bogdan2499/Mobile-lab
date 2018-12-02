package com.nulp.labs_aplication.api.Converter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Vova0199 on 18/11/2018.
 */

public class EnumConverterFactory extends Converter.Factory {

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Converter<?, String> converter = null;
        if (type instanceof Class && ((Class<?>)type).isEnum()) {
            converter = new Converter<Enum, String>() {
                @Override
                public String convert(Enum value) throws IOException {
                    return value.toString();
                }
            };
        }

        return converter;
    }

}
