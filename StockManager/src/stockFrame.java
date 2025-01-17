import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

public class stockFrame extends JFrame{
	Connection conn=null;
	PreparedStatement state=null;
	ResultSet result=null;
	// помощни променливи за проследяване на id-тата на отделните номенкалтури
	int id=-1;
	int idpr=-1;
	int idsup=-1;
	int idspr=-1;

	//общ таб панел
	JTabbedPane tab=new JTabbedPane();

	//ТАБЛИЦА ПОРЪЧКИ
	//Основен панел за поръчки
	JPanel stockPanel=new JPanel();
	
	//подпанели за поръчки
	JPanel stockPanel1=new JPanel();
	JPanel stockPanel2=new JPanel();
	JPanel stockPanel3=new JPanel();
		
	//етикети
	JLabel orderIDL=new JLabel("Поръчка:");
	JLabel productsL=new JLabel("Продукт:");
	JLabel supplierL=new JLabel("Доставчик:");
	JLabel quantityL=new JLabel("Количество:");
	JLabel statusL=new JLabel("Статус:");
	JLabel orderDateL=new JLabel("Дата:");
	
	private static final long serialVersionUID = 4L;
	
	//текстови полета за въвеждане
	JTextField orderIDTF=new JTextField();
	JTextField productsTF=new JTextField();
	JTextField supplierTF=new JTextField();
	JTextField quantityTF=new JTextField();
	JTextField statusTF=new JTextField();
	JTextField orderDateTF=new JTextField();
	
	//комбобокс за номенклатурите
	JComboBox<String> comboOrd=new JComboBox<String>();
	JComboBox<String> comboPr=new JComboBox<String>();
	JComboBox<String> comboS=new JComboBox<String>();
	
	//бутони за панела поръчки
	JButton BAdd=new JButton("Добави");
	JButton BDel=new JButton("Изтрий");
	JButton BEdit=new JButton("Промени");
	
	//таблица+скроол за визуализация
	JTable orderTable=new JTable();
	JScrollPane orderScroll=new JScrollPane(orderTable);
	
	//ТАБЛИЦА ПРОДУКТИ
		//етикет
		JLabel productIDL=new JLabel("Продукти:");
		JLabel nameL=new JLabel("Име:");
		JLabel descriptionL=new JLabel("Описание:");
		JLabel priceL=new JLabel("Цена:");
		JLabel prquantityL=new JLabel("Количество:");

		//текстово поле за въвеждане
		JTextField productIDTF=new JTextField();
		JTextField nameTF =new JTextField();
		JTextField descriptionTF=new JTextField();
		JTextField priceTF=new JTextField();
		JTextField prquantityTF=new JTextField();

		//основен панел
		JPanel prPanel=new JPanel();
		//подпанели
		JPanel prPanel1=new JPanel();
		JPanel prPanel2=new JPanel();
		JPanel prPanel3=new JPanel();

		//бутони за продукти
		JButton PrAdd=new JButton("Добави");
		JButton PrDel=new JButton("Изтрий");
		JButton PrEdit=new JButton("Промени");
		
		//таблица+скроол за продукти
		JTable prTable=new JTable();
		JScrollPane prScroll=new JScrollPane(prTable);

	//ТАБЛИЦА ДОСТАВЧИК
	//етикет
	JLabel supplierIDL=new JLabel("Доставчик:");
	JLabel supNameL=new JLabel("Име:");
	JLabel contactInfoL=new JLabel("Контакти:");
	
	
	//текстови полета за въвеждане
	JTextField supplierIDTF =new JTextField();
	JTextField supNameTF=new JTextField();
	JTextField contactInfoTF=new JTextField();
	
	//основен панел за доставчик
	JPanel supPanel=new JPanel();
	//подпанели
	JPanel supPanel1=new JPanel();
	JPanel supPanel2=new JPanel();
	JPanel supPanel3=new JPanel();

	//бутони за панела поръчки
	JButton supAdd=new JButton("Добави");
	JButton supDel=new JButton("Изтрий");
	JButton supEdit=new JButton("Промени");
	
	//таблица+скроол за доставчик
	JTable supTable=new JTable();
	JScrollPane supScroll=new JScrollPane(supTable);
	
