package com.example.application.model.database;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "uploadedfilelogs", schema = "table_uploader_vaadin_schema")
public class UploadedFileLogs {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
    @SequenceGenerator(name = "ID_GENERATOR", sequenceName = "logs_sequence", allocationSize = 1)
    private int id;

    @Column(name = "file_name")
    private String filename;

    @Column(name = "date_time")
    private Timestamp timestamp;

    @Column(name = "user_id")
    private int userId;

    public UploadedFileLogs(String filename, int userId, Timestamp timestamp) {
        this.filename = filename;
        this.userId = userId;
        this.timestamp = timestamp;
    }


    public UploadedFileLogs() {
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int ELN) {
        this.userId = ELN;
    }

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
