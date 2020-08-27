 /**
 * $Id$
 */
 package com.advancestores.alexa.order.controller;
/**
 * This class is responsible for defining custom media types that indicate versions of the resources
 * provided by the Catalog service.
 * 
 * @author $Author$ (last modified by)
 * @version $Revision$
 */
public class VersionedMediaTypes {
    private static final String BASE = 
            "application/vnd.com.advancestores";
     /**
     * JSON appendage to custom mime type.
     */
    public static final String JSON_EXTENSION = "+json";

    /**
     * Charset appendage for custom mime type.
     */
    public static final String CHARSET = ";charset=UTF-8";

    /**
     * Version number prefix for custom mime type.
     */
    public static final String API_VERSION_PREFIX = ";v=";

    private static final String VERSION_1_0 = API_VERSION_PREFIX + "1";
    
    private static final String RESOURCE = ".alexa-order";

    /**
     * Custom media type for versioning resources
     */
    public static final String CATALOG_CONTENT_TYPE_JSON_1_0 = BASE + RESOURCE
                                + JSON_EXTENSION + VERSION_1_0 + CHARSET;

    private VersionedMediaTypes() {
        // Privatized constructor to hide the implicit public constructor.
    }
}

