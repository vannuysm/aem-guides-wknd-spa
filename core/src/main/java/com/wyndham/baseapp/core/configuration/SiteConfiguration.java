package com.wyndham.baseapp.core.configuration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Designate(ocd=SiteConfiguration.Config.class)
@Component(service=SiteConfiguration.class,immediate=true)
public class SiteConfiguration {
	
	@ObjectClassDefinition(name="Wyndham Owner Site Configuration Service",
			description = "OSGI configuration service for Wyndham Holiday.")
	public static @interface Config {
		public static String DEFAULT_API_HOSTNAME = "consumerapiuat.wvc.wyndhamdestinations.com";
		
		@AttributeDefinition(name = "Scheduler name", description = "Scheduler name", type = AttributeType.STRING)
		String schedulerName() default "Wyndham_Next_Gen_Import_Resorts_Task";

		@AttributeDefinition(name = "Cron-job expression")
		String schedulerExpression() default ""; // "0 1 0 ? * *"; // Run everyday at midnight

		@AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
		boolean schedulerConcurrent() default false;
		
		@AttributeDefinition(name = "Disable Product Hub Scheduler", description = "Disable product hub scheduler that imports resorts.")
		boolean disableImportResortScheduler() default false;
		
		@AttributeDefinition(name = "Skip SFTP File download", description = "Whether or not to skip sftp file download and use file from DAM path")
		boolean skipSFTPFileDownload() default false;
		
		@AttributeDefinition(name = "Wyndham SFTP Host", description = "Wyndham SFTP Host")
		String sftpHost() default "";
		
		@AttributeDefinition(name = "Wyndham SFTP Port", description = "Wyndham SFTP Port Number")
		int sftpPort() default 22;
		
		@AttributeDefinition(name = "Wyndham SFTP Username", description = "Wyndham SFTP Username")
		String sftpUserName() default "";
		
		@AttributeDefinition(name = "RSA Key File Path", description = "RSA Key file path on the server to connect to the SFTP server for authentication")
		String rsaKeyFilePath() default "";

		@AttributeDefinition(name = "AEM ASSET Parent Path to store resort files", description = "Resort import files parent path to store downloaded file from SFTP to AEM Assets")
		String resortsFilePath() default "";
		
		@AttributeDefinition(name = "Resort Pages Path", description = "Path where to create resort pages")
		String resortContentPath() default "";

		@AttributeDefinition(name = "Resort Fragment Path", description = "Path where to create resort fragments")
		String resortFragmentPath() default "";
		
		@AttributeDefinition(name = "Resort Tags Path", description = "Path where to create resort tags")
		String resortTagsPath() default "";
		
		@AttributeDefinition(name = "Resort Fragment Placeholder Path", description = "Resort fragment placeholder path to copy and create new resort fragment.")
		String resortFragmentPlaceholderPath() default "";
		
		@AttributeDefinition(name = "Resort Template Path", description = "Template path used when creating resort pages")
		String resortTemplatePath() default "";
		
		@AttributeDefinition(name = "Resort Detail Component Path", description = "Resort detail component path used on each resort page to display description and review star.")
		String resortDetailComponentPath() default "";
		
		@AttributeDefinition(name = "Resort Room Types Component Path", description = "Resort room types component path used on each resort page to display description and review star.")
		String resortRoomTypesComponentPath() default "";

		@AttributeDefinition(name = "Resort Gallery Image Path", description = "Resort gallery image path to pull images dynamically for hero banner gallery and resort room types gallery.")
		String resortGalleryImagePath() default "";

		@AttributeDefinition(name = "Google Map Key", description = "Google Map Key")
		String googleMapKey() default "";

		@AttributeDefinition(name = "Captcha Key", description = "The key for Captcha")
		String captchaKey() default "";
		
		@AttributeDefinition(name = "Yext Review API URL", description = "Yext Review API URL with API key, version and required query parameters.")
		String yextReviewAPIUrl() default "";
		
		@AttributeDefinition(name = "Yext Review API Disable", description = "Check if the reviews need to be disabled for the environment. Enabled by default.")
		boolean yextReviewAPIDisable() default false;
			
		@AttributeDefinition(name = "Import Log Path", description = "Path to import the log about resorts process in AEM")
		String importLogPath() default "";

