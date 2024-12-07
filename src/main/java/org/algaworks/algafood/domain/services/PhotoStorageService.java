package org.algaworks.algafood.domain.services;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface PhotoStorageService {

    void storage(NewPhoto newPhoto);

    InputStream recover(String codes);

    @Getter
    @Builder
    class NewPhoto {
        private String code;
        private InputStream inputStream;
    }
}