	//таб СПРАВКА: по дата на поръчка, по цена на продукт, по име на доставчик
		//етикет за дата
		JLabel sprOrdDateL=new JLabel("Въведете дата на поръчката:");
		//текстово поле за въвеждане
		JTextField sprOrdDateTF=new JTextField();
		//етикет за цена
		JLabel sprPriceL=new JLabel("Въведете цена на продукт:");
		//текстово поле за въвеждане
		JTextField sprPriceTF=new JTextField();
		//етикет за име
		JLabel sprNameL=new JLabel("Въведете име на доставчик:");
		//текстово поле за въвеждане
		JTextField sprNameTF=new JTextField();

		//основен панел
		JPanel sprPanel=new JPanel();
		//подпанели
		JPanel sprPanel1=new JPanel();
		JPanel sprPanel2=new JPanel();
		JPanel sprPanel3=new JPanel();

		//бутони за поръчка
		JButton sOrdSearch=new JButton("Търси");
		//JButton sPrDel=new JButton("Изтрий");
		//JButton sPrEdit=new JButton("Промени");
		//бутони за поръчка
		JButton sPrSearch=new JButton("Търси");
		//JButton sPrDel=new JButton("Изтрий");
		//JButton sPrEdit=new JButton("Промени");
		//бутони за поръчка
		JButton sSupSearch=new JButton("Търси");
		//JButton sPrDel=new JButton("Изтрий");
		//JButton sPrEdit=new JButton("Промени");
				
