/**
 * Copyright 2014 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import com.example.androidvtmtlib.R;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 
 * lop animation factory de tao hinh anh chuyen dong
 * 
 * @author: Duchha
 * @version: 1.0
 * @since: 1.0
 */
public class AnimationUtils {
	/**
	 * @return Animation that moves from left to position of View
	 */
	public static Animation inFromLeft() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	/**
	 * @return Animation that moves from Right to position of View
	 */
	public static Animation inFromRight() {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	/**
	 * @return Animation that moves from Top to position of View
	 */
	public static Animation inFromTop() {
		Animation inFromTop = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromTop.setInterpolator(new AccelerateInterpolator());
		return inFromTop;
	}

	/**
	 * @return Animation that moves from Bottom to position of View
	 */
	public static Animation inFromBottom() {
		Animation inFromBottom = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromBottom.setInterpolator(new AccelerateInterpolator());
		return inFromBottom;
	}

	/**
	 * @return Animation that fades in
	 */
	public static Animation inFade() {
		Animation inFade = new AlphaAnimation(0.0f, 1.0f);
		inFade.setInterpolator(new AccelerateInterpolator());
		return inFade;
	}

	/**
	 * @return Animation that moves from position of View to left
	 */
	public static Animation outToLeft() {
		Animation outToLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outToLeft.setInterpolator(new AccelerateInterpolator());
		return outToLeft;
	}

	/**
	 * @return Animation that moves from position of View to right
	 */
	public static Animation outToRight() {
		Animation outToRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outToRight.setInterpolator(new AccelerateInterpolator());
		return outToRight;
	}

	/**
	 * @return Animation that moves from position of View to top
	 */
	public static Animation outToTop() {
		Animation outToTop = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f);
		outToTop.setInterpolator(new AccelerateInterpolator());
		return outToTop;
	}

	/**
	 * @return Animation that moves from position of View to bottom
	 */
	public static Animation outToBottom() {
		Animation outToBottom = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f);
		outToBottom.setInterpolator(new AccelerateInterpolator());
		return outToBottom;
	}

	/**
	 * @return Animation that fades out
	 */
	public static Animation outFade() {
		Animation outFade = new AlphaAnimation(1.0f, 0.0f);
		outFade.setInterpolator(new AccelerateInterpolator());
		return outFade;
	}

	public static Animation toLeft1() {
		Animation outToLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -0.15f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outToLeft.setDuration(5000);
		return outToLeft;
	}

	public static Animation toRight1() {
		Animation outToRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -0.15f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outToRight.setDuration(5000);
		return outToRight;
	}

	/**
	 * 
	 * Giam dan Alpha de an
	 * 
	 * @author: Duchha
	 * @return
	 * @return: Animation
	 * @throws:
	 */
	public static Animation alphaHide() {
		Animation outFade = new AlphaAnimation(1.0f, 0.2f);
		outFade.setDuration(1000);
		outFade.setInterpolator(new AccelerateInterpolator());
		return outFade;
	}

	/**
	 * 
	 * tang dan alpha de hien thi
	 * 
	 * @author: Duchha
	 * @return
	 * @return: Animation
	 * @throws:
	 */
	public static Animation alphaShow() {
		Animation outFade = new AlphaAnimation(0.2f, 1.0f);
		outFade.setDuration(1000);
		outFade.setInterpolator(new AccelerateInterpolator());
		return outFade;
	}
	
	/**
	 * 
	 *  Slide Up
	 *  @author: hainm22
	 *  @param context
	 *  @return
	 *  @return: Animation
	 *  @throws:
	 */
	public static Animation slideup(Context context) {
		Animation slideup = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.slide_up);
		return slideup;

	}
	
	/**
	 * 
	 *  Slide Down
	 *  @author: hainm22
	 *  @param context
	 *  @return
	 *  @return: Animation
	 *  @throws:
	 */
	public static Animation slidedown(Context context) {
		Animation slidedown = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.slide_down);
		return slidedown;

	}
	
	/**
	 * 
	 *  Slide Down
	 *  @author: hainm22
	 *  @param context
	 *  @return
	 *  @return: Animation
	 *  @throws:
	 */
	public static Animation warning(Context context) {
		Animation warning = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.warning);
		return warning;

	}
}
