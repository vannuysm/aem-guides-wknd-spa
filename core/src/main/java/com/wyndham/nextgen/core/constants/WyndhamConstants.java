package com.wyndham.nextgen.core.constants;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.wcm.api.NameConstants;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;

/**
 * Wyndham constants: Interface where all constants are added.
 */
public interface WyndhamConstants {

    /**
     * The constant ROOT.
     */
	String DEFAULT_LANGUAGE_ROOT = "/content/wyndham/nextgen/us/en";
    String DAM_ROOT = "/content/dam/wyndham";
    String PROP_NAV_ROOT = "navRoot";
    String DEFAULT_HTTP_STRING = "ht"+"tp";
    
    /**
     * Resolver service constant.
     */
    String PATH_SEPERATOR = "/";
    String READ_RESOLVER = "readService";
	String READ_WRITE_RESOLVER= "readWriteService";
	String ADMINUSER_RESOLVER = "adminuser";
	
	/**
     * Query builder constant.
     */
    String QUERY_CONSTANT_PATH = "path";
    String QUERY_CONSTANT_TYPE = "type";
    String QUERY_OFFSET = "p.offset";
    String QUERY_LIMIT = "p.limit";
    String QUERY_ORDERBY = "orderby";
    String QUERY_ODERBY_SORT = "orderby.sort";
    String QUERY_ORDER_AESC = "asc";
    String QUERY_ORDER_DESC = "desc";
    String QUERY_CONSTANT_DAMASSET = DamConstants.NT_DAM_ASSET;
    String QUERY_CONSTANT_NODENAME = "nodename";
    String QUERY_CONSTANT_CQPAGE = NameConstants.NT_PAGE;
    String QUERY_PROPERTY = "property";
    String QUERY_PROPERTY_VALUE = "property.value";
    String QUERY_PROPERTY1 = "1_property";
    String QUERY_PROPERTY1_VALUE = "1_property.value";
    String QUERY_PROPERTY2 = "2_property";
    String QUERY_PROPERTY2_VALUE = "2_property.value";
    String RESORT_NUMBER ="jcr:content/resortNumber";

    
    /**
     * Page properties and values 
     */
    String CQ_PAGE = NameConstants.NT_PAGE;
    String CQ_PAGE_CONTENT = "cq:PageContent";
    String CQ_TEMPLATE = NameConstants.NN_TEMPLATE;
    String SLING_RESOURCE_TYPE = JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY;
    String SLING_FOLDER = JcrResourceConstants.NT_SLING_FOLDER;
    String NT_UNSTRUCTED = JcrConstants.NT_UNSTRUCTURED;
    String PARSYS_NODE_NAME = "par";
    String JCR_TITLE = JcrConstants.JCR_TITLE;
    String JCR_CONTENT = JcrConstants.JCR_CONTENT;
    String JCR_CONTENT_CQ_TEMPLATE = "jcr:content/cq:template";
    String CQ_LASTREPLICATED = NameConstants.PN_PAGE_LAST_REPLICATED;
    String JCR_ASSET_METATATA_RELATIVE_PATH = "jcr:content/metadata";
	String CFM_MASTER_VARIATION_PATH = "/jcr:content/data/master";
	
    /**
     * Import log fields
     * 
     */
    String PROCESS_TYPE_FIELD = "type";
    String STARTED_TIME_FIELD = "startedTime";
    String FINISHED_TIME_FIELD = "finishedTime";
    String ERRORS_FIELD = "errors";

    /**
     * Header constants
     */
    String REDIRECT_RESOURCE_TYPE = "foundation/components/redirect";
	String PROP_REDIRECT_TARGET = "redirectTarget";
    String PROP_HIDE_IN_NAV = "hideInNav";
    String BC_HIDE_PROPERTY = "hideBreadCrumb";
    String PROP_HIDE_SUB_IN_NAV = "hideSubItemsInNav";
	boolean PROP_OPEN_LINK_IN_NEW_WINDOW_DEFAULT = false;
	String PROP_NAVIGATION_ROOT_PATH_DEFAULT = "/content/wyndham/nextgen/us/en";
	int PROP_NAVIGATION_DEPTH_DEFAULT = 3;
    String PROP_LOGO_URL = "logoLinkUrl";
    String PROP_DESKTOP_LOGO_SRC = "desktopLogoSrc";
    String PROP_MOBILE_LOGO_SRC = "mobileLogoSrc";
    String PROP_LOGO_ALT_TEXT = "logoAltText";
    String PROP_LOGO_OPEN_IN_NEW_WINDOW = "logoOpenInNewWindow";
    String PROP_NAVIGATION_ROOTPATH = "navigationRootPath";
    String PROP_NAVIGATION_DEPTH = "navigationDepth";
	String HEADER_BUTTONS = "headerButtons";

	/**
     * Footer constants
     */
	
