package hw10.main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Retail extends Application implements Initializable {

	private DerbyHandle derby;

	@FXML
	private TableColumn<?, ?> tcCol0;

	@FXML
	private TableColumn<?, ?> tcCol1;

	@FXML
	private TableColumn<?, ?> tcCol2;

	@FXML
	private TableColumn<?, ?> tcCol3;

	@FXML
	private TableColumn<?, ?> tcCol4;

	@FXML
	private TableColumn<?, ?> tcCol5;

	@FXML
	private TableColumn<?, ?> tcCol6;

	@FXML
	private Stage primaryStage;

	@FXML
	private MenuItem customerAddNew;

	@FXML
	private MenuItem customerListAll;

	@FXML
	private MenuItem employeeAddNew;

	@FXML
	private MenuItem employeeListAll;

	@FXML
	private MenuItem merchandiseAddNew;

	@FXML
	private MenuItem merchandiseListAll;

	@FXML
	private MenuItem miexit;

	@FXML
	private TableView<RetailTuple> retailtable;

	/**
	 * @author jsnrice
	 * @desc Private inner class to represent the tuple rows of the retail
	 *       table. Columns are generic and go off an indexed array count from
	 *       0..8 In some cases not every column will be populated. This is only
	 *       the case for the Merchandise data. For Merchandise a special three
	 *       attribute tuple constructor has already been established.
	 */
	public class RetailTuple {

		public void setCol0(String col0) {
			this.col0.set(col0);
		}

		public void setCol1(String col1) {
			this.col1.set(col1);
		}

		public void setCol2(String col2) {
			this.col2.set(col2);
		}

		public void setCol3(String col3) {
			this.col3.set(col3);
		}

		public void setCol4(String col4) {
			this.col4.set(col4);
		}

		public void setCol5(String col5) {
			this.col5.set(col5);
		}

		public void setCol6(String col6) {
			this.col6.set(col6);
		}

		public String getCol0() {
			return this.col0.get();
		}

		public String getCol1() {
			return this.col1.get();
		}

		public String getCol2() {
			return this.col2.get();
		}

		public String getCol3() {
			return this.col3.get();
		}

		public String getCol4() {
			return this.col4.get();
		}

		public String getCol5() {
			return this.col5.get();
		}

		public String getCol6() {
			return this.col6.get();
		}

		private final SimpleStringProperty col0 = new SimpleStringProperty("");
		private final SimpleStringProperty col1 = new SimpleStringProperty("");
		private final SimpleStringProperty col2 = new SimpleStringProperty("");
		private final SimpleStringProperty col3 = new SimpleStringProperty("");
		private final SimpleStringProperty col4 = new SimpleStringProperty("");
		private final SimpleStringProperty col5 = new SimpleStringProperty("");
		private final SimpleStringProperty col6 = new SimpleStringProperty("");

		/* Constructor for the Employee and Customer based tuples. */
		public RetailTuple(String col0, String col1, String col2, String col3,
				String col4, String col5, String col6) {
			setCol0(col0);
			setCol1(col1);
			setCol2(col2);
			setCol3(col3);
			setCol4(col4);
			setCol5(col5);
			setCol6(col6);
		}

		/* Constructor for the Merchandise based tuples. */
		public RetailTuple(String col0, String col1, String col2) {
			setCol0(col0);
			setCol1(col1);
			setCol2(col2);
		}
	}

	@FXML
	private String[] states;

	public void popupPersonAddNew(final String titleStr, boolean isCustomer,
			boolean isEmployee) {
		Logger.getLogger(Retail.class.getName()).log(Level.INFO,
				"EVENT: popupPersonAddNew");
		Stage newStage = new Stage();
		VBox comp = new VBox();

		HBox titlebox = new HBox();
		titlebox.setPadding(new Insets(25, 0, 0, 40));
		titlebox.setSpacing(30);
		Text title = new Text(titleStr);
		title.setFont(Font.font(null, FontWeight.BOLD, 14));
		titlebox.getChildren().add(title);

		// First Name
		HBox fnamebox = new HBox();
		fnamebox.setPadding(new Insets(25, 0, 0, 40));
		fnamebox.setSpacing(30);
		Text fnameDisplay = new Text("First Name");
		TextField fname = new TextField("");
		fnamebox.getChildren().add(fnameDisplay);
		fnamebox.getChildren().add(fname);

		// Last Name
		HBox lnamebox = new HBox();
		lnamebox.setPadding(new Insets(25, 0, 0, 40));
		lnamebox.setSpacing(31);
		Text lnameDisplay = new Text("Last Name");
		TextField lname = new TextField("");
		lnamebox.getChildren().add(lnameDisplay);
		lnamebox.getChildren().add(lname);

		// City
		HBox citybox = new HBox();
		citybox.setPadding(new Insets(25, 0, 0, 40));
		citybox.setSpacing(76);
		Text cityDisplay = new Text("City");
		TextField city = new TextField("");
		citybox.getChildren().add(cityDisplay);
		citybox.getChildren().add(city);

		// Address
		HBox addressbox = new HBox();
		addressbox.setPadding(new Insets(25, 0, 0, 40));
		addressbox.setSpacing(5);
		Text streetAddressDisplay = new Text("Street Address");
		TextField streetAddress = new TextField("");
		addressbox.getChildren().add(streetAddressDisplay);
		addressbox.getChildren().add(streetAddress);

		HBox statebox = new HBox();
		Text stateDisplay = new Text("State");
		ChoiceBox<String> statesMenu = new ChoiceBox<String>();
		statesMenu.getItems().addAll(states);

		statebox.setPadding(new Insets(25, 0, 0, 40));
		statebox.setSpacing(68);
		statebox.getChildren().add(stateDisplay);
		statebox.getChildren().add(statesMenu);

		HBox zipbox = new HBox();
		zipbox.setPadding(new Insets(25, 0, 0, 40));
		zipbox.setSpacing(49);
		TextField zipcode = new TextField("");
		Text zipcodeDisplay = new Text("Zipcode");
		zipbox.getChildren().add(zipcodeDisplay);
		zipbox.getChildren().add(zipcode);

		HBox genderbox = new HBox();
		genderbox.setPadding(new Insets(25, 0, 0, 40));
		genderbox.setSpacing(49);
		Text genderDisplay = new Text("Gender");
		RadioButton male = new RadioButton("Male");
		male.setSelected(true);
		RadioButton female = new RadioButton("Female");
		ToggleGroup genders = new ToggleGroup();
		genders.getToggles().addAll(male, female);
		genderbox.getChildren().add(genderDisplay);
		genderbox.getChildren().addAll(male, female);

		HBox buttons = new HBox();
		buttons.setPadding(new Insets(25, 0, 0, 40));
		buttons.setSpacing(10);
		Button ok = new Button("OK"); // Submit a person
		/*****************************************
		 * The 'OK' button will either add a new customer or employee.
		 ****************************************/
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String genderStr = "male";
				if (female.isSelected()) {
					genderStr = "female";
				}
				RetailTuple rt = new RetailTuple(fname.getText(), lname
						.getText(), streetAddress.getText(), city.getText(),
						statesMenu.getSelectionModel().getSelectedItem(),
						zipcode.getText(), genderStr);
				if (isCustomer) {
					derby.insertPersonTuples("CUSTOMER", packTuple(rt, true));
					updateRetailTableSingleton(true, false, false, rt);
				} else if (isEmployee) {
					derby.insertPersonTuples("EMPLOYEE", packTuple(rt, true));
					updateRetailTableSingleton(false, true, false, rt);
				}
				newStage.close();
			}
		});

		Button cancel = new Button("Cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				newStage.close();
			}
		});
		buttons.getChildren().add(ok);
		buttons.getChildren().add(cancel);

		comp.getChildren().add(titlebox);
		comp.getChildren().add(fnamebox);
		comp.getChildren().add(lnamebox);
		comp.getChildren().add(addressbox);
		comp.getChildren().add(citybox);
		comp.getChildren().add(statebox);
		comp.getChildren().add(zipbox);
		comp.getChildren().add(genderbox);
		comp.getChildren().add(buttons);

		Scene stageScene = new Scene(comp, 350, 475);
		newStage.setScene(stageScene);
		newStage.show();
	}

	/****************************************************************************
	 * @desc Lazy algorithm packing of tuple data. Lazy meaning that no bounds or
	 *       error checking is taken into account. If the personBased flag is
	 *       true than assume that all column fields are established properly
	 *       else assume a MERCHANDISE based tuple and pack 3 values into the
	 *       array.
	 *       
	 * @param rt
	 * @param personBased
	 * @return An array of packed tuple data values.
	 ***************************************************************************/
	public ArrayList<String> packTuple(RetailTuple rt, boolean personBased) {
		ArrayList<String> data = new ArrayList<String>();
		// Each table has at least three columns:
		data.add(rt.getCol0());
		data.add(rt.getCol1());
		data.add(rt.getCol2());
		if (personBased) { // pack according to EMPLOYEE or CUSTOMER based
						   // format (7 values)
			data.add(rt.getCol3());
			data.add(rt.getCol4());
			data.add(rt.getCol5());
			data.add(rt.getCol6());
		}
		return data;
	}

	/**
	 * @desc Truncate and then insert a single row into the retail table. This is for the
	 * 'Add New' menu item. After the user clicks OK then the data is immediately redrawn
	 * into the retail table.
	 * 
	 * @param isCustomer
	 *            Tuples (rows) to insert are customers.
	 * @param isEmployee
	 *            Tuples (rows) to insert are employees.
	 * @param isMerchandise
	 *            Tuples (rows) to insert are merchandise.
	 */
	public void updateRetailTableSingleton(boolean isCustomer, boolean isEmployee,
			boolean isMerchandise, RetailTuple singleton) {
		Logger.getLogger(Retail.class.getName()).log(Level.INFO,
				"EVENT: updateRetailTableSingleton");
		retailtable.setEditable(true);

		ObservableList<RetailTuple> data = retailtable.getItems();
		data.removeAll(data); // TRUNCATE

		// TRUNCATE column values:
		clearColumns();

		// Draw the column headers:
		if (isCustomer || isEmployee) {
			Logger.getLogger(Retail.class.getName()).log(Level.INFO,
					"EVENT: updateRetailTableSingleton - adding person based tuple");
			tcCol0.setText("First Name");
			tcCol1.setText("Last Name");
			tcCol2.setText("Address");
			tcCol3.setText("City");
			tcCol4.setText("State");
			tcCol5.setText("Zipcode");
			tcCol6.setText("Gender");
		} else if (isMerchandise) {
			Logger.getLogger(Retail.class.getName()).log(Level.INFO,
					"EVENT: updateRetailTableSingleton - adding MERCHANDISE tuple");
			tcCol0.setText("Name");
			tcCol1.setText("Price");
			tcCol2.setText("Description");
		}

		// Add the singleton in:
		Logger.getLogger(Retail.class.getName())
				.log(Level.INFO,
						"EVENT: updateRetailTable - reinserting tuples into retail JavaFX table.");
		data = retailtable.getItems();
		data.add(singleton);
	}
	
	/**
	 * @desc Truncate and then insert new rows into the retail table.
	 * @param isCustomer
	 *            Tuples (rows) to insert are customers.
	 * @param isEmployee
	 *            Tuples (rows) to insert are employees.
	 * @param isMerchandise
	 *            Tuples (rows) to insert are merchandise.
	 */
	public void updateRetailTable(boolean isCustomer, boolean isEmployee,
			boolean isMerchandise) {
		Logger.getLogger(Retail.class.getName()).log(Level.INFO,
				"EVENT: updateRetailTable");
		retailtable.setEditable(true);

		ObservableList<RetailTuple> data = retailtable.getItems();
		data.removeAll(data); // TRUNCATE

		ArrayList<RetailTuple> retailRows = new ArrayList<RetailTuple>();
		try {
			// Add new tuples to the retailRows list:
			if (isMerchandise) {
				Logger.getLogger(Retail.class.getName()).log(Level.INFO,
						"EVENT: updateRetailTable - adding merchandise tuples");
				ResultSet results = derby.getTuples("MERCHANDISE");
				while (results.next()) {
					RetailTuple rt = new RetailTuple(results.getString("name"),
							results.getString("price"),
							results.getString("description"));
					retailRows.add(rt);
				}

			} else if (isCustomer) {
				ResultSet results = derby.getTuples("CUSTOMER");
				while (results.next()) {
					RetailTuple rt = new RetailTuple(
							results.getString("fname"),
							results.getString("lname"),
							results.getString("address"),
							results.getString("city"),
							results.getString("state"),
							results.getString("zipcode"),
							results.getString("gender"));
					retailRows.add(rt);
				}
			} else if (isEmployee) {
				ResultSet results = derby.getTuples("EMPLOYEE");
				while (results.next()) {
					RetailTuple rt = new RetailTuple(
							results.getString("fname"),
							results.getString("lname"),
							results.getString("address"),
							results.getString("city"),
							results.getString("state"),
							results.getString("zipcode"),
							results.getString("gender"));
					retailRows.add(rt);
				}
			}
		} catch (SQLException e) {
			Logger.getLogger(Retail.class.getName()).log(Level.SEVERE,
					"ERROR: Retrieving existing retail based tuples.");
			e.printStackTrace();
		}
		// TRUNCATE column values:
		clearColumns();

		// Draw the column headers:
		if (isCustomer || isEmployee) {
			tcCol0.setText("First Name");
			tcCol1.setText("Last Name");
			tcCol2.setText("Address");
			tcCol3.setText("City");
			tcCol4.setText("State");
			tcCol5.setText("Zipcode");
			tcCol6.setText("Gender");
		} else if (isMerchandise) {
			tcCol0.setText("Name");
			tcCol1.setText("Price");
			tcCol2.setText("Description");
		}

		// Re-populate the retail table:
		Logger.getLogger(Retail.class.getName())
				.log(Level.INFO,
						"EVENT: updateRetailTable - re-inserting tuples into retail JavaFX table.");
		data = retailtable.getItems();
		for (RetailTuple rt : retailRows) {
			data.add(rt);
		}
	}

	public void clearColumns() {
		tcCol0.setText("");
		tcCol1.setText("");
		tcCol2.setText("");
		tcCol3.setText("");
		tcCol4.setText("");
		tcCol5.setText("");
		tcCol6.setText("");
	}

	public void popupMerchandiseAddNew(final String titleStr) {
		Logger.getLogger(Retail.class.getName()).log(Level.INFO,
				"EVENT: popupMerchandiseAddNew");
		Stage newStage = new Stage();
		VBox comp = new VBox();

		HBox titlebox = new HBox();
		titlebox.setPadding(new Insets(25, 0, 0, 40));
		titlebox.setSpacing(30);
		Text title = new Text(titleStr);
		title.setFont(Font.font(null, FontWeight.BOLD, 14));
		titlebox.getChildren().add(title);

		// Name
		HBox namebox = new HBox();
		namebox.setPadding(new Insets(25, 0, 0, 40));
		namebox.setSpacing(43);
		Text nameDisplay = new Text("Name");
		TextField name = new TextField("");
		namebox.getChildren().add(nameDisplay);
		namebox.getChildren().add(name);

		// Price
		HBox pricebox = new HBox();
		pricebox.setPadding(new Insets(25, 0, 0, 40));
		pricebox.setSpacing(50);
		Text priceDisplay = new Text("Price");
		TextField price = new TextField("");
		pricebox.getChildren().add(priceDisplay);
		pricebox.getChildren().add(price);

		// Description
		HBox descbox = new HBox();
		descbox.setPadding(new Insets(25, 0, 0, 40));
		descbox.setSpacing(5);
		Text descDisplay = new Text("Description");
		TextArea desc = new TextArea("");
		descbox.getChildren().add(descDisplay);
		descbox.getChildren().add(desc);

		HBox buttons = new HBox();
		buttons.setPadding(new Insets(25, 0, 0, 40));
		buttons.setSpacing(10);
		Button ok = new Button("OK");
		/*****************************************
		 * The 'OK' button will either add a new customer or employee.
		 ****************************************/
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				derby.insertMerchandiseTuple(name.getText(),Double.parseDouble(price.getText()),desc.getText());
				updateRetailTableSingleton(false, false, true, new RetailTuple(
						name.getText(), price.getText(), desc.getText()));
				newStage.close();
			}
		});

		Button cancel = new Button("Cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				newStage.close();
			}
		});
		buttons.getChildren().add(ok);
		buttons.getChildren().add(cancel);

		comp.getChildren().add(titlebox);
		comp.getChildren().add(namebox);
		comp.getChildren().add(pricebox);
		comp.getChildren().add(descbox);
		comp.getChildren().add(buttons);

		Scene stageScene = new Scene(comp, 750, 415);
		newStage.setScene(stageScene);
		newStage.show();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			this.primaryStage = primaryStage;
			AnchorPane page = FXMLLoader.load(Retail.class
					.getResource("Retail.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Retail Management System");
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(Retail.class.getName())
					.log(Level.SEVERE, null, ex);
		}
	}

	public void setEvents() {
		miexit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Logger.getLogger(Retail.class.getName()).log(Level.INFO,
						"Shutting down clean exit.");
				System.exit(0);
			}
		});

		customerAddNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Logger.getLogger(Retail.class.getName()).log(Level.INFO,
						"EVENT: customerAddNew.");
				popupPersonAddNew("Add New Customer", true, false);
			}
		});

		customerListAll.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Logger.getLogger(Retail.class.getName()).log(Level.INFO,
						"EVENT: customerListAll.");
				updateRetailTable(true, false, false);
			}
		});

		employeeAddNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Logger.getLogger(Retail.class.getName()).log(Level.INFO,
						"EVENT: employeeAddNew.");
				popupPersonAddNew("Add New Employee", false, true);
			}
		});

		employeeListAll.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Logger.getLogger(Retail.class.getName()).log(Level.INFO,
						"EVENT: employeeListAll.");
				updateRetailTable(false, true, false);
			}
		});

		merchandiseAddNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Logger.getLogger(Retail.class.getName()).log(Level.INFO,
						"EVENT: merchandiseAddNew.");
				popupMerchandiseAddNew("Add New Merchandise");
			}
		});

		merchandiseListAll.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Logger.getLogger(Retail.class.getName()).log(Level.INFO,
						"EVENT: merchandiseListAll.");
				updateRetailTable(false, false, true);
			}
		});
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(Retail.class, (java.lang.String[]) null);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert miexit != null : "fx:id=\"miexit\" was not injected: check your FXML file.";
		derby = new DerbyHandle();

		// First drop the tables in case they are already in memory (safe
		// method):
		derby.dropTable("EMPLOYEE");
		derby.dropTable("CUSTOMER");
		derby.dropTable("MERCHANDISE");

		// Create EMPLOYEE and CUSTOMER tables:
		ArrayList<String> createPersonBasedTable = new ArrayList<String>();
		createPersonBasedTable.add("fname VARCHAR(25)");
		createPersonBasedTable.add("lname VARCHAR(25)");
		createPersonBasedTable.add("address VARCHAR(50)");
		createPersonBasedTable.add("city VARCHAR(25)");
		createPersonBasedTable.add("state VARCHAR(25)");
		createPersonBasedTable.add("zipcode VARCHAR(25)"); // in case there are hyphens
		// Note: an enum type of 'gender' with values 'male' and 'female' can be
		// created
		// but for simplicity just use a varchar:
		createPersonBasedTable.add("gender VARCHAR(6)");
		derby.createTable("EMPLOYEE", createPersonBasedTable);
		derby.createTable("CUSTOMER", createPersonBasedTable);

		// Create MERCHANDISE table
		ArrayList<String> createMerchandiseTable = new ArrayList<String>();
		createMerchandiseTable.add("name VARCHAR(25)");
		createMerchandiseTable.add("price DECIMAL (20,2)"); // dollars and cents
		createMerchandiseTable.add("description VARCHAR(200)");
		derby.createTable("MERCHANDISE", createMerchandiseTable);

		// Set the JavaFX events
		setEvents();

		// Generate the states and their territories.
		states = new String[] { "Alabama", "Alaska", "Arizona", "Arkansas",
				"California", "Colorado", "Connecticut", "Delaware", "Florida",
				"Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
				"Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
				"Massachusetts", "Michigan", "Minnesota", "Mississippi",
				"Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
				"New Jersey", "New Mexico", "New York", "North Carolina",
				"North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
				"Rhode Island", "South Carolina", "South Dakota", "Tennessee",
				"Texas", "Utah", "Vermont", "Virginia", "Washington",
				"West Virginia", "Wisconsin", "Wyoming",
				"District of Columbia", "Puerto Rico", "Guam",
				"American Samoa", "U.S. Virgin Islands",
				"Northern Mariana Islands" };
	}
}