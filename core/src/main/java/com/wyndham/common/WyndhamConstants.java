package com.wyndham.common;

import com.day.cq.commons.jcr.JcrConstants;

import static com.day.cq.commons.jcr.JcrConstants.NT_UNSTRUCTURED;
import static com.day.cq.replication.ReplicationStatus.NODE_PROPERTY_LAST_REPLICATED;
import static com.day.cq.wcm.api.NameConstants.NN_TEMPLATE;
import static com.day.cq.wcm.api.NameConstants.NT_PAGE;
import static org.apache.sling.jcr.resource.api.JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY;

/**
 * Wyndham constants: Interface where all constants are added.
 */
public interface WyndhamConstants {
	
	/**
	 * Email error type
	 */
	enum ErrorType{
		GENERAL_ERROR,JOBS_ERROR
	}

	// Date format for the string
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd-HHmmSS";
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_3 = "yyyy-MM-dd HH:mm:SS";
	public static final String DATE_FORMAT_4 = "E yyyy-MM-dd, HH:mma z";
	public static final String DATE_FORMAT_5 = "yyyy-MM-dd'T'HH:mm'Z'";
	public static final String DATE_FORMAT_6 = "MMMM  YYYY";
	public static final String DATE_FORMAT_7 = "MMM dd YYYY";
	public static final String DATE_FORMAT_8 = "MMM YYYY";
	public static final String DATE_FORMAT_9 = "dd MMM YYYY";

	public final static String HYPHEN = "-";

	/**
	 * The constant ROOT.
	 */
	String ROOT = "/content/wyndham";
	/**
	 * The constant PATH_SEPERATOR.
	 */
	String PATH_SEPERATOR = "/";
	/**
	 * The constant DESTIONATIONS_US_EN_PATH.
	 */
	String DESTIONATIONS_US_EN_PATH = "/content/wyndham/destinations/us/en";

	/**
	 * The hideInNav property name
	 */
	String HIDE_IN_NAV = "hideInNav";
	String QUERY_CONSTANT_PATH = "path";
	String QUERY_CONSTANT_TYPE = "type";
	String QUERY_OFFSET = "p.offset";
	String QUERY_LIMIT = "p.limit";
	String QUERY_ORDERBY = "orderby";
	String QUERY_ODERBY_SORT = "orderby.sort";
	String QUERY_ORDER_AESC = "asc";
	String QUERY_ORDER_DESC = "desc";

	/**
	 * Page properties and values
	 */
	String CQ_PAGE = NT_PAGE;
	String CQ_PAGE_CONTENT = "cq:PageContent";
	String CQ_TEMPLATE = NN_TEMPLATE;
	String SLING_RESOURCE_TYPE = SLING_RESOURCE_TYPE_PROPERTY;
	String NT_UNSTRUCTED = NT_UNSTRUCTURED;
	String PARSYS_NODE_NAME = "par";
	String PARSYS_NODE_RESOURCE_PATH = "foundation/components/parsys";
	String JOB_COMPONENT_NAME = "career";
	String JOBENTITY_FIELD = "jobEntity";
	String JCR_TITLE = JcrConstants.JCR_TITLE;
	String CQ_LASTREPLICATED = NODE_PROPERTY_LAST_REPLICATED;
	String META_KEYWORDS = "metaKeywords";
	String META_DESCRIPTION = "metaDescription";
	
	/**
	 * Static component for job detail pages
	 */
	String BANNER_COMPONENT = "banner";
	String BANNER_COMPONENT_PATH = "wyndham-redesign/components/content/banner";
	String ARTICLES_ICONS_NODE = "articlesicons";
	String ARTICLES_ITEM = "item0";

	String BANER_PRO_FILE_REFER = "imgPath";
	String BANER_PRO_FILE_REFER_VALUE = "/content/dam/wyndham/img/temp-hero.png";
	String BANER_PRO_ALT = "altText";
	String BANER_PRO_ALT_VALUE = "BANNER";

