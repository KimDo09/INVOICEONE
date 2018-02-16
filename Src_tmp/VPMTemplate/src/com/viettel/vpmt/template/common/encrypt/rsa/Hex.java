/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;

/**
 *  Mo ta muc dich cua lop (interface)
 *  @author: TruongHN
 *  @version: 1.0
 *  @since: Feb 2, 2012
 */
public class Hex implements BinaryEncoder, BinaryDecoder {
	 private static final char[] DIGITS = {
		 '0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	 };
	 public static byte[] decodeHex(char[] data) throws DecoderException {
		  
		          int len = data.length;
		  
		          if ((len & 0x01) != 0) {
		              throw new DecoderException("Odd number of characters.");
		          }
		  
		          byte[] out = new byte[len >> 1];
		  
		          // two characters form the hex value.
		  for (int i = 0, j = 0; j < len; i++) {
		              int f = toDigit(data[j], j) << 4;
		              j++;
		              f = f | toDigit(data[j], j);
		              j++;
		              out[i] = (byte) (f & 0xFF);
		          }
		  
		          return out;
		      }
	 protected static int toDigit(char ch, int index) throws DecoderException {
		          int digit = Character.digit(ch, 16);
		          if (digit == -1) {
		              throw new DecoderException("Illegal hexadecimal charcter " + ch + " at index " + index);
		          }
		          return digit;
		      }

	 public static char[] encodeHex(byte[] data) {
		  
		          int l = data.length;
		  
		             char[] out = new char[l << 1];
		  
		             // two characters form the hex value.
		  for (int i = 0, j = 0; i < l; i++) {
		                 out[j++] = DIGITS[(0xF0 & data[i]) >>> 4 ];
		                 out[j++] = DIGITS[ 0x0F & data[i] ];
		             }
		  
		             return out;
		      }

	 public byte[] decode(byte[] array) throws DecoderException {
		          return decodeHex(new String  (array).toCharArray());
		      }

	 public Object   decode(Object   object) throws DecoderException {
		          try {
		              char[] charArray = object instanceof String   ? ((String  ) object).toCharArray() : (char[]) object;
		              return decodeHex(charArray);
		          } catch (ClassCastException   e) {
		              throw new DecoderException(e.getMessage());
		          }
		      }

	 public byte[] encode(byte[] array) {
		          return new String  (encodeHex(array)).getBytes();
		      }
	
	 public Object   encode(Object   object) throws EncoderException { 
		          try {
		              byte[] byteArray = object instanceof String   ? ((String  ) object).getBytes() : (byte[]) object;
		              return encodeHex(byteArray);
		          } catch (ClassCastException   e) {
		              throw new EncoderException(e.getMessage());
		          }
		      }

		 
}
