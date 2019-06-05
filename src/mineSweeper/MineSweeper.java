package mineSweeper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

enum Stage {
	EASY, NORMAL, HARD, CUSTOM, RESTART;
}

public class MineSweeper extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1022203880024927244L;

	private final int size = 30;
	
	public static long time;
	
	private MineField mineField;
	
	private JPanel pMineField;
	
	private JMenuBar jmb = new JMenuBar();
	private JMenu mStage = new JMenu("난이도");
	private JMenuItem iEasy = new JMenuItem("EASY");
	private JMenuItem iNormal = new JMenuItem("NORMAL");
	private JMenuItem iHard = new JMenuItem("HARD");
	private JMenuItem iCustom = new JMenuItem("사용자 설정");
	private JMenuItem iRestart = new JMenuItem("다시 한번");

	MineSweeper(String title) {
		super(title);
		
		iEasy.addActionListener(this);
		iEasy.setActionCommand(Stage.EASY.name());
		iNormal.addActionListener(this);
		iNormal.setActionCommand(Stage.NORMAL.name());
		iHard.addActionListener(this);
		iHard.setActionCommand(Stage.HARD.name());
		iCustom.addActionListener(this);
		iCustom.setActionCommand(Stage.CUSTOM.name());
		iRestart.addActionListener(this);
		iRestart.setActionCommand(Stage.RESTART.name());
		
		mStage.add(iEasy);
		mStage.add(iNormal);
		mStage.add(iHard);
		mStage.add(iCustom);
		
		jmb.add(mStage);
		jmb.add(iRestart);
		
		setJMenuBar(jmb);
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(100, 100);
		setMineSweeper(Stage.EASY);
	}
	
	public static void main(String[] args) {
		new MineSweeper("지뢰 찾기");
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		setMineSweeper(Stage.valueOf(ae.getActionCommand()));
	}
	
	private void setMineSweeper(Stage stage) {
		switch(stage) {
		case EASY : setMineSweeper(8, 8, 10); break;
		case NORMAL : setMineSweeper(20, 20, 70); break;
		case HARD : setMineSweeper(45, 20, 160); break;
		case CUSTOM : setMineSweeper(new CustomOptionDialog(this, "사용자 설정").getMineField()); break;
		case RESTART : setMineSweeper(mineField); break;
		}
	}
	
	private void setMineSweeper(MineField mineField) {
		if(mineField == null) return;
		setMineSweeper(mineField.getWidth(), mineField.getHeight(), mineField.getNumberOfMine());
	}
	
	private void setMineSweeper(int width, int height, int numberOfMine) {
		mineField = new MineField(width, height, numberOfMine);
		setMineSweeper();
	}
	
	private void setMineSweeper() {
		if(pMineField != null)
			remove(pMineField);
		
		pMineField = new JPanel(new GridLayout(mineField.getHeight(), mineField.getWidth()));
		
		for(int i=0; i<mineField.getHeight(); i++) 
			for(int j=0; j<mineField.getWidth(); j++) 
				pMineField.add(mineField.getButton(i, j));
		
		add(pMineField);
		
		setVisible(true);
		setSize(mineField.getWidth()*size+getInsets().left+getInsets().right ,
				mineField.getHeight()*size+getInsets().top+getInsets().bottom+
				jmb.getHeight());
		
		time = System.currentTimeMillis()/1000;
	}
}
