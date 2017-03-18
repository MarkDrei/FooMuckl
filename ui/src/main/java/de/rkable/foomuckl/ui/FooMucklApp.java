package de.rkable.foomuckl.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class FooMucklApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("FooMuckl UI");

		Label label = new Label("Hello World");
		StackPane root = new StackPane();
		root.getChildren().add(label);
		stage.setScene(new Scene(root, 300, 250));
		stage.show();

	}

}
