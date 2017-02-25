package com.softserve.osbb.test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.osbb.model.Folder;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.repository.FolderRepository;
import com.softserve.osbb.service.FolderService;
import com.softserve.osbb.service.exceptions.EntityNotFoundException;
import com.softserve.osbb.service.impl.FolderServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class FolderServiceTest {
	
	@InjectMocks
	private FolderServiceImpl folderServiceImpl;
	
	@Mock
	private FolderService folderService;
	
	@Mock
	private FolderRepository folderRepository;
	
	@Mock
	private Folder folder;
	
	@Mock
	private Osbb osbb;
	
	@Mock
	private List<Folder> folders;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void deleteTest() {
		doNothing().when(folderService).delete(folder);
		
		folderServiceImpl.delete(folder);
		
		verify(folderRepository, times(1)).delete(folder);
	}
	
	@Test
	public void deleteByIdTest() {		
		folderServiceImpl.deleteById(1);
		folderServiceImpl.deleteById(2);
		
		verify(folderRepository, times(1)).delete(1);
		verify(folderRepository, times(1)).delete(2);
	}
	
	@Test (expected = EntityNotFoundException.class)
	public void updateTest() {
		Folder folder1 = new Folder();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		folder1.setId(1);
		folder1.setName("folder");
		folder1.setOsbb(osbb);
		folder1.setParent(folder);
		folder1.setCreated(timestamp);
		
		when(folderServiceImpl.update(folder)).thenReturn(folder);
		when(folderServiceImpl.update(1, "folder1")).thenReturn(folder);
		
		folderServiceImpl.update(folder);
		folderServiceImpl.update(1, "folder1");
		
		verify(folderRepository, times(1)).saveAndFlush(folder);
	}
	
	@Test
	public void findAllTest() {
		when(folderServiceImpl.findAll()).thenReturn(folders);
		
		folderServiceImpl.findAll();
		
		verify(folderRepository, times(1)).findAll();
	}
	
	@Test (expected  = EntityNotFoundException.class)
	public void findByIdTest() {		
		when(folderServiceImpl.findById(1)).thenReturn(folder);
		when(folderServiceImpl.findById(2)).thenReturn(folder);
		
		folderServiceImpl.findById(1);
		folderServiceImpl.findById(2);
		
		verify(folderRepository, times(1)).findById(1);
		verify(folderRepository, times(1)).findById(2);
	}
	
	@Test
	public void findByParentIdTest() {
		when(folderServiceImpl.findByParentId(1)).thenReturn(folders);
		when(folderServiceImpl.findByParentId(2)).thenReturn(folders);
		
		folderServiceImpl.findByParentId(1);
		folderServiceImpl.findByParentId(2);
		
		verify(folderRepository, times(1)).findByParentId(1);
		verify(folderRepository, times(1)).findByParentId(2);
	}
	
	@Test
	public void findByParentTest() {
		when(folderServiceImpl.findByParent(folder)).thenReturn(folders);
		
		folderServiceImpl.findByParent(folder);
		
		verify(folderRepository, times(1)).findByParent(folder);
	}
	
	@Test
	public void findByOsbbTest() {
		when(folderServiceImpl.findByOsbb(osbb)).thenReturn(folders);
		
		folderServiceImpl.findByOsbb(osbb);
		
		verify(folderRepository, times(1)).findByOsbb(osbb);
	}
	
	@Test (expected = EntityNotFoundException.class)
	public void saveTest() {
		Folder folder = new Folder();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		folder.setId(1);
		folder.setName("folder1");
		folder.setCreated(timestamp);
		folder.setOsbb(osbb);
		
		when(folderServiceImpl.save("folder1", 1)).thenReturn(folder);
		
		folderServiceImpl.save("folder1", 1);
		
		verify(folderService, times(0)).save("folder1", 1);
	}

}