		@AttributeDefinition(name = "Max log Days", description = "Maximum of days to store historical information. Default 30 days", type = AttributeType.INTEGER)
		int maxDaysToKeepLogs() default 30;

		@AttributeDefinition(name = "Save Log File", description = "The flag of save the job log.", type = AttributeType.BOOLEAN)
		boolean saveLogFile() default false;

		@AttributeDefinition(name = "AEM Custom Image Rendition Generation Paths", description = "Add AEM Asset paths where to generate custom renditions to use on nextgen components.")
		String[] imageRenditionGenerationPaths();

		@AttributeDefinition(name = "API Hostname", description = "The hostname of the Wyndham Back End Services API.  Default value is " + DEFAULT_API_HOSTNAME)
		String apiHostname() default DEFAULT_API_HOSTNAME;

		@AttributeDefinition(name = "EUM Script Thunk", description = "This EUM script will be placed into all pages for the site in its entirety, verbatim, before the HEAD html element and must be valid HTML.")
		String eumScriptThunk() default "";
		
		@AttributeDefinition(name = "Launch Script", description = "This Launch script will be placed into all pages for the site before the HEAD html element and must be valid HTML.")
		String launchScript() default "";

		@AttributeDefinition(name = "Key to get access to hotel data", description = "Key to get access to hotel data")
		String hotelAccessKey() default "wyndhamhotels";
	}
	
	private String schedulerExpression;

	private boolean schedulerConcurrent;
	
	private boolean disableImportResortScheduler;

	private String schedulerName;
	
	private String rsaKeyFilePath;
	
	private String resortsFilePath;
	
	private String resortContentPath;
	
	private String resortFragmentPath;
	
	private String resortTagsPath;
	
	private String resortFragmentPlaceholderPath;

	private String resortTemplatePath;
	
	private String resortDetailComponentPath;
	
	private String resortRoomTypesComponentPath;
	
	private String resortGalleryImagePath;
	
	private String importLogPath;

	private int maxDaysToKeepLogs;

	private boolean saveLogFile;

	private String googleMapKey;

	private String captchaKey;
	
	private String sftpHost;
	
	private int sftpPort;
	
	private String sftpUserName;

	private String yextReviewAPIUrl;
	
	private boolean yextReviewAPIDisable;
	
	private String[] imageRenditionGenerationPaths;
	
	private boolean skipSFTPFileDownload;

	private String apiHostname;

	private String eumScriptThunk;
	
	private String launchScript;

	private String hotelAccessKey;
	
	@Activate
	protected void activate(final Config config) {
		this.schedulerName = config.schedulerName();
		this.schedulerExpression = config.schedulerExpression();
		this.schedulerConcurrent = config.schedulerConcurrent();
		this.disableImportResortScheduler = config.disableImportResortScheduler();
		this.importLogPath = config.importLogPath();
		this.maxDaysToKeepLogs = config.maxDaysToKeepLogs();
		this.saveLogFile = config.saveLogFile();
		this.resortContentPath = config.resortContentPath();
		this.resortTemplatePath = config.resortTemplatePath();
		this.resortDetailComponentPath = config.resortDetailComponentPath();
		this.resortRoomTypesComponentPath = config.resortRoomTypesComponentPath();
		this.rsaKeyFilePath = config.rsaKeyFilePath();
		this.resortsFilePath = config.resortsFilePath();
		this.resortFragmentPath = config.resortFragmentPath();
		this.resortTagsPath = config.resortTagsPath();
		this.resortFragmentPlaceholderPath = config.resortFragmentPlaceholderPath();
		this.resortGalleryImagePath = config.resortGalleryImagePath();
		this.sftpHost = config.sftpHost();
		this.sftpPort = config.sftpPort();
		this.sftpUserName = config.sftpUserName();
		this.googleMapKey = config.googleMapKey();
		this.captchaKey = config.captchaKey();
		this.yextReviewAPIUrl = config.yextReviewAPIUrl();
		this.yextReviewAPIDisable = config.yextReviewAPIDisable();
		this.imageRenditionGenerationPaths = config.imageRenditionGenerationPaths().clone();
		this.skipSFTPFileDownload = config.skipSFTPFileDownload();
		this.apiHostname = config.apiHostname();
		this.eumScriptThunk = config.eumScriptThunk();
		this.launchScript = config.launchScript();
		this.hotelAccessKey = config.hotelAccessKey();
	}

