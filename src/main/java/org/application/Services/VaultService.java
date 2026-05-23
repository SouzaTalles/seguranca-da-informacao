//package org.application.Services;
//
//import io.github.jopenlibs.vault.VaultConfig;
//import io.github.jopenlibs.vault.VaultException;
//
//public class VaultService {
//
//    final VaultConfig config;
//
//    public VaultService() throws VaultException {
//        try {
//            config = new VaultConfig()
//                    .address("http://127.0.0.1:8200")
//                    .token("3c9fd6be-7bc2-9d1f-4fb3-cd745c0fc4e8")
//                    .build();
//        } catch (VaultException e) {
//            throw new RuntimeException("Falha ao inicializar o cliente do Vault.", e);
//        }
//    }
//}