package br.com.allen.flashfood.core.storage;

import java.nio.file.Path;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("flashfood.storage")
public class StorageProperties {
  private Local local = new Local();
  private S3 s3 = new S3();

  @Getter
  @Setter
  public class Local {
    private Path photoPath;
  }

  @Getter
  @Setter
  public class S3 {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String region;
    private String photoCatalogPath;
  }
}
