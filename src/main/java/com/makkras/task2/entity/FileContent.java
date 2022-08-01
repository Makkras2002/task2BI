package com.makkras.task2.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class FileContent {
    @Id
    private String fileName;
    @OneToMany(targetEntity = Data.class, fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Data> fileDataList;

    public FileContent() {
    }

    public FileContent(String fileName, List<Data> fileDataList) {
        this.fileName = fileName;
        this.fileDataList = fileDataList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Data> getFileDataList() {
        return fileDataList;
    }

    public void setFileDataList(List<Data> fileDataList) {
        this.fileDataList = fileDataList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileContent that = (FileContent) o;

        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        return fileDataList != null ? fileDataList.equals(that.fileDataList) : that.fileDataList == null;
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (fileDataList != null ? fileDataList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileContent{");
        sb.append("fileName='").append(fileName).append('\'');
        sb.append(", fileDataList=").append(fileDataList);
        sb.append('}');
        return sb.toString();
    }
}
