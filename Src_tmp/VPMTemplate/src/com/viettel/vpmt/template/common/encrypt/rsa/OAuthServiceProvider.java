/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;
import java.io.Serializable;
/**
 * Properties of an OAuth Service Provider.
 * 
 * @author John Kristian
 */
public class OAuthServiceProvider implements Serializable {

    private static final long serialVersionUID = 3306534392621038574L;

    public final String requestTokenURL;
    public final String userAuthorizationURL;
    public final String accessTokenURL;

    public OAuthServiceProvider(String requestTokenURL,
            String userAuthorizationURL, String accessTokenURL) {
        this.requestTokenURL = requestTokenURL;
        this.userAuthorizationURL = userAuthorizationURL;
        this.accessTokenURL = accessTokenURL;
    }

}
