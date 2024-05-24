package br.com.anthonycruz.data.dto.v1;

import java.io.Serializable;

public class UploadFileResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileDownloadURI;
	private String fileType;
	private long fileSize;
	
	public UploadFileResponseDTO() {}

	public UploadFileResponseDTO(String fileName, String fileDownloadURI, String fileType, long fileSize) {
		this.fileName = fileName;
		this.fileDownloadURI = fileDownloadURI;
		this.fileType = fileType;
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadURI() {
		return fileDownloadURI;
	}

	public void setFileDownloadURI(String fileDownloadURI) {
		this.fileDownloadURI = fileDownloadURI;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long size) {
		this.fileSize = size;
	}
}
