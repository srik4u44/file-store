User H2 Database as the in-memory DB. 
http://localhost:8080/h2-console/

JDBC URL:jdbc:h2:mem:mydb
UserName:cfa_admin
Password:p@$$w0rd!

build the application first and then goto org.doc.store folder then run the DocumentStoreApplication class as java application.

http://localhost:8080/documents/save
method:POST
request:docName:srikanth
docType:doc
docTag:tag
file:choose one file
response:"uuid": "174b1a8a-e56d-49ea-8e78-3703503430fe",
    "docName": "174b1a8a-e56d-49ea-8e78-3703503430fe-\"srikanth\"",
    "docType": "\"doc\"",
    "docTag": "\"tag\"",
    "docUrl": "C:\\Users\\srikanthreddy\\Desktop\\projects\\samples\\file-store\\doc-store\\174b1a8a-e56d-49ea-8e78-3703503430fe-\"srikanth\""
http://localhost:8080/documents/174b1a8a-e56d-49ea-8e78-3703503430fe
method:GET
request:uuid
response:{
  "uuid": "174b1a8a-e56d-49ea-8e78-3703503430fe",
    "docName": "174b1a8a-e56d-49ea-8e78-3703503430fe-\"srikanth\"",
    "docType": "\"doc\"",
    "docTag": "\"tag\"",
    "docUrl": "C:\\Users\\srikanthreddy\\Desktop\\projects\\samples\\file-store\\doc-store\\174b1a8a-e56d-49ea-8e78-3703503430fe-\"srikanth\""
}


http://localhost:8080/documents/search
method:POST
request:{
"uuid":"174b1a8a-e56d-49ea-8e78-3703503430fe",
"docName": "174b1a8a-e56d-49ea-8e78-3703503430fe-\"srikanth\"",
"docType":"\"doc\""
}
response:[
  {
    "uuid": "174b1a8a-e56d-49ea-8e78-3703503430fe",
    "docName": "174b1a8a-e56d-49ea-8e78-3703503430fe-\"srikanth\"",
    "docType": "\"doc\"",
    "docTag": "\"tag\"",
    "docUrl": "C:\\Users\\srikanthreddy\\Desktop\\projects\\samples\\file-store\\doc-store\\174b1a8a-e56d-49ea-8e78-3703503430fe-\"srikanth\""
  }
]

http://localhost:8080/documents/download/9ebf86ce-ef5b-43af-bb03-20acd478fcfb
method:GET
request:{
uuid
}
response:requested file will be downloaded
