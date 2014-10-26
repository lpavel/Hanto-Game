/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.alpha;

import hanto.common.HantoCoordinate;

/** AlphaHantoClass that implements the interface HantoCoordinate
 */
public class AlphaHantoCoordinate implements HantoCoordinate {

	int x;
	int y;
	
	/**
	 * Constructor for AlphaHantoCoordinate.
	 * @param x int
	 * @param y int
	 */
	public AlphaHantoCoordinate(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor for AlphaHantoCoordinate.
	 * @param hantoCoordinate HantoCoordinate
	 */
	public AlphaHantoCoordinate(final HantoCoordinate hantoCoordinate) {
		x = hantoCoordinate.getX();
		y = hantoCoordinate.getY();
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	/**
	 * Getter for the coordinate in the North
	 * @return coordinates of the element in the north
	 */
	public AlphaHantoCoordinate getN() {
		final AlphaHantoCoordinate coord = 
				new AlphaHantoCoordinate(x, y + 1);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the North east
	 * @return coordinates of the element in the north east
	 */
	public AlphaHantoCoordinate getNE() {
		final AlphaHantoCoordinate coord = 
				new AlphaHantoCoordinate(x + 1, y);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the NorthWest
	 * @return coordinates of the element in the northwest
	 */
	public AlphaHantoCoordinate getNW() {
		final AlphaHantoCoordinate coord = 
				new AlphaHantoCoordinate(x - 1, y + 1);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the south
	 * @return coordinates of the element in the south
	 */
	public AlphaHantoCoordinate getS() {
		final AlphaHantoCoordinate coord = 
				new AlphaHantoCoordinate(x, y - 1);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the south east
	 * @return coordinates of the element in the south east
	 */
	public AlphaHantoCoordinate getSE() {
		final AlphaHantoCoordinate coord = new AlphaHantoCoordinate(x + 1, y - 1);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the southwest
	 * @return coordinates of the element in the southwest
	 */
	public AlphaHantoCoordinate getSW() {
		final AlphaHantoCoordinate coord = 
				new AlphaHantoCoordinate(x - 1, y);
		return coord;
	}
		
	/**
	 * Checks if the coordinates are the origin
	 * @return true if it is origin, false if it's not
	 */
	public boolean isOrigin() {
		return (x == 0) && (y == 0);
	}
	
	@Override
	public boolean equals(final Object other) {
	        final AlphaHantoCoordinate that = (AlphaHantoCoordinate) other;
		return (x == that.getX() && y == that.getY());
	}
	
    @Override public int hashCode() {
        return (41 * (41 + getX()) + getY());
    }

}
