package org.doc.store.service;

import java.io.File;

public class FileUtil {

  public static String buildFilePath(String docName, String... dirPathVars) {
    StringBuffer fPath = new StringBuffer();
    if (dirPathVars != null) {
      for (String dirPath : dirPathVars) {
        fPath.append(dirPath).append(File.separator);
      }
    }

    fPath.append(docName);
    return fPath.toString();
  }

}
