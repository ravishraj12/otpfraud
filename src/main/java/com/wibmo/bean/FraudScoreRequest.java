package com.wibmo.bean;

import java.util.List;

public class FraudScoreRequest {

    private List<Instances> instances;
    
	public FraudScoreRequest() {
		
	}

	public List<Instances> getInstances() {
		return instances;
	}

	public void setInstances(List<Instances> instances) {
		this.instances = instances;
	}
	
}

