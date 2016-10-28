package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Attachment;
import com.softserve.osbb.model.enums.AttachmentType;
import com.softserve.osbb.repository.AttachmentRepository;
import com.softserve.osbb.service.AttachmentService;
import com.softserve.osbb.utils.Constants;
import liquibase.util.file.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.softserve.osbb.utils.Constants.DATE_FORMATTER;

/**
 * Created by nataliia on 11.07.16.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Value("${file.upload.path}")
    private String FILE_UPLOAD_PATH;

    @Value("${file.download.url}")
    private String FILE_DOWNLOAD_URL;

    private static Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Attachment uploadFile(MultipartFile file) {
        logger.info("Uploading file " + file.getName());
        Path attachmentPath = saveFile(getFilePathWithSubDir(file), file);
        Attachment attachment = new Attachment();
        String path = attachmentPath.toString();
        attachment.setPath(path);
        attachment.setDate(LocalDate.now());
        attachment.setFileName(file.getOriginalFilename());
        attachment.setType(getAttachmentType(attachment));
        return saveAttachment(attachment);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAttachmentEverywhere(Integer attachmentId) {
        renameFile(new File(attachmentRepository.findOne(attachmentId).getPath()));
        deleteAttachment(getAttachmentById(attachmentId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Attachment> findAttachmentByPath(String path) {
        return attachmentRepository.findAttachmentByPath(path);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Attachment saveAttachment(Attachment attachment) {
        return attachmentRepository.save(getAttachmentWithUrl(attachment));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public List<Attachment> saveAttachments(List<Attachment> list) {
        return attachmentRepository.save(list);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Attachment> getAttachments(List<Attachment> list) {
        return attachmentRepository.save(list);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Attachment getAttachmentById(Integer id) {
        return attachmentRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Attachment> getAttachmentsByIds(List<Integer> ids) {
        return attachmentRepository.findAll(ids);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Attachment updateAttachment(Integer id, Attachment attachment) {
        return attachmentRepository.exists(id) ? attachmentRepository.save(attachment) : null;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAttachment(Attachment attachment) {
        attachmentRepository.delete(attachment);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAttachmentById(Integer id) {
        attachmentRepository.delete(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAttachments(List<Attachment> list) {
        attachmentRepository.delete(list);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAllAttachments() {
        attachmentRepository.findAll().forEach(a -> deleteAttachmentEverywhere(a.getAttachmentId()));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public long countAttachments() {
        return attachmentRepository.count();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean existsAttachment(Integer id) {
        return attachmentRepository.exists(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Attachment> getAllAttachments(Integer pageNumber, String sortBy, Boolean order) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, Constants.DEF_ROWS,
                getSortingOrder(order), sortBy == null ? "path" : sortBy);
        return attachmentRepository.findAll(pageRequest);
    }

    private Sort.Direction getSortingOrder(Boolean order) {
        if (order == null) {
            return Sort.Direction.DESC;
        }
        return order ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    private AttachmentType getAttachmentType(Attachment attachment) {
        try {
            String type = Files.probeContentType(Paths.get(attachment.getFileName()));
            if (type.contains("image")) {
                return AttachmentType.IMAGE;
            } else if (type.contains("audio")) {
                return AttachmentType.AUDIO;
            } else if (type.contains("video")) {
                return AttachmentType.VIDEO;
            } else if (type.contains("text")) {
                return AttachmentType.TEXT;
            }
        } catch (IOException e) {
            logger.error("Could not set Attachment type.");
        }
        return AttachmentType.DATA;
    }

    private Path getFilePathWithSubDir(MultipartFile file) {
        Path path = Paths.get(FILE_UPLOAD_PATH + DATE_FORMATTER.format(new Date()));
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            logger.error("Could not create directory " + path.toString());
        }
        return Paths.get(String.valueOf(path) + File.separator + file.getOriginalFilename());
    }

    private Path saveFile(Path newFilePath, MultipartFile file) {
        if (!Files.exists(newFilePath)) {
            try {
                InputStream is = file.getInputStream();
                Files.copy(is, newFilePath);
            } catch (IOException e) {
                logger.error("Could not save file " + file.getName());
            }
            return newFilePath;
        } else {
            newFilePath = getFilePathForDuplicatedFile(newFilePath);
            return saveFile(newFilePath, file);
        }
    }

    private Path getFilePathForDuplicatedFile(Path existingFilePath) {
        int i = 0;
        Path tempPath = existingFilePath;
        while (Files.exists(tempPath)) {
            String filePathWithoutExtension = FilenameUtils.removeExtension(String.valueOf(existingFilePath));
            String fileExtension = FilenameUtils.getExtension(String.valueOf(existingFilePath));
            tempPath = Paths.get(filePathWithoutExtension + "(" + ++i + ")." + fileExtension);
        }
        return tempPath;
    }

    private void renameFile(File file) {
        String filePathWithoutExtension = FilenameUtils.removeExtension(file.getPath());
        String fileExtension = FilenameUtils.getExtension(file.getPath());
        File delFile = new File(filePathWithoutExtension + "_del." + fileExtension);
        try {
            Files.move(file.toPath(), delFile.toPath());
        } catch (IOException e) {
            logger.error("Could not rename file " + file.getName());
        }
    }

    private Attachment getAttachmentWithUrl(Attachment attachment) {
        attachment.setUrl(attachment.getPath().replace(FILE_UPLOAD_PATH, FILE_DOWNLOAD_URL));
        return attachment;
    }
}
