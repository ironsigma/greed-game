package com.izylab.greed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.izylab.greed.Cell.State;

/** Board. */
class Board extends JComponent {
	/** Directions. */
	public enum Direction { UP, DOWN, LEFT, RIGHT }

	/** Logger. */
	private static final Logger LOG = Logger.getLogger(Board.class);

	/** Orange. */
	private static final Color COLOR_ONE = new Color(0xff, 0xc0, 0x00);

	/** Green. */
	private static final Color COLOR_TWO = new Color(0x00, 0x66, 0x00);

	/** Cyan. */
	private static final Color COLOR_THREE = new Color(0x33, 0x99, 0x99);

	/** Purple. */
	private static final Color COLOR_FOUR = new Color(0xcc, 0x66, 0xcc);

	/** Light Blue. */
	private static final Color COLOR_FIVE = new Color(0x33, 0x99, 0xff);

	/** Empty cell array. */
	private static final Cell[] EMPTY_CELL_ARRAY = new Cell[0];

	/** Version. */
	private static final long serialVersionUID = 1L;

	/** Horizontal Spacing. */
	private static final int HSPACING = 20;

	/** Vertical Spacing. */
	private static final int VSPACING = 20;

	/** Cells values. */
	private Cell[] cells;

	/** Board width. */
	private int width;

	/** Board height. */
	private int height;

	/** X Location. */
	private int xLoc;

	/** Y Location. */
	private int yLoc;

	/** Listener. */
	private BoardListener listener;

	/**
	 * Constructor.
	 * @param w Width
	 * @param h Height
	 */
	public Board(final int w, final int h) {
		width = w;
		height = h;
		cells = new Cell[w * h];
	}

