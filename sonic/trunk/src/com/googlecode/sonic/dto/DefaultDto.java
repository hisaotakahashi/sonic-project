package com.googlecode.sonic.dto;

/**
 * 
 * @author hisao takahashi
 * @since 2011/11/06
 */
public class DefaultDto implements Dto {

	private static final long serialVersionUID = 2056180819131144990L;

	//TODO DateはimmutableにするためSerializationUtils#cloneを使用すること。
	private Long id;
	private String createDate;
	private String createUser;
	private String updateDate;
	private String updateUser;
	private boolean deleted;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
