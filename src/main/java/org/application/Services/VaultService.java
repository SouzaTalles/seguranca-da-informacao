package org.application.Services;

import io.github.jopenlibs.vault.Vault;
import io.github.jopenlibs.vault.VaultConfig;
import io.github.jopenlibs.vault.VaultException;
import io.github.jopenlibs.vault.VaultImpl;
import io.github.jopenlibs.vault.response.LogicalResponse;

import java.util.Map;

public class VaultService {

    final VaultConfig config;
    private final Vault vault;

    public VaultService() throws VaultException {
        try {
            config = new VaultConfig()
                    .address("http://127.0.0.1:8200")
                    .token("3c9fd6be-7bc2-9d1f-4fb3-cd745c0fc4e8")
                    .engineVersion(2)
                    .build();
            this.vault = new VaultImpl(config);
        } catch (VaultException e) {
            throw new RuntimeException("Falha ao inicializar o cliente do Vault.", e);
        }
    }

    public Map<String, String> buscarSegredos() throws VaultException {
        LogicalResponse resposta = vault.logical().read("secret/gerador-resposta");

        if (resposta == null || resposta.getRestResponse().getStatus() != 200) {
            throw new RuntimeException("Erro ao conectar ou ler dados do Vault.");
        }

        return resposta.getData();
    }
}