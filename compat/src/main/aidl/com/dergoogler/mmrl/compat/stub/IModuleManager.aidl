package com.dergoogler.mmrl.compat.stub;

import com.dergoogler.mmrl.compat.content.LocalModule;
import com.dergoogler.mmrl.compat.stub.IInstallCallback;
import com.dergoogler.mmrl.compat.stub.IModuleOpsCallback;

interface IModuleManager {
    String getVersion();
    int getVersionCode();
    List<LocalModule> getModules();
    LocalModule getModuleById(String id);
    LocalModule getModuleInfo(String zipPath);
    oneway void enable(String id, IModuleOpsCallback callback);
    oneway void disable(String id, IModuleOpsCallback callback);
    oneway void remove(String id, IModuleOpsCallback callback);
    oneway void install(String path, IInstallCallback callback);
}