/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * @author John Kristian
 */
class HMAC_SHA1 extends OAuthSignatureMethod {

    @Override
    protected String getSignature(String baseString) throws OAuthException {
        try {
            String signature = base64Encode(computeSignature(baseString));
            return signature;
        } catch (GeneralSecurityException e) {
            throw new OAuthException(e);
        } catch (UnsupportedEncodingException e) {
            throw new OAuthException(e);
        }
    }

    @Override
    protected boolean isValid(String signature, String baseString)
    throws OAuthException {
        try {
            byte[] expected = computeSignature(baseString);
            byte[] actual = decodeBase64(signature);
            return Arrays.equals(expected, actual);
        } catch (GeneralSecurityException e) {
            throw new OAuthException(e);
        } catch (UnsupportedEncodingException e) {
            throw new OAuthException(e);
        }
    }

    private byte[] computeSignature(String baseString)
            throws GeneralSecurityException, UnsupportedEncodingException {
        SecretKey key = null;
        synchronized (this) {
            if (this.key == null) {
                String keyString = OAuth.percentEncode(getConsumerSecret())
                        + '&' + OAuth.percentEncode(getTokenSecret());
                byte[] keyBytes = keyString.getBytes(ENCODING);
                this.key = new SecretKeySpec(keyBytes, MAC_NAME);
            }
            key = this.key;
        }
        Mac mac = Mac.getInstance(MAC_NAME);
        mac.init(key);
        byte[] text = baseString.getBytes(ENCODING);
        return mac.doFinal(text);
    }

    /** ISO-8859-1 or US-ASCII would work, too. */
    private static final String ENCODING = OAuth.ENCODING;

    private static final String MAC_NAME = "HmacSHA1";

    private SecretKey key = null;

    @Override
    public void setConsumerSecret(String consumerSecret) {
        synchronized (this) {
            key = null;
        }
        super.setConsumerSecret(consumerSecret);
    }

    @Override
    public void setTokenSecret(String tokenSecret) {
        synchronized (this) {
            key = null;
        }
        super.setTokenSecret(tokenSecret);
    }

}