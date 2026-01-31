package com.sopheak.istadfinalems.media;
import com.sopheak.istadfinalems.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/files")
public class FileController {

    private final FileService fileService;

    @Value("${spring.file.upload-location}")
    private String fileUploadLocation;

    @PostMapping("/uploads")
    public ResponseTemplate<Object> uploadSingleFile(@RequestParam(name = "file") MultipartFile multipartFile){
        return ResponseTemplate
                .builder()
                .staus(HttpStatus.OK.toString())
                .date(Date.from(Instant.now()))
                .message("Upload single file successfully")
                .data(fileService.uploadSingleFile(multipartFile))
                .build();
    }

    @PostMapping("/upload-multi")
    public ResponseTemplate<Object> uploadMultipleFiles(@RequestParam(name = "files") List<MultipartFile> multipartFiles){
        return ResponseTemplate
                .builder()
                .staus(HttpStatus.OK.toString())
                .date(Date.from(Instant.now()))
                .message("Upload multiple files successfully")
                .data(fileService.uploadMultipleFiles(multipartFiles))
                .build();
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Object> getDownloadFile(@PathVariable(name = "filename") String filename) {
        try {
            Path path = Paths.get(fileUploadLocation).resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\""+ resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception exception) {
            throw new RuntimeException("Error Downloading file");
        }
    }

    @GetMapping("/preview/{filename:.+}")
    public ResponseEntity<Resource> getPreviewFile(@PathVariable String filename) {
        try {
            Path path = Paths.get(fileUploadLocation).resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + filename);
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } catch (Exception exception) {
            throw new RuntimeException("Error viewing file: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<ResponseTemplate<Object>> deleteFile(@PathVariable String filename) {
        Boolean isDeleted = fileService.deleteFileByName(filename);

        HttpStatus status = isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String message = isDeleted ? "File deleted successfully" : "File not found";

        return ResponseEntity.status(status).body(
                ResponseTemplate.builder()
                        .staus(status.toString())
                        .date(Date.from(Instant.now()))
                        .message(message)
                        .data(null)
                        .build()
        );
    }

}
