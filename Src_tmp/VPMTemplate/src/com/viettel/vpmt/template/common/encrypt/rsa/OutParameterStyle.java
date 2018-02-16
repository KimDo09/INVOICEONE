/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;

/**
 * Where to place OAuth parameters in an HTTP message. The alternatives are
 * summarized in OAuth Core section 5.2.
 */
public enum OutParameterStyle {
    /**
     * Send parameters whose names begin with "oauth_" in an HTTP header, and
     * other parameters (whose names don't begin with "oauth_") in either the
     * message body or URL query string. The header formats are specified by
     * OAuth Core section 5.4.
     */
    AUTHORIZATION_HEADER,

    /**
     * Send all parameters in the message body, with a Content-Type of
     * application/x-www-form-urlencoded.
     */
    BODY,

    /** Send all parameters in the query string part of the URL. */
    QUERY_STRING;
}