	String BR_COMPONENT = "breadcrumb";
	String BR_COMPONENT_PATH = "wyndham-redesign/components/content/breadcrumb";
	String BR_PRO_ROOT_PATH = "rootPath";
	String BR_PRO_ROOT_PATH_VALUE = "/content/wyndham/careers/us/en";
	String BC_HIDE_PROPERTY = "hideBreadCrumb";
//    "/content/wyndham/careers/us/en/jobs".

	/**
	 * Import log fields
	 * 
	 */
	String PROCESS_TYPE_FIELD = "type";
	String STARTED_TIME_FIELD = "startedTime";
	String FINISHED_TIME_FIELD = "finishedTime";
	String JOBS_NUMBER_FIELD = "jobsNum";
	String ERRORS_FIELD = "errors";

	/**
	 * Latest Search Results constants
	 */
	String SORT_BY_DATE_ASC = "sortbydateasc";
	String SORT_BY_DATE_DESC = "sortbydatedesc";
	String LSR_TYPE_NEWS = "news";
	String LSR_TYPE_EVENTS = "events";
	

	public final static String JOB_TITLE_FORMAT = "[^0-9a-zA-Z- ]";
	public final static String PROCESS_TYPE_AUTO = "Automatic";
	public final static String PROCESS_TYPE_MANUAL = "Manual";
	/**
	 * Email error msg
	 */
	String generalMsgFormat = "<p>Error sequence: $(error sequience)</p>"+
		"<p>Error message:  $(error message)</p>"+
		"<p>Error stack trace:  $(error stack trace)</p>";
	String jobsErrMsgFormat = "<p>Job number: $(req_number)</p>"+
		"<p>Error message:  $(error message)</p>"+
		"<p>Error stack trace:  $(error stack trace)</p>";
	/*
	 * elastic search for jobs
	 */
	String SEARCH_HEADER_TYPE = "wyn-search-type";
	String SEARCH_HEADER_DOMAIN = "wyn-search-domain";
	String SEARCH_HEADER_SUBDOMAIN = "wyn-search-subdomain";
	String SEARCH_PARAM__REQNO = "ReqNo";
	String SEARCH_PARAM__JOB_ID = "job_id";
	String SEARCH_JOBID = "jobId";
	String SEARCH_TALEOID = "taleoId";
	String SEARCH_TYPE = "Careers";
	String SEARCH_DOMAIN = "WyndhamCareers";
	String SEARCH_SUBDOMAIN = "Filter";
	String SEARCH_JOBS_NODE = "jobs";
	String SEARCH_CONTENTPATH_NODE = "contentPath";
	
	//special fields for the careers jobs 
	public final static String CAREER_EMP_STATUS = "Internship";
	public final static String CAREER_POSTING_SECTION_CAMPUS= "Campus";
	public final static String CAREER_POSTING_SECTION_EXTERNAL= "External";

	/**
	 * Jobs XML data
	 */
	String XMLNS = "http://www.sitemaps.org/schemas/sitemap/0.9";
	String SITEMAP_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
			"<?xml-stylesheet type=\"text/xsl\" href=\"//careers.wyndhamworldwide.com/sitemap.xsl\"?>";
	String CHANGEFREQ = "hourly";
	String PRIORITY = "1.0";
	String BRAND = "brand";
	String JOB_CATEGORY = "job_category";
	
	
	String HTTP = "http://";
	String HTTPS = "https://";
	public final static String URL_SUFFIX = ".html";
	
	// Http headers
	String HTTP_CACHE_CONTROL = "Cache-Control";
	String HTTP_MAX_AGE = "max-age";
	String HTTP_S_MAX_AGE = "s-maxage";
	String HTTP_CONTENT_TYPE_JSON = "application/json";
	
	String CORPCOM_EMAILADDRESS = "corpcommunications@wyn.com";
	String NATION_EMAIL_KVP = "nation.emailNotificationAddress";

	/**
	 * HTTP Session Attributes
	 */
	public static final String HTTP_SESSION_MEMBERSHIP_NAME = "wyndham.session.membershipName";
	public static final String HTTP_SESSION_LOGIN_API_RESPONSE_JSON = "wyndham.api.response";

}
