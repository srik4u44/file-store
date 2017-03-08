package org.doc.store.service;

import java.io.File;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.doc.store.dto.DocumentDto;
import org.doc.store.model.Document;
import org.doc.store.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class DocumentServiceImpl implements DocumentService {

  private static final String doc_folder =
      System.getProperty("user.dir") + File.separator + "doc-store";

  @Autowired
  protected DocumentRepository repository;

  @Override
  public DocumentDto save(DocumentDto docDto) {
    Document document = enrichDocument(docDto);
    Document savedDoc = repository.save(document);
    try {
      BeanUtilsBean.getInstance().copyProperties(savedDoc, docDto);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return docDto;
  }

  Document enrichDocument(DocumentDto docDto) {
    Document document = new Document();

    if (StringUtils.isEmpty(docDto.getUuid())) {
      String uuid = UUID.randomUUID().toString();
      document.setUuid(uuid);
    }

    String docName = docDto.getDocName();
    if (StringUtils.isEmpty(docName)) {
      document.setDocName(docDto.getDocName());
    }

    if (docDto.getMultiPartfile() != null) {
      document.setDocSize(docDto.getMultiPartfile().getSize());
    }

    if (StringUtils.isEmpty(docDto.getDocName())) {
      document.setDocName(docDto.getMultiPartfile().getOriginalFilename());
    } else {
      document.setDocName(document.getUuid() + "-" + document.getDocName());
    }

    if (StringUtils.isEmpty(document.getDocUrl())) {
      String docPath = FileUtil.buildFilePath(document.getDocName(), doc_folder);
      document.setDocUrl(docPath);
    }

    if (StringUtils.isEmpty(document.getDocType()) && docName.lastIndexOf(".") != -1) {
      String type = docName.substring(docName.lastIndexOf(".") + 1);
      document.setDocType(type);
    }

    return document;
  }

  @Override
  public DocumentDto find(String docUuid) {
    return null;
  }


}
