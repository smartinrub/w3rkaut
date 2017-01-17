package net.dynu.w3rkaut.network;

import net.dynu.w3rkaut.network.Services.SyncService;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = RestClientModule.class)
public interface RestClientComponent {
    SyncService getApiService();
}