		//таблица+скроол за за справката
		JTable sprTable=new JTable();
		JScrollPane sprScroll=new JScrollPane(sprTable);


public stockFrame() {
		
		conn=MyDBConnection.getConnection();

		this.setSize(600, 720);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Складова система");
		
		// ТАБЛИЦА ПОРЪЧКИ
		tab.add(stockPanel,"Поръчки");
		stockPanel.setLayout(new GridLayout(3,1));
		
		//първи подпанел
		stockPanel.add(stockPanel1);

		stockPanel1.setLayout(new GridLayout(7,2));
		stockPanel1.add(orderIDL);stockPanel1.add(orderIDTF);
		stockPanel1.add(productsL);stockPanel1.add(comboPr);
		stockPanel1.add(supplierL);stockPanel1.add(comboS);
		stockPanel1.add(quantityL);stockPanel1.add(quantityTF);
		stockPanel1.add(statusL);stockPanel1.add(statusTF);
		stockPanel1.add(orderDateL);stockPanel1.add(orderDateTF);

		//втори подпанел за бутоните
		stockPanel.add(stockPanel2);
		stockPanel2.add(BAdd);stockPanel2.add(BDel);stockPanel2.add(BEdit);
		
		// БУТОНИ 
		BAdd.addActionListener(new AddStockDB());
		BDel.addActionListener(new DelStockDB());
		BEdit.addActionListener(new EditStockDB());
		
		stockPanel.add(stockPanel3);
		orderScroll.setPreferredSize(new Dimension(550, 200));
		stockPanel3.add(orderScroll);
		
	
		//ТАБЛИЦА ПРОДУКТИ
		tab.add(prPanel,"Продукти");
		prPanel.setLayout(new GridLayout(3,1));
		prPanel.add(prPanel1);

		prPanel1.setLayout(new GridLayout(7,2));
		prPanel1.add(productIDL);prPanel1.add(productIDTF);
		prPanel1.add(nameL);prPanel1.add(nameTF);
		prPanel1.add(descriptionL);prPanel1.add(descriptionTF);
		prPanel1.add(priceL);prPanel1.add(priceTF);
		prPanel1.add(prquantityL);prPanel1.add(prquantityTF);
		productIDTF.setPreferredSize(new Dimension(450,30));

		prPanel.add(prPanel2);
		prPanel2.add(PrAdd);prPanel2.add(PrDel);prPanel2.add(PrEdit);
		
		// БУТОНИ 
		PrAdd.addActionListener(new AddPrDB());
		PrDel.addActionListener(new DelPrDB());
		PrEdit.addActionListener(new EditPrDB());
		
		prPanel.add(prPanel3);
		prScroll.setPreferredSize(new Dimension(550, 200));
		prPanel3.add(prScroll);
		
		//ТАБЛИЦА ДОСТАВЧИК
		tab.add(supPanel,"Доставчик");
		supPanel.setLayout(new GridLayout(3,1));
		supPanel.add(supPanel1);

		supPanel1.setLayout(new GridLayout(7,2));
		supPanel1.add(supplierIDL);supPanel1.add(supplierIDTF);
		supPanel1.add(supNameL);supPanel1.add(supNameTF);
		supPanel1.add(contactInfoL);supPanel1.add(contactInfoTF);
		supplierIDTF.setPreferredSize(new Dimension(450,30));

		supPanel.add(supPanel2);
		supPanel2.add(supAdd);supPanel2.add(supDel);supPanel2.add(supEdit);
		
		// БУТОНИ 
		supAdd.addActionListener(new AddSupDB());
		supDel.addActionListener(new DelSupDB());
		supEdit.addActionListener(new EditSupDB());
		
		supPanel.add(supPanel3);
		supScroll.setPreferredSize(new Dimension(550, 200));
		supPanel3.add(supScroll);
		
		//ТАБЛИЦА СПРАВКА
		tab.add(sprPanel,"Справка");
		sprPanel.setLayout(new GridLayout(3,1));
		sprPanel.add(sprPanel1);

		sprPanel1.add(sprOrdDateL);
		sprPanel1.add(sprOrdDateTF);
		sprOrdDateTF.setPreferredSize(new Dimension(450,30));
		
		sprPanel1.add(sprPriceL);
		sprPanel1.add(sprPriceTF);
		sprPriceTF.setPreferredSize(new Dimension(450,30));
		
		sprPanel1.add(sprNameL);
		sprPanel1.add(sprNameTF);
		sprNameTF.setPreferredSize(new Dimension(450,30));

		sprPanel.add(sprPanel2);
		sprPanel2.add(sPrSearch);
				
		// БУТОНИ 
		sPrSearch.addActionListener(new SearchDB());
				
		sprPanel.add(sprPanel3);
		sprScroll.setPreferredSize(new Dimension(550, 200));
		sprPanel3.add(sprScroll);
		


		this.add(tab);
		this.setVisible(true);
		this.refreshOrdTable(); // вариант без параметри
		this.refreshComboOrd();
		this.refreshComboPr();
		this.ComboS();
		this.refreshPrTable("PRODUCTS", prTable); // вариант с параметри
		this.refreshSTable("SUPPLIERS", supTable); // вариант с параметри
		
		//обработка на мишката в таблиците за визуализация
		orderTable.addMouseListener(new MouseActionOrdTable());
		prTable.addMouseListener(new MouseActionPrTable());
		supTable.addMouseListener(new MouseActionSupTable());
		
comboOrd.addActionListener(new ActionListener() {
			
			//събитието обслужва комбобокса за поръчки само ако е отворен първия таб
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(idpr);
				if(tab.getSelectedIndex()==0 && id>0) {
					//if(idpr>0 || !comboPr.getSelectedItem().toString().isEmpty()) {
					String str="select * from ORDERS where order_id='"+comboOrd.getSelectedItem().toString()+"'";
					try {
						state=conn.prepareStatement(str);
						result=state.executeQuery();
						result.next();
						id=Integer.parseInt(result.getObject(1).toString());
					} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}

					System.out.println(comboOrd.getSelectedItem().toString());
					System.out.println(str);
					System.out.println(id);
				}
			//	} 
			}
		});
		
		comboPr.addActionListener(new ActionListener() {
			
			//събитието обслужва комбобокса за продукти само ако е отворен първия таб
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(idpr);
				if(tab.getSelectedIndex()==0 && idpr>0) {
					//if(idpr>0 || !comboPr.getSelectedItem().toString().isEmpty()) {
					String str="select * from PRODUCTS where product_id='"+comboPr.getSelectedItem().toString()+"'";
					try {
						state=conn.prepareStatement(str);
						result=state.executeQuery();
						result.next();
						idpr=Integer.parseInt(result.getObject(1).toString());
					} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}

					System.out.println(comboPr.getSelectedItem().toString());
					System.out.println(str);
					System.out.println(idpr);
				}
			//	} 
			}
		});

		comboS.addActionListener(new ActionListener() {
			
			//събитието обслужва комбобокса за доставчик само ако е отворен първия таб
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(tab.getSelectedIndex()==0 && idsup>0) {
					
					String str="select * from SUPPLIERS where supplier_id='"+comboS.getSelectedItem().toString()+"'";
					try {
						state=conn.prepareStatement(str);
						result=state.executeQuery();
						result.next();
						idsup=Integer.parseInt(result.getObject(1).toString());
					} catch (SQLException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					System.out.println(comboS.getSelectedItem().toString());
					System.out.println(str);
					System.out.println(idsup);
				}

			}
		});

	}
