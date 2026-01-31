package com.sopheak.istadfinalems.media;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public interface FileService {
    FileResponseTemplate uploadSingleFile(MultipartFile file);
    List<FileResponseTemplate> uploadMultipleFiles(List<MultipartFile> files);
    Boolean deleteFileByName(String fileName);
}
