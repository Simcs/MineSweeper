package mineSweeper;

import java.awt.event.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;

enum Click {
	LEFT, WHEEL, RIGHT;
	
	private static final Map<Integer, Click> clickByCode =
			new HashMap<Integer, Click>();
	static {
		clickByCode.put(MouseEvent.BUTTON1_DOWN_MASK, LEFT);
		clickByCode.put(MouseEvent.BUTTON2_DOWN_MASK, WHEEL);
		clickByCode.put(MouseEvent.BUTTON3_DOWN_MASK, RIGHT);
	}
	
	public static Click getClickByID(int id) {
		return clickByCode.get(id);
	}
}

public class MineField {
	
	private int width, height, numberOfMine;
	private MineButton[][] mPane;
	
	private MouseHandler mouseHandler = new MouseHandler();
	
	public MineField(int width, int height, int numberOfMine) {
		this.width = width;
		this.height = height;
		this.numberOfMine = numberOfMine;
		
		mPane = new MineButton[height][width];
		for(int i=0; i<mPane.length; i++) {
			for(int j=0; j<mPane[0].length; j++) {
				mPane[i][j] = new MineButton();
				mPane[i][j].getBtn().addMouseListener(mouseHandler);
			}
		}
		
		setMine();
		plusValueAroundMine();
	}
	
	public JButton getButton(int i, int j) {
		if(!isValid(i, j))
			throw new IllegalArgumentException();
		return mPane[i][j].getBtn();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getNumberOfMine() {
		return numberOfMine;
	}
	
	class MouseHandler extends MouseAdapter {
		 @Override
		public void mousePressed(MouseEvent me) {
			 JButton tmp = (JButton)me.getSource();
			 int x = tmp.getY()/tmp.getHeight();
			 int y = tmp.getX()/tmp.getWidth();
			 
			 switch(Click.getClickByID(me.getModifiersEx())) {
			 case LEFT : open(x, y); break;
			 case RIGHT : mPane[x][y].turnFlag(); break;
			 case WHEEL : wheelClick(x, y); break;
			 }
			 
			 if(isWin())
				 gameOver(true);
		 }
	}
	
	private void setMine() {
		int[] tmpArr = new int[width*height];
		for(int i=0; i<tmpArr.length; i++)
				tmpArr[i] = i;
		for(int i=0; i<tmpArr.length*2; i++)
			swap(tmpArr, 0, (int)(Math.random()*tmpArr.length));
		
		for(int i=0; i<numberOfMine; i++)
			mPane[tmpArr[i]/width][tmpArr[i]%width].setMine();
	}
	
	private void plusValueAroundMine() {
		for(int i=0; i<mPane.length; i++)
			for(int j=0; j<mPane[0].length; j++)
				if(mPane[i][j].isMine())
					for(int k=i-1; k<=i+1; k++)
						for(int l=j-1; l<=j+1; l++)
							if(isValid(k, l) && !mPane[k][l].isMine())
								mPane[k][l].plusVal();
	}
	
	public void wheelClick(int x, int y) {
		if(!isValid(x, y) || !mPane[x][y].isOpen())
			return;
		
		int count = 0;
		for(int i=x-1; i<=x+1; i++)
			for(int j=y-1; j<=y+1; j++)
				if(isValid(i, j) && mPane[i][j].isFlag())
					count++;
		
		if(count == mPane[x][y].getValue()) {
			for(int i=x-1; i<=x+1; i++)
				for(int j=y-1; j<=y+1; j++)
					if(isValid(i, j) && !mPane[i][j].isFlag())
						open(i, j);
		} else if(count >= mPane[x][y].getValue()) {
			gameOver(false);
		}
	}
	
	public void open(int x, int y) {
		if(!isValid(x, y) || mPane[x][y].isOpen())
			return;
		
		mPane[x][y].open();
		if(mPane[x][y].isMine()) {
			gameOver(false);
		} else if(mPane[x][y].getValue()==0) {
			for(int i=x-1; i<=x+1; i++)
				for(int j=y-1; j<=y+1; j++)
					open(i, j);
		}
	}
	
	public boolean isWin() {
		for(int i=0; i<mPane.length; i++)
			for(int j=0; j<mPane[0].length; j++)
				if(!mPane[i][j].isOpen() && !mPane[i][j].isMine())
					return false;
		return true;
	}
	
	public void gameOver(boolean isGameWin) {
		long time = (System.currentTimeMillis()/1000) - MineSweeper.time;
		for(int i=0; i<mPane.length; i++)
			for(int j=0; j<mPane[i].length; j++)
				mPane[i][j].getBtn().removeMouseListener(mouseHandler);
		
		if(isGameWin) {
			flagAll();
			JOptionPane.showMessageDialog(null, "승리!\n걸린시간 : "+time+"초");
		} else {
			showAllMine();
			JOptionPane.showMessageDialog(null, "패배!\n걸린시간 : "+time+"초");
		}
	}
	
	public void flagAll() {
		for(int i=0; i<mPane.length; i++)
			for(int j=0; j<mPane[i].length; j++)
				if(mPane[i][j].isMine() && !mPane[i][j].isFlag())
					mPane[i][j].turnFlag();
	}
	
	public void showAllMine() {
		for(int i=0; i<mPane.length; i++)
			for(int j=0; j<mPane[i].length; j++)
				if(mPane[i][j].isMine())
					mPane[i][j].open();
	}
	
	public boolean isValid(int i, int j) {
		return !(i<0 || i>=height || j<0 || j>=width);
	}
	
	private static boolean swap(int[] arr, int i, int j) {
		if(i<0 || i>=arr.length || j<0 || j>arr.length)
			return false;
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
		return true;
	}
}