	/**
	 * Init the board.
	 * @param max Maximum cell value.
	 */
	public void init(final int max) {
		Random rnd = new Random();
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(State.VALUE, rnd.nextInt(max) + 1);
		}
		xLoc = rnd.nextInt(cells.length);
		cells[xLoc].setState(State.START);
		yLoc = xLoc / width;
		xLoc = xLoc % width;
	}

	/**
	 * Add listener.
	 * @param l Listener
	 */
	public void addListener(final BoardListener l) {
		listener = l;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension((width + 1) * HSPACING, (height + 1) * VSPACING);
	}

	/**
	 * Paint.
	 * @param graphics Graphics
	 */
	public void paint(final Graphics graphics) {
		int y = 0;
		int x = 0;
		Graphics2D g = (Graphics2D) graphics;
		for (int i = 0; i < cells.length; i++) {
			if (i % width == 0) {
				y += VSPACING;
				x = HSPACING;
			} else {
				x += HSPACING;
			}
			switch (cells[i].getState()) {
			case EMPTY:
				continue;

			case START:
				g.setColor(Color.RED);
				g.drawString("@", x, y);
				continue;

			default:
				switch (cells[i].getValue()) {
				case 1: g.setColor(COLOR_ONE); break;
				case 2: g.setColor(COLOR_TWO); break;
				case 3: g.setColor(COLOR_THREE); break;
				case 4: g.setColor(COLOR_FOUR); break;
				case 5: g.setColor(COLOR_FIVE); break;
				default: g.setColor(Color.BLACK); break;
				}
				g.drawString("" + cells[i].getValue(), x, y);
			}
		}
	}

	/**
	 * Set a cell value.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param value Value
	 */
	public void set(final int x, final int y, final int value) {
		cells[width * y + x].setValue(value);
	}

	/**
	 * Set a cell value.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return Cell value.
	 */
	public Cell get(final int x, final int y) {
		return cells[width * y + x];
	}

	/**
	 * Move.
	 * @param d Direction
	 */
	public void move(final Direction d) {
		Cell[] cellRow;
		switch (d) {
		case RIGHT:
			cellRow = getCells(d);
			if (cellRow.length == 0) {
				LOG.debug("Not cells returned.");
				return;
			}
			for (int i = 0; i < cellRow.length - 1; i++) {
				LOG.debug("Cell at " + i + " is: " + cellRow[i] + ", marking empty.");
				cellRow[i].setState(State.EMPTY);
			}
			cells[yLoc * width + xLoc].setState(State.EMPTY);
			cellRow[cellRow.length - 1].setState(State.START);
			xLoc += cellRow.length;
			if (listener != null) {
				listener.moved(cellRow);
			}
			break;

		case LEFT:
			cellRow = getCells(d);
			if (cellRow.length == 0) {
				LOG.debug("Not cells returned.");
				return;
			}
			for (int i = 0; i < cellRow.length - 1; i++) {
				LOG.debug("Cell at " + i + " is: " + cellRow[i] + ", marking empty.");
				cellRow[i].setState(State.EMPTY);
			}
			cells[yLoc * width + xLoc].setState(State.EMPTY);
			cellRow[cellRow.length - 1].setState(State.START);
			xLoc -= cellRow.length;
			if (listener != null) {
				listener.moved(cellRow);
			}
			break;

		case UP:
			cellRow = getCells(d);
			if (cellRow.length == 0) {
				LOG.debug("Not cells returned.");
				return;
			}
			for (int i = 0; i < cellRow.length - 1; i++) {
				LOG.debug("Cell at " + i + " is: " + cellRow[i] + ", marking empty.");
				cellRow[i].setState(State.EMPTY);
			}
			cells[yLoc * width + xLoc].setState(State.EMPTY);
			cellRow[cellRow.length - 1].setState(State.START);
			yLoc -= cellRow.length;
			if (listener != null) {
				listener.moved(cellRow);
			}
			break;

		case DOWN:
			cellRow = getCells(d);
			if (cellRow.length == 0) {
				LOG.debug("Not cells returned.");
				return;
			}
			for (int i = 0; i < cellRow.length - 1; i++) {
				LOG.debug("Cell at " + i + " is: " + cellRow[i] + ", marking empty.");
				cellRow[i].setState(State.EMPTY);
			}
			cells[yLoc * width + xLoc].setState(State.EMPTY);
			cellRow[cellRow.length - 1].setState(State.START);
			yLoc += cellRow.length;
			if (listener != null) {
				listener.moved(cellRow);
			}
			break;

		default:
			LOG.debug("Invalid move");
		}
		repaint();
	}

	private Cell[] getCells(final Direction d) {
		Cell counterCell;
		Cell[] cellRow;
		int startPos;
		int index = 1;
		switch (d) {
		case RIGHT:
			if (xLoc + 1 == width) {
				LOG.debug("bad move, xLoc is all the way to the right.");
				return EMPTY_CELL_ARRAY;
			}
			startPos = yLoc * width + xLoc;
			LOG.debug("Start pos: " + startPos);
			counterCell = cells[startPos + 1];
			if (counterCell.getState() == State.EMPTY) {
				LOG.debug("bad move, right is empty.");
				return EMPTY_CELL_ARRAY;
			}
			LOG.debug("Counter cell is: " + counterCell.toString());
			if (xLoc + counterCell.getValue() >= width) {
				LOG.debug("bad move, xLoc + counter is out of bounds.");
				return EMPTY_CELL_ARRAY;
			}
			cellRow = new Cell[counterCell.getValue()];
			cellRow[0] = counterCell;
			LOG.debug(String.format("loop i = %d; i <= %d", startPos + 2, startPos + counterCell.getValue()));
			for (int i = startPos + 2; i <= startPos + counterCell.getValue(); i++) {
				LOG.debug("Counter cell " + i + " is: " + cells[i]);
				if (cells[i].getState() == State.EMPTY) {
					LOG.debug("bad move, would cross empty cell.");
					return EMPTY_CELL_ARRAY;
				}
				LOG.debug("Storing cell " + i + " to index " + index + " cell: " + cells[i]);
				cellRow[index] = cells[i];
				index++;
			}
			LOG.debug("Good right move");
			return cellRow;

		case LEFT:
			if (xLoc - 1 < 0) {
				LOG.debug("bad move, xLoc is all the way to the left.");
				return EMPTY_CELL_ARRAY;
			}
			startPos = yLoc * width + xLoc;
			LOG.debug("Start pos: " + startPos);
			counterCell = cells[startPos - 1];
			if (counterCell.getState() == State.EMPTY) {
				LOG.debug("bad move, left is empty.");
				return EMPTY_CELL_ARRAY;
			}
			LOG.debug("Counter cell at " + (startPos - 1) + " is: " + counterCell.toString());
			if (xLoc - counterCell.getValue() < 0) {
				LOG.debug("bad move, xLoc - counter is out of bounds.");
				return EMPTY_CELL_ARRAY;
			}
			cellRow = new Cell[counterCell.getValue()];
			cellRow[0] = counterCell;
			LOG.debug(String.format("loop i = %d; i <= %d", startPos - 2, startPos - counterCell.getValue() - 1));
			for (int i = startPos - 2; i > startPos - counterCell.getValue() - 1; i--) {
				LOG.debug("Counter cell " + i + " is: " + cells[i]);
				if (cells[i].getState() == State.EMPTY) {
					LOG.debug("bad move, would cross empty cell.");
					return EMPTY_CELL_ARRAY;
				}
				LOG.debug("Storing cell " + i + " to index " + index + " cell: " + cells[i]);
				cellRow[index] = cells[i];
				index++;
			}
			LOG.debug("Good left move");
			return cellRow;

		case UP:
			if (yLoc - 1 < 0) {
				LOG.debug("bad move, yLoc is all the way to the top.");
				return EMPTY_CELL_ARRAY;
			}
			startPos = yLoc * width + xLoc;
			LOG.debug("Start pos: " + startPos);
			counterCell = cells[startPos - width];
			if (counterCell.getState() == State.EMPTY) {
				LOG.debug("bad move, up is empty.");
				return EMPTY_CELL_ARRAY;
			}
			LOG.debug("Counter cell at " + (startPos - width) + " is: " + counterCell.toString());
			if (yLoc - counterCell.getValue() < 0) {
				LOG.debug("bad move, yLoc - counter is out of bounds.");
				return EMPTY_CELL_ARRAY;
			}
			cellRow = new Cell[counterCell.getValue()];
			cellRow[0] = counterCell;
			LOG.debug(String.format("loop i = %d; i <= %d", startPos - 2 * width,
					startPos - (counterCell.getValue() + 1) * width));
			for (int i = startPos - 2 * width; i > startPos - (counterCell.getValue() + 1) * width; i -= width) {
				LOG.debug("Counter cell " + i + " is: " + cells[i]);
				if (cells[i].getState() == State.EMPTY) {
					LOG.debug("bad move, would cross empty cell.");
					return EMPTY_CELL_ARRAY;
				}
				LOG.debug("Storing cell " + i + " to index " + index + " cell: " + cells[i]);
				cellRow[index] = cells[i];
				index++;
			}
			LOG.debug("Good up move");
			return cellRow;

		case DOWN:
			if (yLoc + 1 == height) {
				LOG.debug("bad move, yLoc is all the way to the bottom.");
				return EMPTY_CELL_ARRAY;
			}
			startPos = yLoc * width + xLoc;
			LOG.debug("Start pos: " + startPos);
			counterCell = cells[startPos + width];
			if (counterCell.getState() == State.EMPTY) {
				LOG.debug("bad move, right is empty.");
				return EMPTY_CELL_ARRAY;
			}
			LOG.debug("Counter cell is: " + counterCell.toString());
			if (yLoc + counterCell.getValue() >= height) {
				LOG.debug("bad move, yLoc + counter is out of bounds.");
				return EMPTY_CELL_ARRAY;
			}
			cellRow = new Cell[counterCell.getValue()];
			cellRow[0] = counterCell;
			LOG.debug(String.format("loop i = %d; i <= %d", startPos + 2 * width,
					startPos + counterCell.getValue() * width));
			for (int i = startPos + 2 * width; i <= startPos + counterCell.getValue() * width; i += width) {
				LOG.debug("Counter cell " + i + " is: " + cells[i]);
				if (cells[i].getState() == State.EMPTY) {
					LOG.debug("bad move, would cross empty cell.");
					return EMPTY_CELL_ARRAY;
				}
				LOG.debug("Storing cell " + i + " to index " + index + " cell: " + cells[i]);
				cellRow[index] = cells[i];
				index++;
			}
			LOG.debug("Good right move");
			return cellRow;

		default:
			LOG.debug("Invalid move");
			return EMPTY_CELL_ARRAY;
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cells.length; i++) {
			if (i % width == 0) {
				sb.append("\n");
			}
			sb.append(' ');
			sb.append(cells[i]);
			sb.append(' ');
		}
		sb.append("\n(");
		sb.append(xLoc);
		sb.append(", ");
		sb.append(yLoc);
		sb.append(")");
		return sb.toString();
	}
}