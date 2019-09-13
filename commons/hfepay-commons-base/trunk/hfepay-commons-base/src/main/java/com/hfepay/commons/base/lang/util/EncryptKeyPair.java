package com.hfepay.commons.base.lang.util;

public class EncryptKeyPair {
	
	String publicKey;
	
	String privateKey;

	public EncryptKeyPair(String publicKey, String privateKey) {
		super();
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public String toString() {
		return "EncryptKeyPair [publicKey=" + publicKey + ", privateKey=" + privateKey + "]";
	}
	
}
