package com.wyndham.baseapp.core.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleData {
	@ValueMapValue
	@Expose
	private String cardImagePath;
	@ValueMapValue
	private Date cardDate;
	@ValueMapValue
	@Expose
	private String cardTitle;
	@ValueMapValue
	@Expose
	private String cardCTALink;
	@ValueMapValue
	@Expose
	private String cardLinkText;
	@Expose
	private String cardDateString;
	public String getCardImagePath() {
		return cardImagePath;
	}
	public String getCardTitle() {
		return cardTitle;
	}
	public String getCardCTALink() {
		return cardCTALink;
	}
	public String getCardLinkText() {
		return cardLinkText;
	}
	public String getCardDateString() {
		return cardDateString;
	}
	@PostConstruct
	public void init() {
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
		cardDateString = format.format(cardDate);
	}
}
