package org.moflon.tgg.mosl.ui.highlighting.utils;

import org.eclipse.swt.graphics.RGB;

public enum MOSLColor {
	RED, BLUE, GREEN, YELLOW, VIOLETT, BLACK, WHITE, LIGHT_RED, DARK_RED, LIGHT_BLUE, DARK_BLUE, LIGHT_GREEN, DARK_GREEN, ORANGE, DARK_ORANGE, GRAY, BROWN, GOLD, MISTY_ROSE, DARK_GOLD, LIGHT_YELLOW;
	
	public RGB getColor(){
		switch(this){
		case BLACK:
			return new RGB(0,0,0);
		case BLUE:
			return new RGB(0,0,255);
		case BROWN:
			return new RGB(205, 133, 63);
		case DARK_BLUE:
			return new RGB(0,0,128);
		case DARK_RED:
			return new RGB(128,0,0);
		case GREEN:
			return new RGB(0,255,0);
		case LIGHT_BLUE:
			return new RGB(128,128,255);
		case LIGHT_RED:
			return new RGB(255,128,128);
		case ORANGE:
			return new RGB(255, 165, 0);
		case RED:
			return new RGB(255,0,0);
		case VIOLETT:
			return new RGB(149, 0, 85);
		case WHITE:
			return new RGB(255,255,255);
		case YELLOW:
			return new RGB(255, 255, 0);
		case DARK_GREEN:
			return new RGB(0,128,0);
		case LIGHT_GREEN:
			return new RGB(128,255,128);
		case GRAY:
			return new RGB(128,128,128);
		case DARK_GOLD:
			return new RGB(205,173,0);
		case GOLD:
			return new RGB(255,215,0);
		case DARK_ORANGE:
			return new RGB(255, 140, 0);
		case MISTY_ROSE:
			return new RGB(255, 228, 225);
		case LIGHT_YELLOW:
			return new RGB(255, 255, 224);
		default:
			return null;


		
		}
	}
}
