module HMS {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens admin to javafx.fxml;
	opens doctor to javafx.fxml;
	opens receptionist to javafx.fxml;
	opens patient to javafx.fxml, javafx.base;
	opens database to javafx.base;
	
	exports admin to javafx.fxml;
	exports doctor to javafx.fxml;
	exports receptionist to javafx.fxml;
	exports patient to javafx.fxml;
}
