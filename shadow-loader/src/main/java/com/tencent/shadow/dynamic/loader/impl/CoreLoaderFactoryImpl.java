package com.tencent.shadow.dynamic.loader.impl;

import android.content.Context;

import com.tencent.shadow.core.loader.ShadowPluginLoader;

import org.jetbrains.annotations.NotNull;

import com.ajiang.plugin_loader.SamplePluginLoader;

/**
 * 这个类的包名类名是固定的。
 * <p>
 * 见com.tencent.shadow.dynamic.loader.impl.DynamicPluginLoader#CORE_LOADER_FACTORY_IMPL_NAME
 */
public class CoreLoaderFactoryImpl implements CoreLoaderFactory {

    @NotNull
    @Override
    public ShadowPluginLoader build(@NotNull Context context) {
        return new SamplePluginLoader(context);
    }
}
