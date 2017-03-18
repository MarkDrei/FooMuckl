package de.rkable.foomuckl.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.rkable.foomuckl.core.Lifecycle;
import de.rkable.foomuckl.ui.action.handler.SpeechDisplay;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class FooMucklApp extends Application {

	private static final int INTERVALL = 100;

	private static boolean running;

	private static Injector injector;

	public static void main(String[] args) {

		running = true;
		injector = Guice.createInjector(new FooModuleUi());
		Lifecycle instance = injector.getInstance(Lifecycle.class);

		/**
		 * This thread "creates" the elapsed time for FooMuckl
		 */
		new Thread(() -> {
			while (running) {
				try {
					Thread.sleep(INTERVALL);
					instance.executeEventLoop();
				} catch (InterruptedException e) {
					// no op
				}
			}
		}).start();

		launch(args);
		running = false;
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("FooMuckl UI");

		TextArea textArea = new TextArea("Hello World");
		textArea.setEditable(false);
		StackPane root = new StackPane();
		root.getChildren().add(textArea);
		stage.setScene(new Scene(root, 300, 250));
		stage.show();

		SpeechDisplay speechDisplay = injector.getInstance(SpeechDisplay.class);
		speechDisplay.addListener((String speech) -> {
			textArea.setText(speech);
		});
	}

}
