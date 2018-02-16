/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;
import java.io.IOException;
import java.net.URISyntaxException;
/**
 * An algorithm to determine whether a message has a valid signature, a correct
 * version number, a fresh timestamp, etc.
 *
 * @author Dirk Balfanz
 * @author John Kristian
 */
public interface OAuthValidator {

    /**
     * Check that the given message from the given accessor is valid.
     * @throws OAuthException TODO
     * @throws IOException TODO
     * @throws URISyntaxException 
     * @throws OAuthProblemException the message is invalid.
     * The implementation should throw exceptions that conform to the OAuth
     * <a href="http://wiki.oauth.net/ProblemReporting">Problem Reporting extension</a>.
     */
    public void validateMessage(OAuthMessage message, OAuthAccessor accessor)
            throws OAuthException, IOException, URISyntaxException;

}
