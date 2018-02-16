/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * Properties of an OAuth Consumer. Properties may be added freely, e.g. to
 * support extensions.
 * 
 * @author John Kristian
 */
public class OAuthConsumer implements Serializable {

    private static final long serialVersionUID = -2258581186977818580L;

    public final String callbackURL;
    public final String consumerKey;
    public final String consumerSecret;
    public final OAuthServiceProvider serviceProvider;

    public OAuthConsumer(String callbackURL, String consumerKey,
            String consumerSecret, OAuthServiceProvider serviceProvider) {
        this.callbackURL = callbackURL;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.serviceProvider = serviceProvider;
    }

    private final Map<String, Object> properties = new HashMap<String, Object>();

    public Object getProperty(String name) {
        return properties.get(name);
    }

    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    /**
     * The name of the property whose value is the Accept-Encoding header in
     * HTTP requests.
     */
    public static final String ACCEPT_ENCODING = "HTTP.header." + HttpMessage.ACCEPT_ENCODING;

    /**
     * The name of the property whose value is the <a
     * href="http://oauth.pbwiki.com/AccessorSecret">Accessor Secret</a>.
     */
    public static final String ACCESSOR_SECRET = "oauth_accessor_secret";

}

