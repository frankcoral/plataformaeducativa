package cl.duoc.plataformaeducativa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String subirArchivo(Long idInscripcion, MultipartFile archivo) {
        String key = construirKey(idInscripcion, archivo.getOriginalFilename());

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(archivo.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(archivo.getBytes()));

            return key;
        } catch (Exception e) {
            throw new RuntimeException("Error al subir archivo a S3: " + e.getMessage());
        }
    }

    public ByteArrayResource descargarArchivo(Long idInscripcion, String nombreArchivo) {
        String key = construirKey(idInscripcion, nombreArchivo);

        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(request);
            return new ByteArrayResource(response.asByteArray());

        } catch (NoSuchKeyException e) {
            throw new RuntimeException("El archivo no existe en S3: " + key);
        } catch (Exception e) {
            throw new RuntimeException("Error al descargar archivo desde S3: " + e.getMessage());
        }
    }

    public String reemplazarArchivo(Long idInscripcion, MultipartFile archivo) {
        String key = construirKey(idInscripcion, archivo.getOriginalFilename());

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(archivo.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(archivo.getBytes()));

            return key;
        } catch (Exception e) {
            throw new RuntimeException("Error al reemplazar archivo en S3: " + e.getMessage());
        }
    }

    public String eliminarArchivo(Long idInscripcion, String nombreArchivo) {
        String key = construirKey(idInscripcion, nombreArchivo);

        try {
            validarExistencia(key);

            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(request);

            return "Archivo eliminado correctamente: " + key;

        } catch (NoSuchKeyException e) {
            throw new RuntimeException("El archivo no existe en S3: " + key);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar archivo desde S3: " + e.getMessage());
        }
    }

    private void validarExistencia(String key) {
        HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.headObject(request);
    }

    private String construirKey(Long idInscripcion, String nombreArchivo) {
        return "resumenes/" + idInscripcion + "/" + nombreArchivo;
    }
}