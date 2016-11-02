package com.kcj.animationfriend.bean;

import com.kcj.animationfriend.config.DownloadState;
import com.liteutil.annotation.Column;
import com.liteutil.annotation.PrimaryKey;
import com.liteutil.annotation.Table;
import com.liteutil.orm.db.enums.AssignType;


/**
 * @ClassName: Download
 * @Description: 下载
 * @author: KCJ
 * @date:  
 */
@Table("Download")
public class Download {

	@PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    protected long id;
	private String fileName; // 文件名
	private String sortLetters; // 文件名首字母
	private String downLoadUrl; // 下载URL
	
	
	// 其他
	private DownloadState state = DownloadState.STOPPED; // 停止
	private int fileProgress;
    private long fileLength;
    private String fileSavePath; // 本地路径
    private boolean autoResume;  // 重新开始
    private boolean autoRename;  // 重新命名
	
	public Download(){}
	
	public Download(String fileName){
		this.fileName = fileName;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getDownLoadUrl() {
		return downLoadUrl;
	}
	
	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}
	
//	public String getDownLoadAv() {
//		return downLoadAv;
//	}
//
//	public void setDownLoadAv(String downLoadAv) {
//		this.downLoadAv = downLoadAv;
//	}

//	public String getLocalityUrl() {
//		return localityUrl;
//	}
//	
//	public void setLocalityUrl(String localityUrl) {
//		this.localityUrl = localityUrl;
//	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public DownloadState getState() {
		return state;
	}

	public void setState(DownloadState state) {
		this.state = state;
	}

	public int getFileProgress() {
		return fileProgress;
	}

	public void setFileProgress(int fileProgress) {
		this.fileProgress = fileProgress;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}

	public boolean isAutoResume() {
		return autoResume;
	}

	public void setAutoResume(boolean autoResume) {
		this.autoResume = autoResume;
	}

	public boolean isAutoRename() {
		return autoRename;
	}

	public void setAutoRename(boolean autoRename) {
		this.autoRename = autoRename;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Download)) return false;
        Download that = (Download) o;
        if (id != that.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
	
}
