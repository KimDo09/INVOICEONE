/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;

/**
 * @author John Kristian
 */
class PLAINTEXT extends OAuthSignatureMethod {

    @Override
    public String getSignature(String baseString) {
        return getSignature();
    }

    @Override
    protected boolean isValid(String signature, String baseString)
            throws OAuthException {
        return signature.equals(getSignature());
    }

    private synchronized String getSignature() {
        if (signature == null) {
            signature = OAuth.percentEncode(getConsumerSecret()) + '&'
                    + OAuth.percentEncode(getTokenSecret());
        }
        return signature;
    }

    private String signature = null;

    @Override
    public void setConsumerSecret(String consumerSecret) {
        synchronized (this) {
            signature = null;
        }
        super.setConsumerSecret(consumerSecret);
    }

    @Override
    public void setTokenSecret(String tokenSecret) {
        synchronized (this) {
            signature = null;
        }
        super.setTokenSecret(tokenSecret);
    }

}