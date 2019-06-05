package mineSweeper;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

enum Mark {
	FLAG("¢Ó"), MINE("¡Ø");
	
	private String mark;
	
	private Mark(String mark) {
		this.mark = mark;
	}
	
	public String getMark() {
		return mark;
	}
}

enum BlockColor {
	CLOSED(Color.LIGHT_GRAY),
	OPENED(Color.GRAY),
	MINE(Color.RED);
	
	private Color color;
	
	private BlockColor(Color c) {
		this.color = c;
	}
	
	public Color getColor() {
		return color;
	}
}

public class MineButton {
	
	public static final int MINE = -1;
	
	private static final Color[] NUMBER_COLOR = {
			new Color(0, 0, 255),
			new Color(0, 255, 0),
			new Color(255, 0, 0),
			new Color(255, 255, 0),
			new Color(255, 0, 255),
			new Color(0, 255, 255),
			new Color(128, 128, 255),
			new Color(128, 255, 125)
	};
	
	private JButton btn;
	private int value;
	private boolean isFlag;
	private boolean isOpen;
	
	public MineButton(int value) {
		this.btn = new JButton();
		this.value = value;
		this.isFlag = false;
		this.isOpen = false;
		
		btn.setBackground(BlockColor.CLOSED.getColor());
		btn.setBorder(LineBorder.createBlackLineBorder());
	}
	
	MineButton() {
		this(0);
	}

	public JButton getBtn() {
		return btn;
	}

	public int getValue() {
		return value;
	}

	public void setMine() {
		this.value = MINE;
	}

	public boolean isFlag() {
		return isFlag;
	}

	public boolean isOpen() {
		return isOpen;
	}
	
	public void turnFlag() {
		if(!isOpen()) {
			isFlag = !isFlag;
			update();
		}
	}
	
	public void open() {
		isOpen = true;
		isFlag = false;
		update();
	}
	
	public boolean isMine() {
		return value == MINE;
	}
	
	public void plusVal() {
		value++;
	}
	
	public void update() {
		btn.setText(face());
		if(!isOpen()) return;
		
		if(!isMine()) {
			btn.setBackground(BlockColor.OPENED.getColor());
			btn.setForeground(value >= 1 ? NUMBER_COLOR[value-1] : NUMBER_COLOR[0]);
		} else {
			btn.setBackground(BlockColor.MINE.getColor());
		}
	}
	
	public String face() {
		if(!isOpen()) {
			return isFlag() ? Mark.FLAG.getMark() : "";
		} else {
			return isMine() ? Mark.MINE.getMark() : value>0 ? value+"" : "";
		}
	}
}
