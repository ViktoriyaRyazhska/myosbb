package com.softserve.osbb.service;

import com.softserve.osbb.config.ServiceApplication;
import com.softserve.osbb.model.Attachment;
import com.softserve.osbb.utils.Constants;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nataliia on 18.07.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceApplication.class)
@Rollback
@Transactional
public class AttachmentServiceTest {

    private Attachment attachment;
    private Attachment attachment1;

    @Autowired
    private AttachmentService attachmentService;

    @Value("${file.upload.path}")
    private String FILE_UPLOAD_PATH;

    @Value("${file.download.url}")
    private String FILE_DOWNLOAD_URL;

    @Before
    public void init() {

        attachment = new Attachment();
        attachment.setPath("C://...");

        attachment1 = new Attachment();
        attachment1.setPath("D://...");
    }

    @Ignore
    @Test
    public void testUploadFile() throws IOException {
        MultipartFile file = new MockMultipartFile("file.txt", "file.txt", "text", "content of the file".getBytes());
        Attachment attachment = attachmentService.uploadFile(file);
        File f = new File(attachment.getPath());
        assertTrue(f.exists());
        assertFalse(f.isDirectory());

        // clean up
        assertTrue(f.delete());
        Path subDir = Paths.get(FILE_UPLOAD_PATH + File.separator + Constants.DATE_FORMATTER.format(new Date()));
        if (Files.isDirectory(subDir) && Files.list(subDir).count() == 0) {
            Files.delete(subDir);
        }
        Path dir = Paths.get(FILE_UPLOAD_PATH);
        if (Files.isDirectory(dir) && Files.list(dir).count() == 0) {
            Files.delete(dir);
        }
    }

    @Test
    public void testSave() {
        attachmentService.saveAttachment(attachment);
        assertEquals(attachment, attachmentService.getAttachmentById(attachment.getAttachmentId()));
    }

    @Test
    public void testSaveList() {
        List<Attachment> list = new ArrayList<>();
        list.add(attachment);
        list.add(attachment1);
        attachmentService.saveAttachments(list);
        assertTrue(attachmentService.getAllAttachments().containsAll(list));
    }

    @Test
    public void testFindOne() {
        attachmentService.saveAttachment(attachment);
        assertEquals(attachment, attachmentService.getAttachmentById(attachment.getAttachmentId()));
    }

    @Test
    public void testFindAttachments() {
        List<Attachment> list = new ArrayList<>();
        list.add(attachment1);
        list.add(attachment);
        attachmentService.saveAttachments(list);
        assertTrue(attachmentService.getAttachments(list).containsAll(list));
    }

    @Test
    public void testFindAllByIDs() {
        List<Attachment> list = new ArrayList<>();
        list.add(attachment1);
        list.add(attachment);
        attachmentService.saveAttachments(list);
        List<Integer> ids = new ArrayList<>();
        ids.add(attachment.getAttachmentId());
        ids.add(attachment1.getAttachmentId());
        assertTrue(attachmentService.getAttachmentsByIds(ids).containsAll(list));
    }

    @Test
    public void testFindAll() {
        List<Attachment> list = new ArrayList<>();
        list.add(attachment);
        list.add(attachment1);
        attachmentService.saveAttachment(attachment);
        attachmentService.saveAttachment(attachment1);
        assertTrue(attachmentService.getAllAttachments().containsAll(list));
    }

    @Test
    public void testDelete() {
        attachmentService.saveAttachment(attachment);
        attachmentService.deleteAttachment(attachment);
        assertFalse(attachmentService.existsAttachment(attachment.getAttachmentId()));
    }

    @Test
    public void testDeleteById() {
        attachmentService.saveAttachment(attachment);
        attachmentService.deleteAttachmentById(attachment.getAttachmentId());
        assertFalse(attachmentService.existsAttachment(attachment.getAttachmentId()));
    }

    @Test
    public void testDeleteList() {
        List<Attachment> list = new ArrayList<>();
        list.add(attachment);
        list.add(attachment1);
        attachmentService.saveAttachments(list);
        attachmentService.deleteAttachments(list);
        assertFalse(attachmentService.existsAttachment(attachment.getAttachmentId()));
        assertFalse(attachmentService.existsAttachment(attachment1.getAttachmentId()));
    }

    @Test
    public void testDeleteAll() {
        attachmentService.saveAttachment(attachment);
        attachmentService.saveAttachment(attachment1);
        attachmentService.deleteAllAttachments();
        assertTrue(attachmentService.getAllAttachments().isEmpty());
    }

    @Test
    public void testCount() {
        int before = attachmentService.getAllAttachments().size();
        attachmentService.saveAttachment(attachment);
        attachmentService.saveAttachment(attachment1);
        assertEquals(before + 2, attachmentService.countAttachments());
    }

    @Test
    public void testExists() {
        attachmentService.saveAttachment(attachment);
        assertTrue(attachmentService.existsAttachment(attachment.getAttachmentId()));
    }

}