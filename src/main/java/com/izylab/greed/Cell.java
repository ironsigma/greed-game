package com.izylab.greed;

/** Cell. */
class Cell {
	/** Cell State. */
	public enum State { START, EMPTY, VALUE };

	/** State. */
	private State state;

	/** Value. */
	private int value;

	/**
	 * Constructor.
	 * @param s State
	 * @param v Value
	 */
	public Cell(final State s, final int v) {
		state = s;
		value = v;
	}

	/**
	 * Set state.
	 * @param s State
	 */
	public void setState(final State s) {
		state = s;
	}

	/**
	 * Get state.
	 * @return state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Set value.
	 * @param v value
	 */
	public void setValue(final int v) {
		value = v;
	}

	/**
	 * Get value.
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		switch (state) {
		case EMPTY:
			return "_";

		case START:
			return "@";

		default:
			return "" + value;
		}
	}
}