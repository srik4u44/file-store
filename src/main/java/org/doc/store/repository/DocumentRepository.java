package org.doc.store.repository;

import org.doc.store.model.Document;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DocumentRepository extends BaseRepository<Document, String> {

}