	/**
	 * @return the schedulerExpression
	 */
	public String getSchedulerExpression() {
		return schedulerExpression;
	}

	/**
	 * @return the schedulerConcurrent
	 */
	public boolean isSchedulerConcurrent() {
		return schedulerConcurrent;
	}

	/**
	 * @return the disableImportResortScheduler
	 */
	public boolean isDisableImportResortScheduler() {
		return disableImportResortScheduler;
	}

	/**
	 * @return the schedulerName
	 */
	public String getSchedulerName() {
		return schedulerName;
	}

	/**
	 * @return the importLogPath
	 */
	public String getImportLogPath() {
		return importLogPath;
	}

	/**
	 * @return the maxDaysToKeepLogs
	 */
	public int getMaxDaysToKeepLogs() {
		return maxDaysToKeepLogs;
	}

	/**
	 * @return the saveLogFile
	 */
	public boolean isSaveLogFile() {
		return saveLogFile;
	}

	public boolean isYextReviewAPIDisable() {
		return yextReviewAPIDisable;
	}

	/**
	 * @return the resortContentPath
	 */
	public String getResortContentPath() {
		return resortContentPath;
	}

	/**
	 * @return the resortTemplatePath
	 */
	public String getResortTemplatePath() {
		return resortTemplatePath;
	}

	/**
	 * @return the resortDetailComponentPath
	 */
	public String getResortDetailComponentPath() {
		return resortDetailComponentPath;
	}

	/**
	 * @return the rsaKeyFilePath
	 */
	public String getRsaKeyFilePath() {
		return rsaKeyFilePath;
	}

	/**
	 * @return the resortsFilePath
	 */
	public String getResortsFilePath() {
		return resortsFilePath;
	}

	/**
	 * @return the resortFragmentPath
	 */
	public String getResortFragmentPath() {
		return resortFragmentPath;
	}

	/**
	 * @return the resortTagsPath
	 */
	public String getResortTagsPath() {
		return resortTagsPath;
	}

	/**
	 * @return the resortFragmentPlaceholderPath
	 */
	public String getResortFragmentPlaceholderPath() {
		return resortFragmentPlaceholderPath;
	}

	/**
	 * @return the googleMapKey
	 */
	public String getGoogleMapKey() {
		return googleMapKey;
	}

	/**
	 * @return the captchaKey
	 */
	public String getCaptchaKey() {
		return captchaKey;
	}

	/**
	 * @return the sftpHost
	 */
	public String getSftpHost() {
		return sftpHost;
	}

	/**
	 * @return the sftpPort
	 */
	public int getSftpPort() {
		return sftpPort;
	}

	/**
	 * @return the sftpUserName
	 */
	public String getSftpUserName() {
		return sftpUserName;
	}

	/**
	 * @return the resortGalleryImagePath
	 */
	public String getResortGalleryImagePath() {
		return resortGalleryImagePath;
	}

	/**
	 * @return the yextReviewAPIUrl
	 */
	public String getYextReviewAPIUrl() {
		return yextReviewAPIUrl;
	}

	/**
	 * @return the resortRoomTypesComponentPath
	 */
	public String getResortRoomTypesComponentPath() {
		return resortRoomTypesComponentPath;
	}

	/**
	 * @return the imageRenditionGenerationPaths
	 */
	public String[] getImageRenditionGenerationPaths() {
		return imageRenditionGenerationPaths;
	}

	/**
	 * @return the skipSFTPFileDownload
	 */
	public boolean isSkipSFTPFileDownload() {
		return skipSFTPFileDownload;
	}

	/**
	 * @return the apiHostname
	 */
	public String getApiHostname() {
		return apiHostname;
	}

	/**
	 * @return the eumScriptThunk
	 */
	public String getEumScriptThunk() {
		return eumScriptThunk;
	}
	
	/**
	 * @return the launchScript
	 */
	public String getLaunchScript() {
		return launchScript;
	}

	/**
	 * @return the hotelAccessKey
	 */
	public String getHotelAccessKey() {
		return hotelAccessKey;
	}
}