//актуализира изгледа с таблицата за налични поръчки в съответния таб
public void refreshOrdTable() {
	//conn=MyDBConnection.getConnection();
	String str="";
	str="SELECT DISTINCT order_id, p.name, s.name, ORDERS.quantity, status, order_date, \r\n"
			+ "         FROM ORDERS\r\n"
			+ "join products p on p.product_id=orders.product_id\r\n"
			+ "join suppliers s on s.supplier_id=orders.supplier_id";
	try {
		state=conn.prepareStatement(str);
		result=state.executeQuery();
		orderTable.setModel(new MyTModel(result));

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

	// актуализира изгледа с таблицата за налични продукти в съответния таб
	public void refreshPrTable() {
		//conn=MyDBConnection.getConnection();
		String str="";
		str="select product_id, name, description, price, quantity from PRODUCTS";
		try { 
			state=conn.prepareStatement(str);
			result=state.executeQuery();
			prTable.setModel(new MyTModel(result));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// актуализира изгледа с таблицата за налични доставчици в съответния таб
		public void refreshSupTable() {
			//conn=MyDBConnection.getConnection();
			String str="";
			str="select * from SUPPLIERS";
			try { 
				state=conn.prepareStatement(str);
				result=state.executeQuery();
				supTable.setModel(new MyTModel(result));
	

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	
	// актуализира изгледа с таблицата за поръчки в съответния таб
		public void refreshOrdTable(String name, JTable table) {
			//conn=MyDBConnection.getConnection();
			String str="SELECT * from ORDERS";
			try { 
				state=conn.prepareStatement(str);
				result=state.executeQuery();
				table.setModel(new MyTModel(result));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	// актуализира изгледа с таблицата за продукти в съответния таб
	public void refreshPrTable(String name, JTable table) {
		//conn=MyDBConnection.getConnection();
		String str="select * from PRODUCTS ";
		try {
			state=conn.prepareStatement(str);
			result=state.executeQuery();
			table.setModel(new MyTModel(result));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// актуализира изгледа с таблицата за доставчик в съответния таб
	public void refreshSTable(String name, JTable table) {
		//conn=MyDBConnection.getConnection();
		String str="select * from SUPPLIERS";
		try {
			state=conn.prepareStatement(str);
			result=state.executeQuery();
			table.setModel(new MyTModel(result));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// презарежда комбо кутията от таблицата за поръчки
		public void refreshComboOrd() {

			comboOrd.removeAllItems();
			
			String sql="select order_id, product_id, supplier_id, quantity, status, order_date from ORDERS";
			//conn=DBConnection.getConnection();
			String item="";
			
			try {
	            state=conn.prepareStatement(sql);
	            result=state.executeQuery();
	            while(result.next()) {
	                item=result.getObject(1).toString()+"."+result.getObject(2).toString();
	                comboOrd.addItem(item);
	            }
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			
		}
	// презарежда комбо кутията от таблицата за продукти
	public void refreshComboPr() {
		
		comboPr.removeAllItems();
		
		String sql="select product_id, name, description, price, quantity from PRODUCTS";
		//conn=DBConnection.getConnection();
		String item="";
		
		try {
            state=conn.prepareStatement(sql);
            result=state.executeQuery();
            while(result.next()) {
                item=result.getObject(1).toString()+"."+result.getObject(2).toString();
                comboPr.addItem(item);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	}
	
	public void ComboS() {

        comboS.removeAllItems();

        //conn=DBConnection.getConnection();
        String sql="select supplier_id, name, contact_info from SUPPLIERS";
        String item="";

        try {
            state=conn.prepareStatement(sql);
            result=state.executeQuery();
            while(result.next()) {
                item=result.getObject(1).toString()+"."+result.getObject(2).toString();
                comboS.addItem(item);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	// обслужва бутона "Добавяне" за въведена поръчка
	class AddStockDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			if(!orderIDTF.getText().isEmpty()) {
				String sql="INSERT INTO ORDERS(order_id, PRODUCT_ID, supplier_id, quantity, status, order_date) VALUES (?, ?, ?, ?, ?, ?)";
				try {
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(orderIDTF.getText()));
					//state.setInt(2, Integer.parseInt(productsTF.getText()));
					String a = comboPr.getSelectedItem().toString();
	                state.setString(2, a.substring(0, a.indexOf(".")));
					//state.setString(2, comboPr.getSelectedItem().toString());
	                 a = comboS.getSelectedItem().toString();
	               state.setString(3, a.substring(0, a.indexOf(".")));
					//state.setString(3, comboS.getSelectedItem().toString());
					//state.setInt(3, Integer.parseInt(supplierTF.getText()));
					state.setString(4, quantityTF.getText());
					state.setString(5, statusTF.getText());
					state.setString(6, orderDateTF.getText());
					state.execute();
					refreshOrdTable();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				quantityTF.setText("");
				statusTF.setText("");
				orderDateTF.setText("");
				id=-1;
			}
		}
	}
	// обслужва бутона "Изтриване" за въведена поръчка
	class DelStockDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			if (id>0) {
				String sql="delete from ORDERS where order_id=?";
				try {
					state=conn.prepareStatement(sql);
					state.setString(1, orderIDTF.getText());
					state.execute();
					refreshOrdTable();
					refreshComboOrd();
				
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				orderIDTF.setText("");
			}
		}
	}
	// обслужва бутона "Промени" за въведена поръчка
	class EditStockDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			if(id>0) {
				String sql="UPDATE ORDERS " +
				        "SET quantity=?, status=?, order_date=? " +
				        "WHERE order_id=?";
			
				try {
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(quantityTF.getText()));
					state.setString(2, statusTF.getText());
					state.setString(3, orderDateTF.getText());
					
					state.setString(4, orderIDTF.getText());
					state.execute();
					refreshOrdTable();
					refreshComboOrd();
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				quantityTF.setText("");
				statusTF.setText("");
				orderDateTF.setText("");
			}
			
		}
	}
	// обслужва бутона "Добавяне" за продукти
	class AddPrDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			if(!productIDTF.getText().isEmpty()) {
				String sql= "INSERT INTO PRODUCTS(product_id, name, description, price, quantity) VALUES (?, ?, ?, ?, ?)";
				try {
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(productIDTF.getText()));
					state.setString(2, nameTF.getText());
					state.setString(3, descriptionTF.getText());
					state.setString(4, priceTF.getText());
					state.setInt(5, Integer.parseInt(prquantityTF.getText()));
					state.execute();
					
					refreshPrTable("PRODUCTS", prTable);
					refreshComboPr();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				productIDTF.setText("");
			}
		}
	}
	// обслужва бутона "Изтриване" за продукти
	class DelPrDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			if (idpr>0) {
				String sql="delete from PRODUCTS where product_id=?";
				try {
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(productIDTF.getText()));
					state.execute();
					refreshPrTable("PRODUCTS", prTable);
					refreshComboPr();
					//idpr=-1;
				
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				productIDTF.setText("");
			}
		}
	}
	// обслужва бутона "Промени" за продукти
	class EditPrDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			if(idpr>0) {
				String sql="update PRODUCTS set name=?, description=?, price=?, quantity=? where product_id=?";
			
				try {
					state=conn.prepareStatement(sql);
					state.setString(1, nameTF.getText());
					state.setString(2, descriptionTF.getText());
					state.setString(3, priceTF.getText());
					state.setInt(4, Integer.parseInt(prquantityTF.getText()));
					state.setInt(5, Integer.parseInt(productIDTF.getText()));
					state.execute();
					refreshPrTable("PRODUCTS", prTable);
					refreshComboPr();
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				nameTF.setText("");
				descriptionTF.setText("");
				priceTF.setText("");
				prquantityTF.setText("");
				
			}
			
		}
	}
	// обслужва бутона "Добавяне" за доставчик
	class AddSupDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			if(!supplierIDTF.getText().isEmpty()) {
				String sql="INSERT INTO SUPPLIERS (supplier_id, name, contact_info) VALUES (?, ?, ?)";
				try {
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(supplierIDTF.getText()));
					state.setString(2, supNameTF.getText());
					state.setString(3, contactInfoTF.getText());
					state.execute();
					refreshSTable("SUPPLIERS", supTable);
					ComboS();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				supplierIDTF.setText("");
			}
		}
	}
	// обслужва бутона "Изтриване" за доставчик
	class DelSupDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			if (idsup>0) {
				String sql="delete from SUPPLIERS where supplier_id=?";
				try {
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(supplierIDTF.getText()));
					state.execute();
					refreshSTable("SUPPLIERS", supTable);
					ComboS();
				
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				supplierIDTF.setText("");
			}
		}
	}
	// обслужва бутона "Промени" за доставчик
	class EditSupDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			if(idsup>0) {
				String sql="update SUPPLIERS set name=?, contact_info=? where supplier_id=?";
			
				try {
					state=conn.prepareStatement(sql);
					state.setString(1, supNameTF.getText());
					state.setString(2, contactInfoTF.getText());
					state.setInt(3, Integer.parseInt(supplierIDTF.getText()));
					state.execute();
					refreshSTable("SUPPLIERS", supTable);
					ComboS();
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				supNameTF.setText("");
				contactInfoTF.setText("");

			}
			
		}
	}
	class MouseActionOrdTable implements MouseListener{

		// обслужва кликването върху ред от таблицата с въведените поръчки
		@Override
		public void mouseClicked(MouseEvent e) {
			
			int row=orderTable.getSelectedRow();
			
			// зареждане на актуалното id за налични поръчки	
			id = Integer.parseInt(orderTable.getValueAt(row, 0).toString());
			orderIDTF.setText(orderTable.getValueAt(row, 0).toString());
			
			//comboPr.setSelectedIndex(Integer.parseInt(prTable.getValueAt(row, 1).toString())-1);
			
			//comboS.setSelectedIndex(Integer.parseInt(supTable.getValueAt(row, 2).toString())-1);
			quantityTF.setText(orderTable.getValueAt(row, 3).toString());
			statusTF.setText(orderTable.getValueAt(row, 4).toString());
			orderDateTF.setText(orderTable.getValueAt(row, 5).toString());
			
		
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class MouseActionPrTable implements MouseListener{

		// обслужва кликването върху ред от таблицата с продукти
		@Override
		public void mouseClicked(MouseEvent e) {
			int row=prTable.getSelectedRow();
			idpr = Integer.parseInt(prTable.getValueAt(row, 0).toString());
			productIDTF.setText(prTable.getValueAt(row, 0).toString());
			nameTF.setText(prTable.getValueAt(row, 1).toString());
			descriptionTF.setText(prTable.getValueAt(row, 2).toString());
			priceTF.setText(prTable.getValueAt(row, 3).toString());
			prquantityTF.setText(prTable.getValueAt(row, 4).toString());
		
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class MouseActionSupTable implements MouseListener{

		// обслужва кликването върху ред от таблицата с доставчик
		@Override
		public void mouseClicked(MouseEvent e) {
			int row=supTable.getSelectedRow();
			
			idsup = Integer.parseInt(supTable.getValueAt(row, 0).toString());
			supplierIDTF.setText(supTable.getValueAt(row, 0).toString());
			supNameTF.setText(supTable.getValueAt(row, 1).toString());
			contactInfoTF.setText(supTable.getValueAt(row, 2).toString());
		
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	//клас за обслужване на бутона търсене за справката
	class SearchDB implements ActionListener {
		public void actionPerformed (ActionEvent arg0) {
//			conn=DBConnection.getConnection();
			// if проверяват и отхвърлят празно текстово поле
			if(!sprOrdDateTF.getText().isEmpty() && !sprPriceTF.getText().isEmpty() && !sprNameTF.getText().isEmpty()) {
				if(Double.parseDouble(sprPriceTF.getText())>0) {
			
					String str="SELECT * FROM OrderProductSupplierView WHERE order_date=? AND price = ? AND supplier_name=? ";
					try {
						state=conn.prepareStatement(str);
						state.setString(1, sprOrdDateTF.getText());
						state.setString(2, sprPriceTF.getText());
						state.setString(3, sprNameTF.getText());
						result=state.executeQuery();
						sprTable.setModel(new MyTModel(result));
				
						sprTable.getColumnModel().getColumn(3).setMinWidth(0);
						sprTable.getColumnModel().getColumn(3).setMaxWidth(0);
						sprTable.getColumnModel().getColumn(3).setWidth(0);

						sprTable.getColumnModel().getColumn(5).setMinWidth(0);
						sprTable.getColumnModel().getColumn(5).setMaxWidth(0);
						sprTable.getColumnModel().getColumn(5).setWidth(0);
			
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sprOrdDateTF.setText("");
					sprPriceTF.setText("");
					sprNameTF.setText("");
					
				}
			}
		}
	}
			


	}
	


