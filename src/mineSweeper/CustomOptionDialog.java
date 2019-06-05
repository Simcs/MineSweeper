package mineSweeper;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class CustomOptionDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 2438637452181668927L;

	private JPanel pCustom = new JPanel(new GridLayout(3, 3));
	
	private JLabel lblWidth = new JLabel("�� �� : ", JLabel.CENTER);
	private JLabel lblHeight = new JLabel("�� �� : ", JLabel.CENTER);
	private JLabel lblNumberOfMine = new JLabel("���ڰ��� : ", JLabel.CENTER);
	private JTextField txtWidth = new JTextField();
	private JTextField txtHeight = new JTextField();
	private JTextField txtNumberOfMine = new JTextField();
	
	private JButton ok = new JButton("Ȯ��");
	
	private MineField mineField;
	
	
	public CustomOptionDialog(Frame owner, String title) {
		super(owner, title, true);
		
		pCustom.add(lblWidth);
		pCustom.add(txtWidth);
		pCustom.add(lblHeight);
		pCustom.add(txtHeight);
		pCustom.add(lblNumberOfMine);
		pCustom.add(txtNumberOfMine);
		
		add(pCustom, BorderLayout.CENTER);
		add(ok, BorderLayout.SOUTH);
		
		ok.addActionListener(this);
		txtWidth.addActionListener(this);
		txtHeight.addActionListener(this);
		txtNumberOfMine.addActionListener(this);
		
		setLocation(owner.getLocation());
		setSize(150, 150);
		setVisible(true);
	}
	
	public MineField getMineField() {
		return mineField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int width = Integer.valueOf(txtWidth.getText());
			int height = Integer.valueOf(txtHeight.getText());
			int numberOfMine = Integer.valueOf(txtNumberOfMine.getText());
			
			if(width*height > 60*60 || width*height < numberOfMine || width<=0 || height<=0 || numberOfMine <= 0 )
				throw new IllegalArgumentException();
			
			mineField = new MineField(width, height, numberOfMine);
			setVisible(false);
		} catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "���ڸ� �Է����ּ��� !");
		} catch(IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(this, "�ùٸ� ���� �Է����ּ��� !");
		}
	}
}
