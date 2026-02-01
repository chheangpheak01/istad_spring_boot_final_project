package com.sopheak.istadfinalems.media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Value("${spring.file.upload-location}")
    private String fileUploadLocation;
    @Value("${spring.file.preview-path}")
    private String previewPath;
    @Value("${spring.file.download-path}")
    private String downloadPath;

    private String uploadLogic(MultipartFile multipartFile){

        String originalName = multipartFile.getOriginalFilename();
        if (originalName == null || !originalName.contains(".")) {
            throw new RuntimeException("Invalid file name");
        }

        int dotSymbolPosition = multipartFile.getOriginalFilename().indexOf(".");
        String extension = multipartFile.getOriginalFilename().substring(dotSymbolPosition+1);

        if(extension.toLowerCase().contains("jpg") || extension.toLowerCase().contains("jpeg") || extension.toLowerCase().contains("png")){
            String newFileName = UUID.randomUUID() + "." + extension;
            Path fileUploadTo = Paths.get(fileUploadLocation, newFileName);
            try{
                Files.copy(multipartFile.getInputStream(), fileUploadTo);
                return newFileName;
            }catch (Exception exception){
                throw new RuntimeException(exception.getMessage());
            }
        }else {
            throw new RuntimeException("File is not supported");
        }
    }

    @Override
    public FileResponseTemplate uploadSingleFile(MultipartFile file) {

        String fileName = uploadLogic(file);

        return FileResponseTemplate.builder()
                .fileName(fileName)
                .size(file.getSize())
                .type(file.getContentType())
                .previewUrl(previewPath + "preview/" + fileName)
                .downloadUrl(downloadPath + "api/v1/files/download/" + fileName)
                .build();
    }

    @Override
    public List<FileResponseTemplate> uploadMultipleFiles(List<MultipartFile> files) {
        List<FileResponseTemplate> fileResponseTemplateList = new ArrayList<>();
        for(MultipartFile file: files){
            String newFileName = uploadLogic(file);
            FileResponseTemplate fileResponseTemplate = FileResponseTemplate
                    .builder()
                    .fileName(newFileName)
                    .size(file.getSize())
                    .type(file.getContentType())
                    .previewUrl(previewPath + "preview/" + newFileName)
                    .downloadUrl(downloadPath + "api/v1/files/download/" + newFileName)
                    .build();
            fileResponseTemplateList.add(fileResponseTemplate);
        }
        return fileResponseTemplateList;
    }

    @Override
    public Boolean deleteFileByName(String fileName) {
        try {
            Path filePath = Paths.get(fileUploadLocation).resolve(fileName);
            if (!Files.exists(filePath)) {
                throw new RuntimeException("Delete failed: File " + fileName + " not found on the server.");
            }
            return Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
