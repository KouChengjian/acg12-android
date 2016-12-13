package org.acg12.net.factory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by DELL on 2016/12/6.
 */
public class ApiConverterFactory extends Converter.Factory {

    public static ApiConverterFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static ApiConverterFactory create(Gson gson) {
        return new ApiConverterFactory(gson);
    }

    private final Gson gson;

    private ApiConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof ApiConverter) {
                try {
                    Class<? extends AbstractResponseConverter> converterClazz = ((ApiConverter) annotation).converter();
//                    // 获取有 以gson参数的 构造函数
                    Constructor<? extends AbstractResponseConverter> constructor = converterClazz .getConstructor(Gson.class);
                    AbstractResponseConverter  converter = constructor.newInstance(gson);
                    return converter;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new ApiResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new ApiRequestBodyConverter<>(gson, adapter);
    }
}

