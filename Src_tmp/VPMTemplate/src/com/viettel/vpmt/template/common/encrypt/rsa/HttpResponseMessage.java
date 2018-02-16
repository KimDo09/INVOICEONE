/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
//TODO: move this class into oauth-core-consumer, together with HttpMessage.
//The sticky part is deleting the method OAuthMessage.toHttpRequest.
/**
* An HTTP response.
* 
* @author John Kristian
*/
public abstract class HttpResponseMessage extends HttpMessage {

 protected HttpResponseMessage(String method, URL url) {
     super(method, url);
 }

 @Override
 public void dump(Map<String, Object> into) throws IOException {
     super.dump(into);
     into.put(STATUS_CODE, Integer.valueOf(getStatusCode()));
     String location = getHeader(LOCATION);
     if (location != null) {
         into.put(LOCATION, location);
     }
 }

 public abstract int getStatusCode() throws IOException;

 /** The name of a dump entry whose value is the response Location header. */
 public static final String LOCATION = "Location";

 /** The name of a dump entry whose value is the HTTP status code. */
 public static final String STATUS_CODE = "HTTP status";

 /** The statusCode that indicates a normal outcome. */
 public static final int STATUS_OK = 200;

 /** The standard end-of-line marker in an HTTP message. */
 public static final String EOL = "\r\n";

}
