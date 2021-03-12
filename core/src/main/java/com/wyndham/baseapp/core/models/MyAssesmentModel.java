package com.wyndham.baseapp.core.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import com.day.cq.wcm.api.PageManager;
import java.util.Optional;
import com.google.gson.JsonObject;
import com.wyndham.common.services.KeyValueFragmentAdapter;
import com.wyndham.common.services.KeyValuePairService;
import com.wyndham.redesign.core.services.impl.WyndhamMappingURLServiceImpl;

// SlingHttpServletRequest.class needed for @ScriptVariable
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyAssesmentModel {
       @OSGiService
       private transient WyndhamMappingURLServiceImpl mappingURLService;
       @OSGiService
       private KeyValuePairService kvService;
       @OSGiService
       private KeyValueFragmentAdapter kvFragmentAdapter;
       @ScriptVariable
       private PageManager pageManager;
       
       private String data;
       private String totalHOAHeading ;
       private String programFeeHeading ;
       private String annualTotalHeading ;
       private String monthlyPaymentHeading ;
       private String contractHeading;
       private String contractDescription;
       private String monthlyPayment;
       private String assessmentDetails;
       private String assessmentSummary;
       private String contractNumber;
       private String contractDetails;
       private String contractType;
       private String pointsOwned;
       private String description;
       private String paymentFrequency;
       private String homeownersAssociationFee;
       private String monthlyFee;
       
       public String getData() {
             return this.data;
       }
       
       // Getter
  public String getTotalHOAHeading() {
    return totalHOAHeading;
  }
  
  // Setter
  public void setTotalHOAHeading(String totalHOAHeading) {
    this.totalHOAHeading = totalHOAHeading;
  }
  
       // Getter
  public String getProgramFeeHeading() {
    return programFeeHeading;
  }
  
  // Setter
  public void setProgramFeeHeading(String programFeeHeading) {
    this.programFeeHeading = programFeeHeading;
  }
  
  
  // Getter
  public String getAnnualTotalHeading() {
    return annualTotalHeading;
  }
  
  // Setter
  public void setAnnualTotalHeading(String annualTotalHeading) {
    this.annualTotalHeading = annualTotalHeading;
  }
  
  // Getter
  public String getMonthlyPaymentHeading() {
    return monthlyPaymentHeading;
  }
  
  // Setter
  public void setMonthlyPaymentHeading(String monthlyPaymentHeading) {
    this.monthlyPaymentHeading = monthlyPaymentHeading;
  }
  //Getter
  public String getContractHeading() {
             return contractHeading;
       }
//Setter
       public void setContractHeading(String contractHeading) {
             this.contractHeading = contractHeading;
       }
       //Getter
       public String getContractDescription() {
             return contractDescription;
       }
//Setter
       public void setContractDescription(String contractDescription) {
             this.contractDescription = contractDescription;
       }
       //Getter
       public String getMonthlyPayment() {
             return monthlyPayment;
       }
//Setter
       public void setMonthlyPayment(String monthlyPayment) {
             this.monthlyPayment = monthlyPayment;
       }
       public String getAssessmentDetails() {
             return assessmentDetails;
       }

       public void setAssessmentDetails(String assessmentDetails) {
             this.assessmentDetails = assessmentDetails;
       }
       
       public String getAssessmentSummary() {
             return assessmentSummary;
       }

       public void setAssessmentSummary(String assessmentSummary) {
             this.assessmentSummary = assessmentSummary;
       }
       public String getContractNumber() {
             return contractNumber;
       }

       public void setContractNumber(String contractNumber) {
             this.contractNumber = contractNumber;
       }
       public String getContractDetails() {
             return contractDetails;
       }

       public void setContractDetails(String contractDetails) {
             this.contractDetails = contractDetails;
       }
       public String getContractType() {
             return contractType;
       }

       public void setContractType(String contractType) {
             this.contractType = contractType;
       }
       
       public String getPointsOwned() {
             return pointsOwned;
       }

       public void setPointsOwned(String pointsOwned) {
             this.pointsOwned = pointsOwned;
       }
       public String getDescription() {
             return description;
       }

       public void setDescription(String description) {
             this.description = description;
       }
       public String getPaymentFrequency() {
             return paymentFrequency;
       }

       public void setPaymentFrequency(String paymentFrequency) {
             this.paymentFrequency = paymentFrequency;
       }
       public String getHomeownersAssociationFee() {
             return homeownersAssociationFee;
       }

       public void setHomeownersAssociationFee(String homeownersAssociationFee) {
             this.homeownersAssociationFee = homeownersAssociationFee;
       }
       public String getMonthlyFee() {
             return monthlyFee;
       }

       public void setMonthlyFee(String monthlyFee) {
             this.monthlyFee = monthlyFee;
       }
       @PostConstruct
       private void initModel() {
             
             JsonObject fragmentsJson = buildFragmentsJson();
             this.data = fragmentsJson.toString();
             
             
       }

private JsonObject buildFragmentsJson() {
      Map<String, String> fragmentToKeyValue = new HashMap<>();
      Set<String> keysToUppercase = new HashSet<>();
      // Assessment Summary Section
      
      fragmentToKeyValue.put("totalHOAHeading", "global.ownership.totalHOA");
      fragmentToKeyValue.put("programFeeHeading", "global.ownership.programFee");
      fragmentToKeyValue.put("annualTotalHeading", "global.ownership.annualTotal");
      fragmentToKeyValue.put("monthlyPaymentHeading", "global.ownership.monthlyPayment");
      //Assessment Contract Details

      fragmentToKeyValue.put("contractHeading", "global.ownership.contract");
      fragmentToKeyValue.put("contractDescription", "global.ownership.contractDetails");
      fragmentToKeyValue.put("monthlyPayment", "global.ownership.monthlyPayment");
      fragmentToKeyValue.put("assessmentDetails", "global.payment.assessmentDetails");
      fragmentToKeyValue.put("assessmentSummary", "global.ownership.assessmentSummary");
      fragmentToKeyValue.put("contractNumber", "global.form.contractNumber");
      fragmentToKeyValue.put("contractDetails", "global.ownership.contractDetails");
      fragmentToKeyValue.put("contractType", "global.ownership.contractType");
      fragmentToKeyValue.put("pointsOwned", "global.ownership.pointsOwned");
      fragmentToKeyValue.put("description", "global.ownership.description");
      fragmentToKeyValue.put("paymentFrequency", "global.ownership.paymentFrequency");
      fragmentToKeyValue.put("homeownersAssociationFee", "global.ownership.homeownersAssociationFee");
      fragmentToKeyValue.put("monthlyFee", "global.ownership.monthlyFee");
                          
                          
      JsonObject fragmentsJson = kvFragmentAdapter.adaptKeyValuePairsToFragments(kvService, fragmentToKeyValue, keysToUppercase);

             
             
      totalHOAHeading = Optional.ofNullable(fragmentsJson.get("totalHOAHeading").getAsString()).orElse("");
      programFeeHeading = Optional.ofNullable(fragmentsJson.get("programFeeHeading").getAsString()).orElse("");
      annualTotalHeading = Optional.ofNullable(fragmentsJson.get("annualTotalHeading").getAsString()).orElse("");
      monthlyPaymentHeading = Optional.ofNullable(fragmentsJson.get("monthlyPaymentHeading").getAsString()).orElse("");
      contractHeading= Optional.ofNullable(fragmentsJson.get("contractHeading").getAsString()).orElse("");
      contractDescription= Optional.ofNullable(fragmentsJson.get("contractDescription").getAsString()).orElse("");
      monthlyPayment= Optional.ofNullable(fragmentsJson.get("monthlyPayment").getAsString()).orElse("");
       assessmentDetails=Optional.ofNullable(fragmentsJson.get("assessmentDetails").getAsString()).orElse("");
       assessmentSummary=Optional.ofNullable(fragmentsJson.get("assessmentSummary").getAsString()).orElse("");
       contractNumber=Optional.ofNullable(fragmentsJson.get("contractNumber").getAsString()).orElse("");
       contractDetails=Optional.ofNullable(fragmentsJson.get("contractDetails").getAsString()).orElse("");
       contractType=Optional.ofNullable(fragmentsJson.get("contractType").getAsString()).orElse("");
       pointsOwned=Optional.ofNullable(fragmentsJson.get("pointsOwned").getAsString()).orElse("");
       description=Optional.ofNullable(fragmentsJson.get("description").getAsString()).orElse("");
       paymentFrequency=Optional.ofNullable(fragmentsJson.get("paymentFrequency").getAsString()).orElse("");
       homeownersAssociationFee=Optional.ofNullable(fragmentsJson.get("homeownersAssociationFee").getAsString()).orElse("");
       monthlyFee=Optional.ofNullable(fragmentsJson.get("monthlyFee").getAsString()).orElse("");
             
             return fragmentsJson;
             
       }

       }
