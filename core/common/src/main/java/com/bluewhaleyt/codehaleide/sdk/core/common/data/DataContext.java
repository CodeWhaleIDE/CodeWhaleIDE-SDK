package com.bluewhaleyt.codehaleide.sdk.core.common.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ApiStatus.Internal
public class DataContext {
    private final Map<String, Object> instances = new HashMap<>();

    public <T> void put(@NonNull String keyName, @Nullable T obj) {
        Objects.requireNonNull(keyName);
        instances.put(keyName, obj);
    }

    public <T> void put(@NonNull DataKey<T> key, @Nullable T obj) {
        put(key.getName(), obj);
    }

    /** @noinspection unchecked*/
    @Nullable
    public <T> T get(@NonNull String keyName) {
        Objects.requireNonNull(keyName);
        return (T) instances.get(keyName);
    }

    @Nullable
    public <T> T get(@NonNull DataKey<T> key) {
        return get(key.getName());
    }

    public void clear() {
        instances.clear();
    }
}
