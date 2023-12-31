package br.com.allen.flashfood.infrastructure.service;

import br.com.allen.flashfood.domain.service.PhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3PhotoStorageService implements PhotoStorageService {

    private final AmazonS3 amazonS3;
    @Override
    public void store(NewPhoto newPhoto) {

    }

    @Override
    public void remove(String filename) {

    }

    @Override
    public InputStream retrieve(String filename) {
        return null;
    }
}
