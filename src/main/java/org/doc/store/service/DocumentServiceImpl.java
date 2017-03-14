package org.doc.store.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.doc.store.dto.DocumentDto;
import org.doc.store.model.Document;
import org.doc.store.repository.DocumentRepository;
import org.doc.store.repository.SearchDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

  private static final String docStoreFolderPath =
      System.getProperty("user.dir") + File.separator + "doc-store";

  @PostConstruct
  public void init() {
    File docStoreFolder = new File(docStoreFolderPath);
    docStoreFolder.mkdirs();
  }

  @Autowired
  protected DocumentRepository repository;

  @Autowired
  protected SearchDocumentRepository searchRepository;

  @Override
  public DocumentDto save(DocumentDto docDto) {
    Document document = enrichDocument(docDto);
    saveFile(docDto.getMultiPartfile(), document.getDocUrl());
    Document savedDoc = repository.save(document);
    DocumentDto savedDocDto = new DocumentDto();
    try {
      BeanUtilsBean.getInstance().copyProperties(savedDocDto, savedDoc);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return savedDocDto;
  }

  private void saveFile(MultipartFile multiPartfile, String docUrl) {
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(new File(docUrl));
      InputStream is = multiPartfile.getInputStream();
      byte[] byteArr = new byte[10 * 1024];
      int numOfBytesRead = is.read(byteArr);
      while (numOfBytesRead != -1) {
        fos.write(byteArr, 0, numOfBytesRead);
        numOfBytesRead = is.read(byteArr);
      }
      fos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  Document enrichDocument(DocumentDto docDto) {
    Document document = new Document();

    if (StringUtils.isEmpty(docDto.getUuid())) {
      String uuid = UUID.randomUUID().toString();
      document.setUuid(uuid);
    }

    if (docDto.getMultiPartfile() != null) {
      document.setDocSize(docDto.getMultiPartfile().getSize());
    }

    String docName = docDto.getDocName();
    if (StringUtils.isEmpty(docName)) {
      document
          .setDocName(document.getUuid() + "-" + docDto.getMultiPartfile().getOriginalFilename());
    } else {
      document.setDocName(document.getUuid() + "-" + docName);
    }

    if (StringUtils.isEmpty(document.getDocUrl())) {
      String docPath = FileUtil.buildFilePath(document.getDocName(), docStoreFolderPath);
      document.setDocUrl(docPath);
    }

    String originalFileName = docDto.getMultiPartfile().getOriginalFilename();
    if (StringUtils.isEmpty(docDto.getDocType()) && document.getDocName().lastIndexOf(".") != -1) {
      String docType = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
      document.setDocType(docType);
    } else {
      document.setDocType(docDto.getDocType());
    }

    document.setDocTag(docDto.getDocTag());
    return document;
  }

  @Override
  public DocumentDto getFile(String docUuid) {
    Document document = repository.findOne(docUuid);
    DocumentDto documentDto = new DocumentDto();
    documentDto.setDocName(document.getDocName());
    documentDto.setDocTag(document.getDocTag());
    documentDto.setDocType(document.getDocType());
    documentDto.setDocUrl(document.getDocUrl());
    return documentDto;
  }

  @Override
  public List<DocumentDto> searchDocuments(DocumentDto documentDto) {
    List<Document> documentList = searchRepository.searchByCriteria(documentDto);
    if (!(null == documentList)) {
      List<DocumentDto> documentDtoList = new ArrayList<DocumentDto>();
      for (Document document : documentList) {
        documentDto.setUuid(document.getUuid());
        documentDto.setDocName(document.getDocName());
        documentDto.setDocTag(document.getDocTag());
        documentDto.setDocType(document.getDocType());
        documentDto.setDocUrl(document.getDocUrl());

        documentDtoList.add(documentDto);
      }
      return documentDtoList;
    } else
      return null;
  }

  @Override
  public DocumentDto getFileStream(String docUuid) {
	  DocumentDto documentDto=new DocumentDto();
    Document document = repository.findOne(docUuid);
    documentDto.setDocName(document.getDocName());
    File fileToRead = new File(document.getDocUrl());
    byte[] fileContentInBytes = new byte[(int) fileToRead.length()];
    
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(fileToRead);
      fis.read(fileContentInBytes);
     
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    documentDto.setContent(fileContentInBytes);
    return documentDto;
  }

}
