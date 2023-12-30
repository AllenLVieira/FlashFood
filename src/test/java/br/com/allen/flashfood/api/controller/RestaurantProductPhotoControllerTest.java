package br.com.allen.flashfood.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.Path;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class RestaurantProductPhotoControllerTest {

  @Mock MultipartFile mockFile;
  @Mock private Path mockFilePath;

  @InjectMocks private RestaurantProductPhotoController controller;

  /*@Test
  @Disabled
  void shouldUpdatePhotoSuccessfully_whenGivenValidRequest() {
    // Given
    MockMultipartFile file =
        new MockMultipartFile("file", "test.jpg", "image/jpeg", "some image data".getBytes());
    ProductPhotoRequest request = new ProductPhotoRequest(file, "Test photo description");

    // When
    PhotoProductResponse response = controller.updatePhoto(1L, 2L, request);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Photo updated successfully.", response.getBody());
  }

  @Test
  @Disabled
  void shouldReturnPayloadTooLargeError_whenFileSizeExceedsLimit() {
    // Given
    MultipartFile mockFile =
        new MockMultipartFile("file", "test.jpg", "image/jpeg", new byte[12 * 1024 * 1024]);
    ProductPhotoRequest request = new ProductPhotoRequest(mockFile, "Test photo description");

    // When
    PhotoProductResponse response = controller.updatePhoto(1L, 2L, request);

    // Then
    assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, response.getStatusCode());
    assertEquals("File size exceeds the allowed limit of 10 MB.", response.getBody());
    verifyNoInteractions(mockFilePath);
  }

  @Test
  @Disabled
  void shouldReturnUnsupportedMediaTypeError_whenInvalidContentTypeIsProvided() {
    // Given
    MultipartFile mockFile =
        new MockMultipartFile("file", "test.pdf", "application/pdf", new byte[1024]);
    ProductPhotoRequest request = new ProductPhotoRequest(mockFile, "Test photo description");

    // When
    PhotoProductResponse response = controller.updatePhoto(1L, 2L, request);

    // Then
    assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    assertEquals("Only image files are supported.", response.getBody());
    verifyNoInteractions(mockFilePath);
  }

  @Test
  @Disabled
  void updatePhoto_badRequest_missingContentType() {
    // Given
    MockMultipartFile file =
        new MockMultipartFile("file", "test.jpg", null, "some image data".getBytes());
    ProductPhotoRequest request = new ProductPhotoRequest(file, "Test photo description");

    // When
    PhotoProductResponse response = controller.updatePhoto(1L, 2L, request);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Missing or invalid content type.", response.getBody());
  }*/
}
