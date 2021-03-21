package rental;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class rentalManagament extends JFrame{
	
	String type,sort;
	String sid,sname;
	int ibudget;
	JLabel lblid;
	JLabel lblname;
	JLabel lblbudget;
	JLabel lblsel;
	JLabel lblbb;
	JLabel lblbb1;
	JLabel lblsort;
	JTextField tfid;
	JTextField tfname;
	JTextField tfbudget;
	CheckboxGroup cbg;
	CheckboxGroup cbgsort;
	Checkbox cbgsort1;
	Checkbox cbgsort2;
	Checkbox cbg1;
	Checkbox cbg2;
	JPanel pn1;
	JTextArea txt;
	public rentalManagament()
	{
		try {
			initializeComponents();
			addComponentsToFrame();
			addListenerInterfaces();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(this,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
			}
	}
	private void initializeComponents()
	{
		txt=new JTextArea("Available houses for rent are:",10,40);
		cbg = new CheckboxGroup();  
		cbgsort = new CheckboxGroup();  
		cbg1=new Checkbox("Apartment", cbg, false); 
		cbg1.setBounds(300,100, 100,100); 
		cbg2=new Checkbox("Independent", cbg, false); 
		cbg2.setBounds(350,100, 50,50); 
		cbgsort1=new Checkbox("Budget", cbgsort, false);
		cbgsort1.setBounds(300,100, 100,100); 
		cbgsort2=new Checkbox("ratting", cbgsort, false); 
		cbgsort2.setBounds(300,100, 100,100); 
		lblid=new JLabel("Customer_Id");
		lblname=new JLabel("Customer_Name");
		lblbudget=new JLabel("Budget");
		lblbb=new JLabel("   ");
		lblbb1=new JLabel("   ");
		lblsort =new JLabel("Select the order in which you want to sort:");
		lblsel=new JLabel("Select the type of house:");
		tfid=new JTextField();
		tfname=new JTextField();
		tfbudget=new JTextField();
		pn1=new JPanel();
		pn1.setLayout(new GridLayout(7,2));
	}
	private void addComponentsToFrame()
	{
		setLayout(new FlowLayout());
		pn1.add(lblid);
		pn1.add(tfid);
		pn1.add(lblname);
		pn1.add(tfname);
		pn1.add(lblbudget);
		pn1.add(tfbudget);
		pn1.add(lblsel);
		pn1.add(lblbb);
		pn1.add(cbg1);
		pn1.add(cbg2);
		pn1.add(lblsort);
		pn1.add(lblbb1);
		pn1.add(cbgsort1);
		pn1.add(cbgsort2);
		add(pn1);
		setTitle("Rental Management System");
		add(txt);
	}
	private void addListenerInterfaces()
	{
		sort="Budget";
		tfid.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					sid=tfid.getText();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		tfname.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					sname=tfname.getText();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		tfbudget.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				try {
					ibudget=Integer.parseInt(tfbudget.getText());
					if(ibudget<1000)
					{
						JOptionPane.showMessageDialog(null,"Budget should be not be less than 1000","WARNING",JOptionPane.WARNING_MESSAGE);
						ibudget=999999;
					}
					else
					{
						try {
							String id=tfid.getText();
							String name=tfname.getText();
							Class.forName("oracle.jdbc.driver.OracleDriver");
							Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vasavi","vasavi");
							Statement st=con.createStatement();
							String query = " insert into customer(cust_id,name,budget)"
							        + " values (?,?,?)";
							PreparedStatement preparedStmt = con.prepareStatement(query);
							preparedStmt.setString (1,id);
							preparedStmt.setString (2,name);
							preparedStmt.setInt    (3, ibudget);
							preparedStmt.execute();
						      con.close();

						}
						catch(Exception ex)
						{
							JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		cbg1.addItemListener(new ItemListener()
				{
			public void itemStateChanged(ItemEvent e)
			{
				type="Apartment";
				try {
					int i=0;
					txt.setText("HOUSES AVAILABLE FOR RENT ARE:\n");
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vasavi","vasavi");
					Statement st=con.createStatement();
					String sg="select * from house natural join (apartname natural join (flat natural join floor))";
					ResultSet rs=st.executeQuery(sg);
					txt.append("Location----------------------------------Rent---------------Rooms-----Apt_name\n");
					while(rs.next())
					{
						if(rs.getInt(4)>=ibudget)
						{
							i=i+1;
							txt.append(i+"."+rs.getString(2)+"------"+rs.getInt(4)+"----"+rs.getInt(6)+"----"+rs.getString(8)+"\n");
						}
					}
					con.close();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
				});
		cbg2.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				type="Independent";
				try {
					int i=0;
					txt.setText("HOUSES AVAILABLE FOR RENT ARE:\n");
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vasavi","vasavi");
					Statement st=con.createStatement();
					String sg="select * from house natural join (indeptplot natural join indepthouse)";
					ResultSet rs=st.executeQuery(sg);
					txt.append("Location----------------------------------Rent---------------Rooms-----plot.no-----------floors\n");
					while(rs.next())
					{
						
						if(rs.getInt(4)>=ibudget)
						{
							i=i+1;
							txt.append(i+"."+rs.getString(2)+"------"+rs.getInt(4)+"----"+rs.getInt(6)+"----"+rs.getString(8)+"---"+rs.getInt(9)+"\n");
						}
					}
					con.close();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		cbgsort1.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				sort="Budget";
				if((type).equals("Independent"))
				{
					try {
						int i=0;
						txt.setText("HOUSES AVAILABLE FOR RENT ARE:\n");
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vasavi","vasavi");
						Statement st=con.createStatement();
						String sg="select * from house natural join (indeptplot natural join indepthouse) order by rent";
						ResultSet rs=st.executeQuery(sg);
						txt.append("Location----------------------------------Rent---------------Rooms-----plot.no-----------floors\n");
						while(rs.next())
						{
							
							if(rs.getInt(4)>=ibudget)
							{
								i=i+1;
								txt.append(i+"."+rs.getString(2)+"------"+rs.getInt(4)+"----"+rs.getInt(6)+"----"+rs.getString(8)+"---"+rs.getInt(9)+"\n");
							}
						}
						con.close();
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					try {
						int i=0;
						txt.setText("HOUSES AVAILABLE FOR RENT ARE:\n");
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vasavi","vasavi");
						Statement st=con.createStatement();
						String sg="select * from house natural join (apartname natural join (flat natural join floor)) order by rent";
						ResultSet rs=st.executeQuery(sg);
						txt.append("Location----------------------------------Rent---------------Rooms-----Apt_name\n");
						while(rs.next())
						{
							
							if(rs.getInt(4)>=ibudget)
							{
								i=i+1;
								txt.append(i+"."+rs.getString(2)+"------"+rs.getInt(4)+"----"+rs.getInt(6)+"----"+rs.getString(8)+"\n");
							}
						}
						con.close();
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		cbgsort2.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				sort="rating";
				if((type).equals("Independent"))
				{
					try {
						int i=0;
						txt.setText("HOUSES AVAILABLE FOR RENT ARE:\n");
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vasavi","vasavi");
						Statement st=con.createStatement();
						String sg="select * from house natural join (indeptplot natural join indepthouse) order by rating";
						ResultSet rs=st.executeQuery(sg);
						txt.append("Location----------------------------------Rent---------------Rooms-----plot.no-----------floors\n");
						while(rs.next())
						{
							
							if(rs.getInt(4)>=ibudget)
							{
								i=i+1;
								txt.append(i+"."+rs.getString(2)+"------"+rs.getInt(4)+"----"+rs.getInt(6)+"----"+rs.getString(8)+"---"+rs.getInt(9)+"\n");
							}
						}
						con.close();
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					try {
						int i=0;
						txt.setText("HOUSES AVAILABLE FOR RENT ARE:\n");
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vasavi","vasavi");
						Statement st=con.createStatement();
						String sg="select * from house natural join (apartname natural join (flat natural join floor)) order by rating";
						ResultSet rs=st.executeQuery(sg);
						txt.append("Location----------------------------------Rent---------------Rooms-----Apt_name\n");
						while(rs.next())
						{
							if(rs.getInt(4)>=ibudget)
							{
								i=i+1;
								txt.append(i+"."+rs.getString(2)+"------"+rs.getInt(4)+"----"+rs.getInt(6)+"----"+rs.getString(8)+"\n");
							}
						}
						con.close();
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null,"Error Occured","ERROR",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}
