package org.doc.store.controller;

import org.doc.store.dto.DocumentDto;
import org.doc.store.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("documents")
public class DocumentController {

  @Autowired
  private DocumentService documentService;

  @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"})
  public @ResponseBody ResponseEntity<DocumentDto> create(@RequestParam("docName") String docName,
      @RequestParam("docType") String docType, @RequestParam("docTag") String docTag,
      @RequestParam(required = false, name = "file") MultipartFile file) {

    DocumentDto docDto = new DocumentDto();
    docDto.setDocName(docName);
    docDto.setDocTag(docTag);
    docDto.setDocType(docType);
    docDto.setMultiPartfile(file);

    DocumentDto savedDoc = documentService.save(docDto);
    return new ResponseEntity<DocumentDto>(savedDoc, HttpStatus.OK);
  }

}