	String PROP_SEARCH_LINK = "searchLink";
	String PROP_SEARCH_PLACEHOLDER_TEXT = "searchPlaceholderText";
	String PROP_SEARCH_BUTTON_TEXT = "searchButtonText";
	String PROP_FOOTERCOLUMN1_HEADING = "footerColumn1Heading";
	String PROP_FOOTERCOLUMN2_HEADING = "footerColumn2Heading";
	String PROP_FOOTERCOLUMN3_HEADING = "footerColumn3Heading";
	String PROP_FOOTERCOLUMN4_HEADING = "footerColumn4Heading";
	String PROP_FOOTERCOLUMN5_HEADING = "footerColumn5Heading";
    String PROP_FOOTERCOLUMN1_HEADING_LINK = "footerColumn1HeadingLink";
	String PROP_FOOTERCOLUMN2_HEADING_LINK = "footerColumn2HeadingLink";
	String PROP_FOOTERCOLUMN3_HEADING_LINK = "footerColumn3HeadingLink";
	String PROP_FOOTERCOLUMN4_HEADING_LINK = "footerColumn4HeadingLink";
	String PROP_FOOTERCOLUMN5_HEADING_LINK = "footerColumn5HeadingLink";
	String CHILD_FOOTERCOLUMN1_LINKS = "footerColumn1Links";
	String CHILD_FOOTERCOLUMN2_LINKS = "footerColumn2Links";
	String CHILD_FOOTERCOLUMN3_LINKS = "footerColumn3Links";
	String CHILD_FOOTERCOLUMN4_LINKS = "footerColumn4Links";
	String CHILD_FOOTERCOLUMN5_LINKS = "footerColumn5Links";
	String PROP_LOGO_URL_FOOTER = "logoLinkUrl";
    String PROP_DESKTOP_LOGO_SRC_FOOTER = "logoSrc";
    String PROP_LOGO_ALT_TEXT_FOOTER = "logoAltText";
    String PROP_LOGO_OPEN_IN_NEW_WINDOW_FOOTER = "logoOpenInNewWindow";
	String CHILD_SOCIALMEDIA_LINKS = "socialmedia";
	String PROP_COPYRIGHT_TEXT_1 = "copyrightText1";
	String PROP_COPYRIGHT_TEXT_2 = "copyrightText2";
	String PROP_TIMESHARE_LEGAL_COPY = "timeShareLegalCopy";
	String CHILD_QUICK_LINKS = "quicklinks";
	
	/**
     * Resort and Product Hub file constants
     */
	String FILENAME_PRODUCT_HUB_RESORT = "cui_teamsite_resort.txt";
	String FILENAME_PRODUCT_HUB_RESORT_FEATURES = "cui_teamsite_resort_feature.txt";
	String FILENAME_PRODUCT_HUB_RESORT_UNIT_FEATURES = "cui_teamsite_unit_feature.txt";
	String FILE_MIMETYPE = "text/plain";
	String PROP_PRODUCT_HUB_ID = "productHubId";
	String PROP_ROOM_TYPES = "roomTypes";
	String PROP_ROOM_CATEGORIES = "roomCategories";
	String PROP_HIDE_THIS_ROOM_TYPE = "hideThisRoomType";
	String PROP_HIDE_THIS_ROOM_CATEGORY = "hideThisRoomCategory";
	
	String TAG_PREFIX_PRODUCT_ID = "wyndham:nextgen/resorts/product-id/";
	String TAG_PREFIX_ROOM_TYPE = "wyndham:nextgen/resorts/room-types/";
	String TAG_PREFIX_ROOM_CATEGORY = "wyndham:nextgen/resorts/room-categories/";
	String TAG_PREFIX_RESORT_BANNER = "wyndham:nextgen/resorts/banner/";
	
	// Resort Room Types
	String ONE_BEDROOM = "1 Bedroom";
	String TWO_BEDROOM = "2 Bedroom";
	String THREE_BEDROOM = "3 Bedroom";
	String FOUR_BEDROOM = "4 Bedroom";
	String FIVE_BEDROOM = "5 Bedroom";
	String STUDIO = "Studio";
	String HOTEL_ROOM = "Hotel Room";
	
	// Template Paths
	String BLANK_TEMPLATE_PATH = "/conf/wyndham-nextgen/settings/wcm/templates/blank-page/initial/jcr:content";
	
	// Image gallery constants to process Video URLs
	String PATTERN_YOUTUBEURL = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
	String PATTERN_VIMEOURL = "((http://|https://){0,1}vimeo.com/(m/){0,1}){0,1}(\\d+)[^0-9]*"; 
	
	String EMBEDED_YOUTUBE = "<iframe class=\"pswp__video\" width=\"960\" height=\"640\" src=\"https://www.youtube.com/embed/URL\" frameborder=\"0\" allowfullscreen></iframe>";
	String EMBEDED_VIMEO ="<iframe class=\"pswp__video\" width=\"960\" height=\"640\" src=\"https://player.vimeo.com/video/URL\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>";
	String EMBEDED_VIDEO = "<video width=\"960\" class=\"pswp__video\" src=\"URL\" height=\"100%\" controls>";
}

