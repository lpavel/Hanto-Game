/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel;

import java.util.List;
import java.util.Vector;

import hanto.common.HantoCoordinate;

/** BetaHantoClass that implements the interface HantoCoordinate
 */
public class BaseHantoCoordinate implements HantoCoordinate {

	int x;
	int y;
	
	/**
	 * Constructor for BetaHantoCoordinate.
	 * @param x int
	 * @param y int
	 */
	public BaseHantoCoordinate(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	
	/**
	 * Constructor for BetaHantoCoordinate.
	 * @param hantoCoordinate HantoCoordinate
	 */
	public BaseHantoCoordinate(final HantoCoordinate hantoCoordinate) {
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
	public BaseHantoCoordinate getN() {
		final BaseHantoCoordinate coord = 
				new BaseHantoCoordinate(x, y + 1);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the North east
	 * @return coordinates of the element in the north east
	 */
	public BaseHantoCoordinate getNE() {
		final BaseHantoCoordinate coord = 
				new BaseHantoCoordinate(x + 1, y);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the NorthWest
	 * @return coordinates of the element in the northwest
	 */
	public BaseHantoCoordinate getNW() {
		final BaseHantoCoordinate coord = 
				new BaseHantoCoordinate(x - 1, y + 1);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the south
	 * @return coordinates of the element in the south
	 */
	public BaseHantoCoordinate getS() {
		final BaseHantoCoordinate coord = 
				new BaseHantoCoordinate(x, y - 1);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the south east
	 * @return coordinates of the element in the south east
	 */
	public BaseHantoCoordinate getSE() {
		final BaseHantoCoordinate coord = 
				new BaseHantoCoordinate(x + 1, y - 1);
		return coord;
	}
	
	/**
	 * Getter for the coordinate in the southwest
	 * @return coordinates of the element in the southwest
	 */
	public BaseHantoCoordinate getSW() {
		final BaseHantoCoordinate coord = new BaseHantoCoordinate(x - 1, y);
		return coord;
	}
		
	/**
	 * Getter for all the coordinates around the point
	 * @return coordinates of the element in the southwest
	 */
	public List<BaseHantoCoordinate> getAdjacent() {
		List<BaseHantoCoordinate> adj = new Vector<BaseHantoCoordinate>();
		adj.add(getN());
		adj.add(getNE());
		adj.add(getNW());
		adj.add(getS());
		adj.add(getSE());
		adj.add(getSW());
		return adj;
	}
	
	/**
	 * Checks if the coordinates are the origin
	 * @return true if it is origin, false if it's not
	 */
	public boolean isOrigin() {
		return (x == 0) && (y == 0);
	}
	
	@Override
	public boolean equals(Object other) {
		final BaseHantoCoordinate that = (BaseHantoCoordinate) other;
	    return (this.getX() == that.getX() && this.getY() == that.getY());
	}
	
    @Override public int hashCode() {
        return (41 * (41 + getX()) + getY());
    }
}
