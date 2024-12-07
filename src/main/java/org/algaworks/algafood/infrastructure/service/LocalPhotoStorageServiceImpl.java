package org.algaworks.algafood.infrastructure.service;

import org.algaworks.algafood.domain.exceptions.StorageException;
import org.algaworks.algafood.domain.services.PhotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPhotoStorageServiceImpl implements PhotoStorageService {

    @Value("${algafood.localstorage}")
    private Path directory;
    private static final String MSG_STORAGE_EXCEPTION = "Unable to store photo.";
    private static final String MSG_READ_EXCEPTION = "Unable to read photo.";

    @Override
    public void storage(NewPhoto newPhoto) {
        try {
            Path photo = getPhotoPath(newPhoto.getCode());
            Files.copy(newPhoto.getInputStream(), photo);
        } catch (IOException e) {
            throw new StorageException(MSG_STORAGE_EXCEPTION, e);
        }
    }

    @Override
    public InputStream recover(String code) {
        try {
            return Files.newInputStream(getPhotoPath(code));
        } catch (IOException e) {
            throw new StorageException(MSG_READ_EXCEPTION, e);
        }
    }

    private Path getPhotoPath(String photoCode) {
        return directory.resolve(Path.of(photoCode));
    }
}
