package org.doc.store.dto;

import org.springframework.web.multipart.MultipartFile;

public class DocumentDto {

  private long uuid;

  private String docName;

  private String docType;

  private String docTag;

  private String docUrl;

  private MultipartFile multiPartfile;

  public long getUuid() {
    return uuid;
  }

  public void setUuid(long uuid) {
    this.uuid = uuid;
  }

  public String getDocName() {
    return docName;
  }

  public void setDocName(String docName) {
    this.docName = docName;
  }

  public String getDocType() {
    return docType;
  }

  public void setDocType(String docType) {
    this.docType = docType;
  }

  public String getDocTag() {
    return docTag;
  }

  public void setDocTag(String docTag) {
    this.docTag = docTag;
  }

  public String getDocUrl() {
    return docUrl;
  }

  public void setDocUrl(String docUrl) {
    this.docUrl = docUrl;
  }

  public MultipartFile getMultiPartfile() {
    return multiPartfile;
  }

  public void setMultiPartfile(MultipartFile multiPartfile) {
    this.multiPartfile = multiPartfile;
  }
}
