module Yams {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;

	exports yams.vue;

	opens yams.controleur to javafx.graphics, javafx.fxml;
	opens yams.vue to javafx.graphics, javafx.fxml;
	opens yams.model to javafx.graphics, javafx.fxml;
}