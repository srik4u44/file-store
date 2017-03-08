package org.doc.store.service;

import org.doc.store.dto.DocumentDto;

public interface DocumentService {

  DocumentDto save(DocumentDto docDto);

  DocumentDto find(String docUuid);

}
