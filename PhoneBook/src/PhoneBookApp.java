import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class PhoneBookApp {

	private JFrame frame;
	private JTextField addBrand;
	private JTextField addModel;
	private JTextField addYear;
	private JTextField addPrice;
	private JTextField search;
	private DefaultTableModel model = new DefaultTableModel();
	private JTable table = new JTable(model);
	private List<Phone> list = new ArrayList<Phone>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhoneBookApp window = new PhoneBookApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PhoneBookApp() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 752, 418);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		addBrand = new JTextField();
		addBrand.setBounds(192, 31, 124, 37);
		frame.getContentPane().add(addBrand);
		addBrand.setColumns(10);

		addModel = new JTextField();
		addModel.setColumns(10);
		addModel.setBounds(328, 31, 124, 37);
		frame.getContentPane().add(addModel);

		addYear = new JTextField();
		addYear.setColumns(10);
		addYear.setBounds(464, 31, 124, 37);
		frame.getContentPane().add(addYear);

		addPrice = new JTextField();
		addPrice.setColumns(10);
		addPrice.setBounds(600, 31, 124, 37);
		frame.getContentPane().add(addPrice);

		search = new JTextField();
		search.setColumns(10);
		search.setBounds(192, 80, 124, 37);
		frame.getContentPane().add(search);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(328, 80, 260, 37);
		comboBox.addItem("");
		comboBox.addItem("by Brand");
		comboBox.addItem("by Model");
		comboBox.addItem("by Year");
		comboBox.addItem("by Price");
		frame.getContentPane().add(comboBox);

		model.addColumn("brand");
		model.addColumn("model");
		model.addColumn("year");
		model.addColumn("price");

		table.setBounds(10, 128, 714, 252);
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(10, 128, 714, 252);
		frame.getContentPane().add(pane);

		JButton addBtn = new JButton("Add a phone");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (addBrand.getText().isEmpty() != true && addModel.getText().isEmpty() != true
							&& addYear.getText().isEmpty() != true && addPrice.getText().isEmpty() != true) {

						int rowCount = model.getRowCount();
						// Remove rows one by one from the end of the table
						for (int i = rowCount - 1; i >= 0; i--) {
							model.removeRow(i);
						}

						Phone phone = new Phone(addBrand.getText(), addModel.getText(),
								Integer.parseInt(addYear.getText()), Double.parseDouble(addPrice.getText()));
						PhoneDataAccessObject dao = new PhoneDataAccessObject();
						dao.addData(phone);

						dao.getData();
						list = dao.getList();
						model.addRow(
								new Object[] { phone.getBrand(), phone.getModel(), phone.getYear(), phone.getPrice() });

					} else {
						JOptionPane.showMessageDialog(null, "You have left an empty textfield!");
					}

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "You have enter a wrong format for year or price!");
				}

			}
		});
		addBtn.setBounds(28, 31, 140, 37);
		frame.getContentPane().add(addBtn);

		JButton srchBtn = new JButton("Search");
		srchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {

					int rowCount = model.getRowCount();
					// Remove rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
					PhoneDataAccessObject dao = new PhoneDataAccessObject();
					dao.getData();
					list = dao.getList();

					if (comboBox.getSelectedItem().equals("")) {
						JOptionPane.showMessageDialog(null, "Please choose an option to select from this menu!");
					}

					else if (comboBox.getSelectedItem().equals("by Brand")) {
						for (Phone phone : list) {
							if (phone.getBrand().equals(search.getText())) {
								model.addRow(new Object[] { phone.getBrand(), phone.getModel(), phone.getYear(),
										phone.getPrice() });
							} 
						}
					}

					else if (comboBox.getSelectedItem().equals("by Model")) {
						for (Phone phone : list) {
							if (phone.getModel().equals(search.getText())) {
								model.addRow(new Object[] { phone.getBrand(), phone.getModel(), phone.getYear(),
										phone.getPrice() });
							} 
						}
					}

					else if (comboBox.getSelectedItem().equals("by Year")) {
						for (Phone phone : list) {
							if (phone.getYear() == Integer.parseInt(search.getText())) {
								model.addRow(new Object[] { phone.getBrand(), phone.getModel(), phone.getYear(),
										phone.getPrice() });
							} 
						}
					}

					else if (comboBox.getSelectedItem().equals("by Price")) {
						for (Phone phone : list) {
							if (phone.getPrice() == Double.parseDouble(search.getText())) {
								model.addRow(new Object[] { phone.getBrand(), phone.getModel(), phone.getYear(),
										phone.getPrice() });
							} 
						}
					}
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "You have enter a wrong format for year or price!");
				}
			}
		});
		srchBtn.setBounds(28, 80, 140, 37);
		frame.getContentPane().add(srchBtn);

		JButton shwBtn = new JButton("Show All");
		shwBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rowCount = model.getRowCount();
				// Remove rows one by one from the end of the table
				for (int i = rowCount - 1; i >= 0; i--) {
					model.removeRow(i);
				}
				PhoneDataAccessObject dao = new PhoneDataAccessObject();
				dao.getData();
				list = dao.getList();
				for (Phone phone : list) {
					model.addRow(
							new Object[] { phone.getBrand(), phone.getModel(), phone.getYear(), phone.getPrice() });
				}
			}
		});
		shwBtn.setBackground(new Color(250, 128, 114));
		shwBtn.setBounds(600, 80, 124, 37);
		frame.getContentPane().add(shwBtn);

	}
}
