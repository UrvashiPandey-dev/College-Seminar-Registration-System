
package com.college.seminar;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private Label pageTitle;
    private Label pageDescription;
    private VBox content;

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f4f6fb;");

        // Sidebar
        VBox sidebar = new VBox(14);
        sidebar.setPadding(new Insets(25, 16, 25, 16));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #172554;");

        Label brand = new Label("SEMINAR\nMANAGER");
        brand.setFont(Font.font("Arial", 22));
        brand.setTextFill(Color.WHITE);

        Label menuLabel = new Label("MAIN MENU");
        menuLabel.setTextFill(Color.LIGHTGRAY);

        Button dashboardBtn = createMenuButton("Dashboard");
        Button studentsBtn = createMenuButton("Students");
        Button speakersBtn = createMenuButton("Speakers");
        Button seminarsBtn = createMenuButton("Seminars");
        Button registrationsBtn = createMenuButton("Registrations");

        sidebar.getChildren().addAll(
            brand, menuLabel, dashboardBtn, studentsBtn,
            speakersBtn, seminarsBtn, registrationsBtn
        );

        // Header
        Label appName = new Label("College Seminar Registration System");
        appName.setFont(Font.font("Arial", 21));

        Label subtitle = new Label("Manage students, speakers and seminars");
        subtitle.setStyle("-fx-text-fill: #64748b;");

        VBox header = new VBox(5, appName, subtitle);
        header.setPadding(new Insets(25, 30, 20, 30));

        // Main content
        pageTitle = new Label("Dashboard");
        pageTitle.setFont(Font.font("Arial", 26));

        pageDescription = new Label("Welcome to your seminar management workspace.");
        pageDescription.setStyle("-fx-text-fill: #64748b;");

        content = new VBox(18);
        content.setPadding(new Insets(25, 30, 30, 30));

        VBox page = new VBox(12, pageTitle, pageDescription, content);
        page.setPadding(new Insets(5));

        BorderPane mainArea = new BorderPane();
        mainArea.setTop(header);
        mainArea.setCenter(page);

        root.setLeft(sidebar);
        root.setCenter(mainArea);

        dashboardBtn.setOnAction(e -> showDashboard());
        studentsBtn.setOnAction(e -> showModule("Students",
            "Manage student records."));
        speakersBtn.setOnAction(e -> showModule("Speakers",
            "Manage speaker records."));
        seminarsBtn.setOnAction(e -> showModule("Seminars",
            "Manage seminar schedules and venues."));
        registrationsBtn.setOnAction(e -> showModule("Registrations",
            "Manage student seminar registrations."));

        showDashboard();

        Scene scene = new Scene(root, 1050, 680);
        stage.setTitle("College Seminar Registration System");
        stage.setMinWidth(850);
        stage.setMinHeight(550);
        stage.setScene(scene);
        stage.show();
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(12));
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-cursor: hand;"
        );
        return button;
    }

    private void showDashboard() {
        pageTitle.setText("Dashboard");
        pageDescription.setText(
            "Welcome! Select a module from the sidebar to get started."
        );

        content.getChildren().clear();

        GridPane cards = new GridPane();
        cards.setHgap(15);
        cards.setVgap(15);

        cards.add(createCard("Students", "Student records"), 0, 0);
        cards.add(createCard("Speakers", "Speaker directory"), 1, 0);
        cards.add(createCard("Seminars", "Seminar schedules"), 0, 1);
        cards.add(createCard("Registrations", "Registration management"), 1, 1);

        Label note = new Label(
            "Database dashboard — live counts will be connected next."
        );
        note.setStyle("-fx-text-fill: #64748b;");

        content.getChildren().addAll(cards, note);
    }

    private VBox createCard(String title, String description) {
        Label heading = new Label(title);
        heading.setFont(Font.font("Arial", 19));

        Label details = new Label(description);
        details.setStyle("-fx-text-fill: #64748b;");

        VBox card = new VBox(10, heading, details);
        card.setPadding(new Insets(22));
        card.setPrefSize(250, 110);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #e2e8f0;" +
            "-fx-border-radius: 12;"
        );

        return card;
    }

    private void showModule(String title, String description) {
        pageTitle.setText(title);
        pageDescription.setText(description);

        content.getChildren().clear();

        Label status = new Label(
            title + " screen selected. Database operations will be added here."
        );
        status.setWrapText(true);
        status.setFont(Font.font("Arial", 15));

        content.getChildren().add(status);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
