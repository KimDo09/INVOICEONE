/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
//TODO: move this class into oauth-core-consumer, together with HttpMessage.
//The sticky part is deleting the method OAuthMessage.toHttpRequest.
/** A decorator that handles Content-Encoding. */
public class HttpMessageDecoder extends HttpResponseMessage {

 /**
  * Decode the given message if necessary and possible.
  * 
  * @return a decorator that decodes the body of the given message; or the
  *         given message if this class can't decode it.
  */
 public static HttpResponseMessage decode(HttpResponseMessage message)
         throws IOException {
     if (message != null) {
         String encoding = getEncoding(message);
         if (encoding != null) {
             return new HttpMessageDecoder(message, encoding);
         }
     }
     return message;
 }

 public static final String GZIP = "gzip";
 public static final String DEFLATE = "deflate";
 public static final String ACCEPTED = GZIP + "," + DEFLATE;

 private static String getEncoding(HttpMessage message) {
     String encoding = message.getHeader(CONTENT_ENCODING);
     if (encoding == null) {
         // That's easy.
     } else if (GZIP.equalsIgnoreCase(encoding)
             || ("x-" + GZIP).equalsIgnoreCase(encoding)) {
         return GZIP;
     } else if (DEFLATE.equalsIgnoreCase(encoding)) {
         return DEFLATE;
     }
     return null;
 }

 private HttpMessageDecoder(HttpResponseMessage in, String encoding)
         throws IOException {
     super(in.method, in.url);
     this.headers.addAll(in.headers);
     removeHeaders(CONTENT_ENCODING); // handled here
     removeHeaders(CONTENT_LENGTH); // unpredictable
     InputStream body = in.getBody();
     if (body != null) {
         if (encoding == GZIP) {
             body = new GZIPInputStream(body);
         } else if (encoding == DEFLATE) {
             body = new InflaterInputStream(body);
         } else {
             assert false;
         }
     }
     this.body = body;
     this.in = in;
 }

 private final HttpResponseMessage in;

 @Override
 public void dump(Map<String, Object> into) throws IOException {
     in.dump(into);
 }

 @Override
 public int getStatusCode() throws IOException {
     return in.getStatusCode();
 }

}
