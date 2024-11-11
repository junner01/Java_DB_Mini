package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_Class extends JFrame {
	private JTextArea resultArea;
	private JButton insertButton;
	private JButton selectButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JButton clearButton;

	public Main_Class() {
		setTitle("Database Control Panel");
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Initialize UI components
		JPanel buttonPanel = new JPanel(new FlowLayout());
		insertButton = new JButton("강준영");
		selectButton = new JButton("조이한");
		updateButton = new JButton("김진영");
		deleteButton = new JButton("이상현");
		clearButton = new JButton("조수빈");

		buttonPanel.add(insertButton);
		buttonPanel.add(selectButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(clearButton);
		add(buttonPanel, BorderLayout.NORTH);

		resultArea = new JTextArea(10, 50);
		resultArea.setEditable(false);
		add(new JScrollPane(resultArea), BorderLayout.CENTER);

		// Button actions
		insertButton.addActionListener(e -> {});
		selectButton.addActionListener(e -> {});
		updateButton.addActionListener(e -> {});
		deleteButton.addActionListener(e -> {});
		clearButton.addActionListener(e -> {});

		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main_Class::new);
	}
}