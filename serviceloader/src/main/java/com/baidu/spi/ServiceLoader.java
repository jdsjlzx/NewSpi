package com.baidu.spi;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

public class ServiceLoader<S>{

    private Class<S> sClass;
    private Map<Class<S>, WeakReference<S>> mProviderMap = new LinkedHashMap<>();
    public ServiceLoader(Class<S> sClass) {
        this.sClass = sClass;
        this.load();
    }

    public static <S> ServiceLoader<S> load(Class<S> sClass) {
        return new ServiceLoader<S>(sClass);
    }


    private S load() {

        try {
            WeakReference<S> weakReference = mProviderMap.get(sClass);
            if (weakReference != null && weakReference.get() != null) {
                return weakReference.get();
            }

            final S provider = ServiceProvider.newProvider(sClass);
            mProviderMap.put(sClass, new WeakReference<>(provider));
            return provider;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public S get() {
        WeakReference<S> weakReference = mProviderMap.get(sClass);
        if (weakReference != null && weakReference.get() != null) {
            return weakReference.get();
        }
        return load();
    }
}
