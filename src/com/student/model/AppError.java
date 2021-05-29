package com.student.model;

public class AppError {

	private String status;
	private String errorMessage;

	public AppError() {
	}

	public AppError(String statusFromOutside, String errorMessageFromOutside) {
		this.status = statusFromOutside;
		this.errorMessage = errorMessageFromOutside;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
