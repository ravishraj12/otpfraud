package com.wibmo.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Instances {
	
	@JsonProperty("phone")
	private String mobile_number;
    private String ip;
    private int id;
    
    @JsonProperty("card_hashed")
    private String cardHashed;

    private String source;

    @JsonProperty("merchant_id")
    private int merchantId;

    private String email;

    @JsonProperty("merchant_name")
    private String merchantName;

    private int amount;

    @JsonProperty("user_agent")
    private String userAgent;

    private String mode;

    @JsonProperty("card_type")
    private String cardType;

    @JsonProperty("card_bin")
    private int cardBin;

    @JsonProperty("merchantCity")
    private String merchantCity;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("created_timestamp")
    private String createdTimestamp;
    
    public Instances() {
    	
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardHashed() {
		return cardHashed;
	}

	public void setCardHashed(String cardHashed) {
		this.cardHashed = cardHashed;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getCardBin() {
		return cardBin;
	}

	public void setCardBin(int cardBin) {
		this.cardBin = cardBin;
	}

	public String getMerchantCity() {
		return merchantCity;
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getIp() {
		return ip;
	}
	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
