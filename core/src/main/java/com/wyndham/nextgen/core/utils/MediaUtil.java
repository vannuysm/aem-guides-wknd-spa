package com.wyndham.nextgen.core.utils;

import static com.day.cq.dam.api.DamConstants.DC_DESCRIPTION;
import static com.day.cq.dam.api.DamConstants.DC_TITLE;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.resource.details.AssetDetails;
import com.wyndham.nextgen.core.constants.WyndhamConstants;
import com.wyndham.nextgen.core.entity.ResortEntity.MediaGallery;

/**
 * The Class MediaUtil.
 */
public class MediaUtil {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(MediaUtil.class);
	
	/**
	 * Checks if is video type.
	 *
	 * @param url the url
	 * @param pattern the pattern
	 * @return true, if is video type
	 */
	public static boolean isVideoType(String url,String pattern) {

	    Pattern compiledPattern = Pattern.compile(pattern);
	    Matcher matcher = compiledPattern.matcher(url); 

	    return matcher.find();
	}

	/**
	 * Gets the vimeo video id.
	 *
	 * @param url the url
	 * @return the vimeo video id
	 */
	public static String getVimeoVideoId(String url) {
		Pattern pattern = Pattern.compile(WyndhamConstants.PATTERN_VIMEOURL);
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			return matcher.group(4);
		}
		return null;
	}
	
	/**
	 * Gets the video id.
	 *
	 * @param url the url
	 * @param pattern the pattern
	 * @return the video id
	 */
	public static String getvideoId(String url,String pattern) {
	    Pattern compiledPattern = Pattern.compile(pattern);
	    Matcher matcher = compiledPattern.matcher(url);
	    if (matcher.find()) {
	    	return matcher.group();
	    }
	   return null;
	}
	
	/**
	 * Gets the embed string.
	 *
	 * @param url the url
	 * @param embededPattern the embeded pattern
	 * @return the embed string
	 */
	public static String getEmbedString(String url,String embededPattern){
		return embededPattern.replace("URL", url);
	}
	
	/**
	 * Gets the image width.
	 *
	 * @param resourceResolver the resource resolver
	 * @param imagePath the image path
	 * @return the image width
	 */
	public static long getImageWidth(ResourceResolver resourceResolver, String imagePath) {
		try {
			Resource imageRes = resourceResolver.getResource(imagePath);
			if (imageRes != null) {
				AssetDetails assetDetails = new AssetDetails(imageRes);
				return assetDetails.getWidth();
			}
		} catch (RepositoryException ex) {
			LOG.error(ex.getMessage() + ex);
		} 
		return 0;
	}

	/**
	 * Gets the image height.
	 *
	 * @param resourceResolver the resource resolver
	 * @param imagePath the image path
	 * @return the image height
	 */
	public static long getImageHeight(ResourceResolver resourceResolver, String imagePath) {
		try {
			Resource imageRes = resourceResolver.getResource(imagePath);
			if (imageRes != null) {
				AssetDetails assetDetails = new AssetDetails(imageRes);
				return assetDetails.getHeight();
			}
		} catch (RepositoryException ex) {
			LOG.error(ex.getMessage() + ex);
		} 
		return 0;
	}
	
	/**
	 * Gets the image title.
	 *
	 * @param resourceResolver the resource resolver
	 * @param imagePath the image path
	 * @return the image title
	 */
	public static String getImageTitle(ResourceResolver resourceResolver, String imagePath) {
		try {
			Resource imageRes = resourceResolver.getResource(imagePath);
			if (imageRes != null) {
				AssetDetails assetDetails = new AssetDetails(imageRes);
				return assetDetails.getDescription(); 
			}
		} catch (RepositoryException ex) {
			LOG.error(ex.getMessage() + ex);
		}
		return "";
	}
	
	/**
	 * Gets the media gallery item.
	 *
	 * @param resourceResolver the resource resolver
	 * @param imagePath the image path
	 * @return the media gallery item
	 */
	public static MediaGallery getMediaGalleryItem(ResourceResolver resourceResolver, String imagePath) {
		try {
			Resource imageRes = resourceResolver.getResource(imagePath);
			if (imageRes != null) {
				MediaGallery mediaGallery = new MediaGallery();
				AssetDetails assetDetails = new AssetDetails(imageRes);
				
				String title = assetDetails.getAsset().getMetadataValue(DC_TITLE);
				
				if(title == null || title.isEmpty()) {
					title = assetDetails.getDescription();
				}
				mediaGallery.setImageAlt(title);
				mediaGallery.setImageTitle(title);
				mediaGallery.setImageSrc(assetDetails.getAsset().getPath());
				mediaGallery.setImageHeight(assetDetails.getHeight());
				mediaGallery.setImageWidth(assetDetails.getWidth());
				if(assetDetails.getMimeType().contains("video")) {
					mediaGallery.setMediaType("video");
					mediaGallery.setEmbedVideoCode(getEmbedString(mediaGallery.getImageSrc(),WyndhamConstants.EMBEDED_VIDEO));
				} else {
					mediaGallery.setMediaType("image");
				}
				
				return mediaGallery;
			}
		} catch (RepositoryException ex) {
			LOG.error(ex.getMessage() + ex);
		}
		return null;
	}
	
	/**
     * Looks for an image in the DAM for a given image path, converts it to an Asset object and fetches the metadata of
     * it. Extracts "dc:title" property as the alternative text of the image or "dc:description" when asset title is
     * empty, or an empty string if both are empty.
     * @param imagePath the image path
     * @param resolver a ResourceResolver object with admin access right
     * @return an alternative text for the image or an empty string
     */
    public static String getImageAlt(final String imagePath,
                               final ResourceResolver resolver) {

        String imageAlt = "";

        final Resource imageResource = resolver.getResource(imagePath);
        if (imageResource != null) {
            final Asset asset = imageResource.adaptTo(Asset.class);

            if(asset != null){
                final Map<String, Object> metadata = asset.getMetadata();
                if (metadata.containsKey(DC_TITLE)) {
                    imageAlt = asset.getMetadataValue(DC_TITLE);
                } else if (metadata.containsKey(DC_DESCRIPTION)) {
                    imageAlt = asset.getMetadataValue(DC_DESCRIPTION);
                }

            }
        }

        return imageAlt;
    }
}
