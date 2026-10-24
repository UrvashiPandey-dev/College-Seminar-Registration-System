
package com.college.seminar;

import com.college.seminar.model.Student;
import com.college.seminar.model.Registration;
import com.college.seminar.service.SeminarService;
import com.college.seminar.service.RegistrationService;
import com.college.seminar.service.ReportService;
import com.college.seminar.model.Seminar;
import com.college.seminar.service.SpeakerService;
import com.college.seminar.model.Speaker;
import com.college.seminar.service.StudentService;

import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.Optional;

public class Main extends Application {

    // ---------- THEME ----------
    private static final String BG = "#F5F7FC";
    private static final String NAVY = "#202747";
    private static final String BLUE = "#7189E8";
    private static final String PINK = "#F2B7D0";
    private static final String MELON = "#FFB49A";
    private static final String PURPLE = "#BDB2F4";
    private static final String WHITE = "#FFFFFF";
    private static final String INK = "#252B43";
    private static final String MUTED = "#858BA2";
    private static final String BORDER = "#E9EAF2";

    private final StudentService studentService = new StudentService();
    private final SeminarService seminarService = new SeminarService();
    private final RegistrationService registrationService = new RegistrationService();
    private final SpeakerService speakerService = new SpeakerService();

    private Stage mainStage;
    private BorderPane root;
    private VBox sidebar;
    private VBox content;
    private Label pageTitle;
    private Label pageDescription;

    private boolean sidebarOpen = false;

    private ObservableList<Student> studentData =
            FXCollections.observableArrayList();

    private FilteredList<Student> filteredStudents;

    @Override
    public void start(Stage stage) {
        mainStage = stage;

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + BG + ";");

        sidebar = buildSidebar();

        BorderPane mainArea = new BorderPane();
        mainArea.setStyle("-fx-background-color: " + BG + ";");

        mainArea.setTop(buildHeader());

        content = new VBox(22);
        content.setPadding(new Insets(28, 32, 30, 32));
        content.setFillWidth(true);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(
                "-fx-background: " + BG + ";" +
                "-fx-background-color: " + BG + ";" +
                "-fx-border-color: transparent;"
        );

        mainArea.setCenter(scrollPane);
        root.setCenter(mainArea);

        Scene scene = new Scene(root, 1280, 820);

        stage.setTitle("College Seminar Registration System");
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setScene(scene);

        showDashboard();
        stage.show();
    }

    // ---------- SIDEBAR ----------

    private VBox buildSidebar() {
        VBox menu = new VBox(10);
        menu.setPrefWidth(235);
        menu.setMinWidth(235);
        menu.setPadding(new Insets(25, 16, 22, 16));
        menu.setStyle("-fx-background-color: " + NAVY + ";");

        HBox brand = new HBox(10);
        brand.setAlignment(Pos.CENTER_LEFT);
        brand.setPadding(new Insets(0, 0, 25, 2));

        Label logo = new Label("CS");
        logo.setMinSize(42, 42);
        logo.setAlignment(Pos.CENTER);
        logo.setStyle(
                "-fx-background-color: " + BLUE + ";" +
                "-fx-background-radius: 13;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;"
        );

        VBox brandText = new VBox(3);
        Label brandName = new Label("College Seminar");
        brandName.setTextFill(Color.WHITE);
        brandName.setFont(Font.font("Arial", 15));
        brandName.setStyle("-fx-font-weight: bold;");

        Label brandSub = new Label("REGISTRATION SYSTEM");
        brandSub.setTextFill(Color.web("#AEB5D1"));
        brandSub.setFont(Font.font("Arial", 9));

        brandText.getChildren().addAll(brandName, brandSub);
        brand.getChildren().addAll(logo, brandText);

        Label menuLabel = new Label("WORKSPACE");
        menuLabel.setTextFill(Color.web("#929AB9"));
        menuLabel.setFont(Font.font("Arial", 10));
        menuLabel.setPadding(new Insets(10, 0, 5, 8));

        Button dashboardBtn = menuButton("⌂", "Dashboard");
        Button studentsBtn = menuButton("♙", "Students");
        Button speakersBtn = menuButton("♧", "Speakers");
        Button seminarsBtn = menuButton("▤", "Seminars");
        Button registrationsBtn = menuButton("✓", "Registrations");
        Button reportsBtn = menuButton("▥", "Reports");

        dashboardBtn.setOnAction(e -> showDashboard());
        studentsBtn.setOnAction(e -> showStudents());
        speakersBtn.setOnAction(e ->
                showSpeakers());
        seminarsBtn.setOnAction(e ->
                showSeminars());
        registrationsBtn.setOnAction(e -> showRegistrations());
        reportsBtn.setOnAction(e -> showReports());

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label footer = new Label("COLLEGE SEMINAR SYSTEM\nVersion 1.0");
        footer.setTextFill(Color.web("#929AB9"));
        footer.setFont(Font.font("Arial", 10));
        footer.setPadding(new Insets(20, 5, 0, 5));

        menu.getChildren().addAll(
                brand, menuLabel, dashboardBtn, studentsBtn,
                speakersBtn, seminarsBtn, registrationsBtn, reportsBtn,
                spacer, footer
        );

        return menu;
    }

    private Button menuButton(String symbol, String text) {
        Button button = new Button(symbol + "     " + text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinHeight(44);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(0, 12, 0, 14));
        button.setFont(Font.font("Arial", 13));
        button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #E6E9F7;" +
                "-fx-background-radius: 10;" +
                "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #343D62;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
                ));

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: #E6E9F7;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
                ));

        return button;
    }

    // ---------- HEADER ----------

    private HBox buildHeader() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 32, 18, 32));
        header.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: transparent transparent " + BORDER + " transparent;"
        );

        Button menuToggle = new Button("☰");
        menuToggle.setMinSize(42, 42);
        menuToggle.setStyle(
                "-fx-background-color: " + BG + ";" +
                "-fx-background-radius: 12;" +
                "-fx-font-size: 19px;" +
                "-fx-cursor: hand;"
        );

        menuToggle.setOnAction(e -> {
            sidebarOpen = !sidebarOpen;

            if (sidebarOpen) {
                root.setLeft(sidebar);
            } else {
                root.setLeft(null);
            }
        });

        VBox titleBox = new VBox(4);

        pageTitle = new Label("Dashboard");
        pageTitle.setTextFill(Color.web(INK));
        pageTitle.setFont(Font.font("Arial", 23));
        pageTitle.setStyle("-fx-font-weight: bold;");

        pageDescription = new Label("Welcome back! Here's your seminar overview.");
        pageDescription.setTextFill(Color.web(MUTED));
        pageDescription.setFont(Font.font("Arial", 12));

        titleBox.getChildren().addAll(pageTitle, pageDescription);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label dateLabel = new Label(
                LocalDate.now().format(
                        DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
                )
        );
        dateLabel.setTextFill(Color.web(INK));
        dateLabel.setPadding(new Insets(10, 13, 10, 13));
        dateLabel.setStyle(
                "-fx-background-color: " + BG + ";" +
                "-fx-background-radius: 10;" +
                "-fx-font-size: 12px;"
        );

        Label brandIcon = new Label("CS");
        brandIcon.setMinSize(40, 40);
        brandIcon.setAlignment(Pos.CENTER);
        brandIcon.setStyle(
                "-fx-background-color: " + PURPLE + ";" +
                "-fx-background-radius: 13;" +
                "-fx-text-fill: " + NAVY + ";" +
                "-fx-font-weight: bold;"
        );

        VBox brandInfo = new VBox(3);
        Label collegeName = new Label("College Seminar");
        collegeName.setTextFill(Color.web(INK));
        collegeName.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

        Label collegeSub = new Label("Management Portal");
        collegeSub.setTextFill(Color.web(MUTED));
        collegeSub.setFont(Font.font("Arial", 10));

        brandInfo.getChildren().addAll(collegeName, collegeSub);

        header.getChildren().addAll(
                menuToggle, titleBox, spacer, dateLabel, brandIcon, brandInfo
        );

        return header;
    }

    // ---------- PAGE HELPERS ----------

    private void setPage(String title, String description) {
        pageTitle.setText(title);
        pageDescription.setText(description);
        content.getChildren().clear();
    }

    private Label label(String text, int size, String color, boolean bold) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", size));
        label.setTextFill(Color.web(color));

        if (bold) {
            label.setStyle("-fx-font-weight: bold;");
        }

        return label;
    }

    private VBox panel(String title, String subtitle) {
        VBox box = new VBox(14);
        box.setPadding(new Insets(20));
        box.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 18;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 18;"
        );

        box.getChildren().add(label(title, 16, INK, true));

        if (subtitle != null && !subtitle.isBlank()) {
            box.getChildren().add(label(subtitle, 11, MUTED, false));
        }

        return box;
    }

    private Button actionButton(String text, String color) {
        Button button = new Button(text);
        button.setMinHeight(38);
        button.setPadding(new Insets(0, 16, 0, 16));
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );
        return button;
    }

    // ---------- DASHBOARD ----------
    private String getTotalStudents() {
    try {
        return String.valueOf(studentService.getAllStudents().size());
    } catch (SQLException e) {
        System.err.println("Could not load student count: " + e.getMessage());
        return "N/A";
    }
}

    private void showDashboard() {
        setPage("Dashboard", "Welcome back! Here's your seminar overview.");

        Label welcome = label(
                "Make every seminar count.",
                25, INK, true
        );

        Label welcomeSub = label(
                "Manage students, speakers, registrations and events from one place.",
                13, MUTED, false
        );

        VBox welcomeBox = new VBox(10, welcome, welcomeSub);
        welcomeBox.setPadding(new Insets(25));
        welcomeBox.setStyle(
                "-fx-background-color: linear-gradient(to right, #E8ECFF, #FBE8F1);" +
                "-fx-background-radius: 18;"
        );

        HBox stats = new HBox(16);
        stats.setAlignment(Pos.CENTER);

        stats.getChildren().addAll(
                statCard("Total Students", getTotalStudents(), "Registered students", BLUE, "♙"),
                statCard("Seminars", "3", "Available seminars", PURPLE, "▤"),
                statCard("Registrations", "6", "Recorded registrations", PINK, "✓"),
                statCard("Upcoming", "3", "Scheduled seminars", MELON, "◷")
        );

        for (javafx.scene.Node node : stats.getChildren()) {
            HBox.setHgrow(node, Priority.ALWAYS);
        }

        HBox mainRow = new HBox(20);
        VBox.setVgrow(mainRow, Priority.ALWAYS);

        VBox upcoming = panel("Upcoming Seminars", "Your next college events");

        upcoming.getChildren().addAll(
                seminarRow("AI Trends", "10 Nov 2026  •  10:00 AM",
                        "Hall A", BLUE),
                seminarRow("Cloud Computing", "12 Nov 2026  •  11:00 AM",
                        "Hall B", PURPLE),
                seminarRow("Career Planning", "15 Nov 2026  •  02:00 PM",
                        "Hall C", PINK)
        );

        VBox schedule = createSchedulePanel();

        HBox.setHgrow(upcoming, Priority.ALWAYS);
        upcoming.setPrefWidth(600);
        schedule.setPrefWidth(330);
        schedule.setMinWidth(300);

        mainRow.getChildren().addAll(upcoming, schedule);

        VBox quickActions = panel("Quick Actions", "Jump straight into your work");

        HBox actionRow = new HBox(12);
        Button addStudent = actionButton("+  Add Student", BLUE);
        Button viewStudents = actionButton("View Students", NAVY);
        Button viewSeminars = actionButton("View Seminars", "#9A7BEA");

        addStudent.setOnAction(e -> showStudentForm(null));
        viewStudents.setOnAction(e -> showStudents());
        viewSeminars.setOnAction(e ->
                showSeminars());

        actionRow.getChildren().addAll(addStudent, viewStudents, viewSeminars);
        quickActions.getChildren().add(actionRow);

        content.getChildren().addAll(
                welcomeBox, stats, mainRow, quickActions
        );
    }

    private VBox statCard(
            String title, String value, String subtitle,
            String accent, String symbol
    ) {
        VBox card = new VBox(11);
        card.setPadding(new Insets(19));
        card.setMinHeight(145);
        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 17;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 17;"
        );

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER_LEFT);

        Label name = label(title, 12, MUTED, false);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label icon = new Label(symbol);
        icon.setMinSize(34, 34);
        icon.setAlignment(Pos.CENTER);
        icon.setStyle(
                "-fx-background-color: " + accent + ";" +
                "-fx-background-radius: 10;" +
                "-fx-font-size: 16px;"
        );

        top.getChildren().addAll(name, spacer, icon);

        Label number = label(value, 27, INK, true);
        Label note = label(subtitle, 10, MUTED, false);

        card.getChildren().addAll(top, number, note);
        return card;
    }

    private HBox seminarRow(
            String title, String date, String venue, String accent
    ) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 0, 12, 0));
        row.setStyle(
                "-fx-border-color: transparent transparent " +
                BORDER + " transparent;"
        );

        Region marker = new Region();
        marker.setMinSize(5, 47);
        marker.setMaxWidth(5);
        marker.setStyle(
                "-fx-background-color: " + accent + ";" +
                "-fx-background-radius: 5;"
        );

        VBox details = new VBox(6);
        details.getChildren().addAll(
                label(title, 13, INK, true),
                label(date, 11, MUTED, false),
                label(venue, 10, MUTED, false)
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label tag = new Label("UPCOMING");
        tag.setPadding(new Insets(6, 8, 6, 8));
        tag.setStyle(
                "-fx-background-color: #F0F2FF;" +
                "-fx-text-fill: #6479D9;" +
                "-fx-background-radius: 7;" +
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;"
        );

        row.getChildren().addAll(marker, details, spacer, tag);
        return row;
    }

    // ---------- MINI CALENDAR ----------

    private VBox createSchedulePanel() {
        VBox box = panel("My Schedule", "November 2026");

        YearMonth month = YearMonth.of(2026, 11);

        HBox monthHeader = new HBox();
        monthHeader.setAlignment(Pos.CENTER);

        Button previous = new Button("‹");
        Button next = new Button("›");

        Label monthLabel = label("November 2026", 13, INK, true);

        monthHeader.getChildren().addAll(previous, monthLabel, next);

        previous.setOnAction(e ->
                monthLabel.setText(month.minusMonths(1)
                        .format(DateTimeFormatter.ofPattern("MMMM yyyy"))));

        next.setOnAction(e ->
                monthLabel.setText(month.plusMonths(1)
                        .format(DateTimeFormatter.ofPattern("MMMM yyyy"))));

        GridPane calendar = new GridPane();
        calendar.setHgap(4);
        calendar.setVgap(7);
        calendar.setAlignment(Pos.CENTER);

        String[] days = {"M", "T", "W", "T", "F", "S", "S"};

        for (int col = 0; col < 7; col++) {
            Label day = label(days[col], 10, MUTED, true);
            day.setAlignment(Pos.CENTER);
            day.setMaxWidth(Double.MAX_VALUE);
            calendar.add(day, col, 0);
        }

        LocalDate first = month.atDay(1);
        int startColumn = first.getDayOfWeek().getValue() - 1;
        int daysInMonth = month.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            int index = startColumn + day - 1;
            int col = index % 7;
            int row = index / 7 + 1;

            Label date = new Label(String.valueOf(day));
            date.setAlignment(Pos.CENTER);
            date.setMinSize(31, 31);
            date.setMaxSize(31, 31);

            boolean eventDay = day == 10 || day == 12 || day == 15;

            String background = eventDay ? BLUE : "transparent";
            String foreground = eventDay ? "white" : INK;

            date.setStyle(
                    "-fx-background-color: " + background + ";" +
                    "-fx-text-fill: " + foreground + ";" +
                    "-fx-background-radius: 9;" +
                    "-fx-font-size: 11px;"
            );

            calendar.add(date, col, row);
        }

        Separator separator = new Separator();

        box.getChildren().addAll(monthHeader, calendar, separator,
                miniEvent("10 NOV", "AI Trends", BLUE),
                miniEvent("12 NOV", "Cloud Computing", PURPLE),
                miniEvent("15 NOV", "Career Planning", PINK));

        return box;
    }

    private HBox miniEvent(String date, String title, String accent) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label dateLabel = new Label(date);
        dateLabel.setMinWidth(54);
        dateLabel.setStyle(
                "-fx-text-fill: " + accent + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
        );

        Label titleLabel = label(title, 11, INK, true);
        row.getChildren().addAll(dateLabel, titleLabel);

        return row;
    }

    // ---------- STUDENTS PAGE ----------

    private void showStudents() {
        setPage("Students", "Manage registered students and their information.");

        HBox toolbar = new HBox(12);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        TextField searchField = new TextField();
        searchField.setPromptText("Search by ID, name, email or department...");
        searchField.setPrefWidth(390);
        searchField.setPrefHeight(40);

        Button refreshButton = actionButton("Refresh", NAVY);
        Button addButton = actionButton("+  Add Student", BLUE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        toolbar.getChildren().addAll(searchField, refreshButton, spacer, addButton);

        TableView<Student> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No students found."));
        table.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 12;"
        );

        TableColumn<Student, String> idCol = new TableColumn<>("Student ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Student, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn<Student, Integer> yearCol = new TableColumn<>("Academic Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("academicYear"));

        TableColumn<Student, Student> actionCol = new TableColumn<>("Actions");

        actionCol.setCellFactory(column -> new TableCell<Student, Student>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox buttons = new HBox(7, editButton, deleteButton);

            {
                editButton.setStyle(
                        "-fx-background-color: #E8ECFF;" +
                        "-fx-text-fill: #5269CF;" +
                        "-fx-background-radius: 7;" +
                        "-fx-cursor: hand;"
                );

                deleteButton.setStyle(
                        "-fx-background-color: #FFE8ED;" +
                        "-fx-text-fill: #D84D70;" +
                        "-fx-background-radius: 7;" +
                        "-fx-cursor: hand;"
                );

                editButton.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    showStudentForm(student);
                });

                deleteButton.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    deleteStudent(student);
                });
            }

            @Override
            protected void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                setGraphic(empty || student == null ? null : buttons);
            }
        });

        table.getColumns().addAll(
                idCol, nameCol, emailCol, deptCol, yearCol, actionCol
        );

        VBox tablePanel = panel("Student Directory", "Student records from the database");
        tablePanel.setPadding(new Insets(16));
        VBox.setVgrow(table, Priority.ALWAYS);
        tablePanel.getChildren().add(table);

        filteredStudents = new FilteredList<>(studentData, student -> true);
        table.setItems(filteredStudents);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = newValue == null ? "" : newValue.trim().toLowerCase();

            filteredStudents.setPredicate(student -> {
                if (query.isEmpty()) {
                    return true;
                }

                return safe(student.getStudentId()).contains(query)
                        || safe(student.getName()).contains(query)
                        || safe(student.getEmail()).contains(query)
                        || safe(student.getDepartment()).contains(query);
            });
        });

        addButton.setOnAction(e -> showStudentForm(null));

        refreshButton.setOnAction(e -> {
            loadStudents();
            searchField.clear();
        });

        content.getChildren().addAll(toolbar, tablePanel);
        VBox.setVgrow(tablePanel, Priority.ALWAYS);

        loadStudents();
    }

    private String safe(String value) {
        return value == null ? "" : value.toLowerCase();
    }

    private void loadStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            studentData.setAll(students);
        } catch (SQLException ex) {
            showAlert(
                    Alert.AlertType.ERROR,
                    "Database Error",
                    "Could not load students.\n" + ex.getMessage()
            );
        }
    }

    // ---------- ADD / EDIT STUDENT ----------

    private void showStudentForm(Student existingStudent) {
        boolean editing = existingStudent != null;

        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle(editing ? "Edit Student" : "Add Student");
        dialog.setHeaderText(
                editing ? "Update student information" : "Enter new student details"
        );

        ButtonType saveType = new ButtonType(
                editing ? "Save Changes" : "Add Student",
                ButtonBar.ButtonData.OK_DONE
        );

        dialog.getDialogPane().getButtonTypes().addAll(
                saveType, ButtonType.CANCEL
        );

        TextField idField = new TextField();
        idField.setPromptText("e.g. S106");

        TextField nameField = new TextField();
        nameField.setPromptText("Full name");

        TextField emailField = new TextField();
        emailField.setPromptText("student@example.com");

        TextField departmentField = new TextField();
        departmentField.setPromptText("e.g. MCA, CSE, ECE");

        Spinner<Integer> yearSpinner =
                new Spinner<>(1, 6, 1);

        yearSpinner.setEditable(true);

        if (editing) {
            idField.setText(existingStudent.getStudentId());
            idField.setDisable(true);
            nameField.setText(existingStudent.getName());
            emailField.setText(existingStudent.getEmail());
            departmentField.setText(existingStudent.getDepartment());
            yearSpinner.getValueFactory().setValue(
                    existingStudent.getAcademicYear()
            );
        }

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(13);
        grid.setPadding(new Insets(18, 10, 10, 10));

        grid.add(label("Student ID", 12, INK, true), 0, 0);
        grid.add(idField, 1, 0);

        grid.add(label("Full Name", 12, INK, true), 0, 1);
        grid.add(nameField, 1, 1);

        grid.add(label("Email", 12, INK, true), 0, 2);
        grid.add(emailField, 1, 2);

        grid.add(label("Department", 12, INK, true), 0, 3);
        grid.add(departmentField, 1, 3);

        grid.add(label("Academic Year", 12, INK, true), 0, 4);
        grid.add(yearSpinner, 1, 4);

        ColumnConstraints firstColumn = new ColumnConstraints();
        firstColumn.setMinWidth(105);

        ColumnConstraints secondColumn = new ColumnConstraints();
        secondColumn.setHgrow(Priority.ALWAYS);
        secondColumn.setFillWidth(true);

        grid.getColumnConstraints().addAll(firstColumn, secondColumn);

        idField.setMaxWidth(Double.MAX_VALUE);
        nameField.setMaxWidth(Double.MAX_VALUE);
        emailField.setMaxWidth(Double.MAX_VALUE);
        departmentField.setMaxWidth(Double.MAX_VALUE);
        yearSpinner.setMaxWidth(Double.MAX_VALUE);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().setPrefWidth(470);

        Button saveButton =
                (Button) dialog.getDialogPane().lookupButton(saveType);

        saveButton.addEventFilter(
                javafx.event.ActionEvent.ACTION, event -> {
                    if (idField.getText().trim().isEmpty()
                            || nameField.getText().trim().isEmpty()
                            || emailField.getText().trim().isEmpty()
                            || departmentField.getText().trim().isEmpty()) {

                        showAlert(
                                Alert.AlertType.WARNING,
                                "Missing Information",
                                "Please fill in all the fields."
                        );
                        event.consume();
                        return;
                    }

                    if (!emailField.getText().trim()
                            .matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                        showAlert(
                                Alert.AlertType.WARNING,
                                "Invalid Email",
                                "Please enter a valid email address."
                        );
                        event.consume();
                    }
                }
        );

        dialog.setResultConverter(button -> {
            if (button == saveType) {
                return new Student(
                        idField.getText().trim(),
                        nameField.getText().trim(),
                        emailField.getText().trim(),
                        departmentField.getText().trim(),
                        yearSpinner.getValue()
                );
            }
            return null;
        });

        Optional<Student> result = dialog.showAndWait();

        if (result.isPresent()) {
            Student student = result.get();

            try {
                boolean success;

                if (editing) {
                    success = studentService.updateStudent(student);
                } else {
                    success = studentService.addStudent(student);
                }

                if (success) {
                    showAlert(
                            Alert.AlertType.INFORMATION,
                            "Success",
                            editing
                                    ? "Student updated successfully."
                                    : "Student added successfully."
                    );
                    loadStudents();
                } else {
                    showAlert(
                            Alert.AlertType.WARNING,
                            "Not Saved",
                            "The student record was not saved. Check the ID and database constraints."
                    );
                }

            } catch (SQLException ex) {
                showAlert(
                        Alert.AlertType.ERROR,
                        "Database Error",
                        "Could not save the student.\n" + ex.getMessage()
                );
            } catch (IllegalArgumentException ex) {
                showAlert(
                        Alert.AlertType.WARNING,
                        "Invalid Student Details",
                        ex.getMessage()
                );
            }
        }
    }

    // ---------- DELETE STUDENT ----------

    private void deleteStudent(Student student) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Student");
        confirm.setHeaderText("Delete " + student.getName() + "?");
        confirm.setContentText(
                "This action may fail if the student has existing registrations."
        );

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean deleted =
                        studentService.deleteStudent(student.getStudentId());

                if (deleted) {
                    showAlert(
                            Alert.AlertType.INFORMATION,
                            "Deleted",
                            "Student record deleted successfully."
                    );
                    loadStudents();
                } else {
                    showAlert(
                            Alert.AlertType.WARNING,
                            "Not Deleted",
                            "The student record was not deleted."
                    );
                }

            } catch (SQLException ex) {
                showAlert(
                        Alert.AlertType.ERROR,
                        "Delete Failed",
                        "Could not delete this student. They may have registrations.\n"
                                + ex.getMessage()
                );
            } catch (IllegalArgumentException ex) {
                showAlert(
                        Alert.AlertType.WARNING,
                        "Invalid Student ID",
                        ex.getMessage()
                );
            }
        }
    }


    // ---------- SPEAKERS MODULE ----------

    private void showSpeakers() {
        setPage("Speakers", "Manage seminar speakers and their details.");

        HBox toolbar = new HBox(12);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        TextField searchField = new TextField();
        searchField.setPromptText("Search ID, name, email, organization or expertise...");
        searchField.setPrefWidth(420);
        searchField.setPrefHeight(40);

        Button refreshButton = actionButton("Refresh", NAVY);
        Button addButton = actionButton("+ Add Speaker", BLUE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolbar.getChildren().addAll(searchField, refreshButton, spacer, addButton);

        TableView<Speaker> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Speaker, String> idCol = new TableColumn<>("Speaker ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("speakerId"));

        TableColumn<Speaker, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Speaker, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Speaker, String> orgCol = new TableColumn<>("Organization");
        orgCol.setCellValueFactory(new PropertyValueFactory<>("organization"));

        TableColumn<Speaker, String> expertiseCol = new TableColumn<>("Expertise");
        expertiseCol.setCellValueFactory(new PropertyValueFactory<>("expertise"));

        TableColumn<Speaker, Speaker> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<Speaker, Speaker>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox buttons = new HBox(7, editButton, deleteButton);

            {
                editButton.setStyle(
                    "-fx-background-color: #E8ECFF;" +
                    "-fx-text-fill: #5269CF;" +
                    "-fx-background-radius: 7;" +
                    "-fx-cursor: hand;"
                );
                deleteButton.setStyle(
                    "-fx-background-color: #FFE8ED;" +
                    "-fx-text-fill: #D84D70;" +
                    "-fx-background-radius: 7;" +
                    "-fx-background-radius: 7;" +
                    "-fx-cursor: hand;"
                );

                editButton.setOnAction(e -> {
                    Speaker speaker = getTableView().getItems().get(getIndex());
                    showSpeakerForm(speaker);
                });

                deleteButton.setOnAction(e -> {
                    Speaker speaker = getTableView().getItems().get(getIndex());
                    deleteSpeaker(speaker);
                });
            }

            @Override
            protected void updateItem(Speaker speaker, boolean empty) {
                super.updateItem(speaker, empty);
                setGraphic(empty || speaker == null ? null : buttons);
            }
        });

        table.getColumns().addAll(
            idCol, nameCol, emailCol, orgCol, expertiseCol, actionsCol
        );

        ObservableList<Speaker> data = FXCollections.observableArrayList();
        FilteredList<Speaker> filtered = new FilteredList<>(data, speaker -> true);
        table.setItems(filtered);

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            String query = newValue == null ? "" : newValue.trim().toLowerCase();

            filtered.setPredicate(speaker -> query.isEmpty()
                || safe(speaker.getSpeakerId()).contains(query)
                || safe(speaker.getName()).contains(query)
                || safe(speaker.getEmail()).contains(query)
                || safe(speaker.getOrganization()).contains(query)
                || safe(speaker.getExpertise()).contains(query));
        });

        VBox tablePanel = panel("Speaker Directory", "Speaker records from the database");
        tablePanel.setPadding(new Insets(16));
        VBox.setVgrow(table, Priority.ALWAYS);
        tablePanel.getChildren().add(table);
        VBox.setVgrow(tablePanel, Priority.ALWAYS);

        addButton.setOnAction(e -> showSpeakerForm(null));
        refreshButton.setOnAction(e -> {
            searchField.clear();
            loadSpeakers(data);
        });

        content.getChildren().addAll(toolbar, tablePanel);
        loadSpeakers(data);
    }

    private void loadSpeakers(ObservableList<Speaker> data) {
        try {
            data.setAll(speakerService.getAllSpeakers());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error",
                "Could not load speakers.\\n" + ex.getMessage());
        }
    }

    private void showSpeakerForm(Speaker existing) {
        boolean editing = existing != null;

        Dialog<Speaker> dialog = new Dialog<>();
        dialog.setTitle(editing ? "Edit Speaker" : "Add Speaker");
        dialog.setHeaderText(editing
            ? "Update speaker information" : "Enter speaker details");

        ButtonType saveType = new ButtonType(
            editing ? "Save Changes" : "Add Speaker",
            ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveType, ButtonType.CANCEL);

        TextField idField = new TextField();
        idField.setPromptText("e.g. SP104");

        TextField nameField = new TextField();
        nameField.setPromptText("Full name");

        TextField emailField = new TextField();
        emailField.setPromptText("speaker@example.com");

        TextField orgField = new TextField();
        orgField.setPromptText("Organization");

        TextField expertiseField = new TextField();
        expertiseField.setPromptText("Area of expertise");

        if (editing) {
            idField.setText(existing.getSpeakerId());
            idField.setDisable(true);
            nameField.setText(existing.getName());
            emailField.setText(existing.getEmail());
            orgField.setText(existing.getOrganization());
            expertiseField.setText(existing.getExpertise());
        }

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(18, 10, 10, 10));

        String[] labels = {"Speaker ID", "Name", "Email", "Organization", "Expertise"};
        TextField[] fields = {idField, nameField, emailField, orgField, expertiseField};

        for (int i = 0; i < labels.length; i++) {
            grid.add(label(labels[i], 12, INK, true), 0, i);
            grid.add(fields[i], 1, i);
            fields[i].setMaxWidth(Double.MAX_VALUE);
        }

        ColumnConstraints labelColumn = new ColumnConstraints();
        labelColumn.setMinWidth(100);
        ColumnConstraints inputColumn = new ColumnConstraints();
        inputColumn.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(labelColumn, inputColumn);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().setPrefWidth(480);

        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveType);
        saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            for (TextField field : fields) {
                if (field.getText() == null || field.getText().trim().isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Missing Information",
                        "Please fill in all speaker fields.");
                    event.consume();
                    return;
                }
            }

            if (!emailField.getText().trim()
                    .matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                showAlert(Alert.AlertType.WARNING, "Invalid Email",
                    "Please enter a valid email address.");
                event.consume();
            }
        });

        dialog.setResultConverter(button -> {
            if (button == saveType) {
                return new Speaker(
                    idField.getText().trim(),
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    orgField.getText().trim(),
                    expertiseField.getText().trim());
            }
            return null;
        });

        Optional<Speaker> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }

        try {
            Speaker speaker = result.get();
            boolean success = editing
                ? speakerService.updateSpeaker(speaker)
                : speakerService.addSpeaker(speaker);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                    editing ? "Speaker updated successfully."
                            : "Speaker added successfully.");
                showSpeakers();
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Saved",
                    "No speaker record was changed.");
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error",
                "Could not save speaker. Check duplicate ID/email or database constraints.\\n"
                    + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.WARNING, "Invalid Speaker Details",
                ex.getMessage());
        }
    }

    private void deleteSpeaker(Speaker speaker) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Speaker");
        confirm.setHeaderText("Delete " + speaker.getName() + "?");
        confirm.setContentText(
            "A speaker linked to existing seminars may not be deletable.");
        Optional<ButtonType> result = confirm.showAndWait();

        if (!result.isPresent() || result.get() != ButtonType.OK) {
            return;
        }

        try {
            boolean deleted = speakerService.deleteSpeaker(speaker.getSpeakerId());
            if (deleted) {
                showAlert(Alert.AlertType.INFORMATION, "Deleted",
                    "Speaker deleted successfully.");
                showSpeakers();
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Deleted",
                    "Speaker record was not deleted.");
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Delete Failed",
                "Could not delete this speaker. They may be linked to a seminar.\\n"
                    + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.WARNING, "Invalid Speaker ID",
                ex.getMessage());
        }
    }


    // ---------- SEMINAR MANAGEMENT ----------

    private void showSeminars() {
        setPage("Seminar Management", "Create and manage college seminars.");

        TextField search = new TextField();
        search.setPromptText("Search seminars by ID, title, topic, category or venue");
        search.setPrefWidth(320);

        Button searchBtn = actionButton("Search", NAVY);
        Button refreshBtn = actionButton("Refresh", BLUE);
        Button addBtn = actionButton("+ Add Seminar", "#9A7BEA");
        Button editBtn = actionButton("Edit Selected", NAVY);
        Button deleteBtn = actionButton("Delete Selected", "#D9534F");

        TableView<Seminar> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Seminar, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("seminarId"));

        TableColumn<Seminar, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Seminar, String> topicCol = new TableColumn<>("Topic");
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));

        TableColumn<Seminar, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Seminar, String> speakerCol = new TableColumn<>("Speaker ID");
        speakerCol.setCellValueFactory(new PropertyValueFactory<>("speakerId"));

        TableColumn<Seminar, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("seminarDate"));

        TableColumn<Seminar, LocalTime> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("seminarTime"));

        TableColumn<Seminar, String> venueCol = new TableColumn<>("Venue");
        venueCol.setCellValueFactory(new PropertyValueFactory<>("venue"));

        TableColumn<Seminar, Integer> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        table.getColumns().addAll(idCol, titleCol, topicCol, categoryCol,
                speakerCol, dateCol, timeCol, venueCol, capacityCol);

        Runnable reload = () -> {
            try {
                table.getItems().setAll(seminarService.getAllSeminars());
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database Error",
                        "Could not load seminars: " + ex.getMessage());
            }
        };

        Runnable filter = () -> {
            String q = search.getText() == null
                    ? "" : search.getText().trim().toLowerCase();
            try {
                List<Seminar> all = seminarService.getAllSeminars();
                table.getItems().setAll(all.stream().filter(item ->
                        q.isEmpty()
                        || safe(item.getSeminarId()).toLowerCase().contains(q)
                        || safe(item.getTitle()).toLowerCase().contains(q)
                        || safe(item.getTopic()).toLowerCase().contains(q)
                        || safe(item.getCategory()).toLowerCase().contains(q)
                        || safe(item.getSpeakerId()).toLowerCase().contains(q)
                        || safe(item.getVenue()).toLowerCase().contains(q)
                        || String.valueOf(item.getSeminarDate()).contains(q)
                ).toList());
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Search Failed", ex.getMessage());
            }
        };

        searchBtn.setOnAction(e -> filter.run());
        search.setOnAction(e -> filter.run());
        refreshBtn.setOnAction(e -> {
            search.clear();
            reload.run();
        });
        addBtn.setOnAction(e -> showSeminarForm(null));
        editBtn.setOnAction(e -> {
            Seminar selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.WARNING, "Select Seminar",
                        "Select a seminar to edit.");
            } else {
                showSeminarForm(selected);
            }
        });
        deleteBtn.setOnAction(e -> {
            Seminar selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.WARNING, "Select Seminar",
                        "Select a seminar to delete.");
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete Seminar");
            confirm.setHeaderText("Delete " + selected.getTitle() + "?");
            confirm.setContentText("This action cannot be undone.");
            Optional<ButtonType> answer = confirm.showAndWait();
            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                try {
                    if (seminarService.deleteSeminar(selected.getSeminarId())) {
                        reload.run();
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Not Deleted",
                                "No seminar record was deleted.");
                    }
                } catch (SQLException ex) {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed",
                            "The seminar may have registrations linked to it.\n"
                                    + ex.getMessage());
                }
            }
        });

        HBox searchRow = new HBox(10, search, searchBtn, refreshBtn, addBtn);
        searchRow.setAlignment(Pos.CENTER_LEFT);
        HBox actions = new HBox(10, editBtn, deleteBtn);

        VBox card = panel("All Seminars", "Select a seminar to edit or delete");
        VBox.setVgrow(table, Priority.ALWAYS);
        card.getChildren().addAll(searchRow, table, actions);
        VBox.setVgrow(card, Priority.ALWAYS);
        content.getChildren().add(card);

        reload.run();
    }

    private void showSeminarForm(Seminar existing) {
        Dialog<Seminar> dialog = new Dialog<>();
        dialog.setTitle(existing == null ? "Add Seminar" : "Edit Seminar");
        dialog.setHeaderText("Enter seminar details");

        ButtonType saveType = new ButtonType(
                existing == null ? "Add" : "Save",
                ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveType, ButtonType.CANCEL);

        TextField id = new TextField(existing == null ? "" : existing.getSeminarId());
        id.setPromptText("e.g. SEM104");
        id.setDisable(existing != null);

        TextField title = new TextField(existing == null ? "" : existing.getTitle());
        TextField topic = new TextField(existing == null ? "" : existing.getTopic());
        TextField category = new TextField(existing == null ? "" : existing.getCategory());
        TextField time = new TextField(existing == null ? "10:00" :
                existing.getSeminarTime().toString());
        time.setPromptText("HH:mm, e.g. 14:30");
        TextField venue = new TextField(existing == null ? "" : existing.getVenue());

        ComboBox<Speaker> speaker = new ComboBox<>();
        speaker.setMaxWidth(Double.MAX_VALUE);
        speaker.setPromptText("Choose speaker");

        try {
            speaker.getItems().setAll(speakerService.getAllSpeakers());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error",
                    "Could not load speakers: " + ex.getMessage());
            return;
        }

        javafx.util.StringConverter<Speaker> converter =
                new javafx.util.StringConverter<>() {
            @Override
            public String toString(Speaker value) {
                return value == null ? "" :
                        value.getSpeakerId() + " - " + value.getName();
            }
            @Override
            public Speaker fromString(String value) {
                return null;
            }
        };
        speaker.setConverter(converter);

        if (existing != null) {
            speaker.getItems().stream()
                    .filter(sp -> sp.getSpeakerId().equals(existing.getSpeakerId()))
                    .findFirst().ifPresent(speaker::setValue);
        } else if (!speaker.getItems().isEmpty()) {
            speaker.getSelectionModel().selectFirst();
        }

        DatePicker date = new DatePicker(existing == null
                ? LocalDate.now().plusDays(1) : existing.getSeminarDate());
        Spinner<Integer> capacity = new Spinner<>(1, 1000,
                existing == null ? 30 : existing.getCapacity());
        capacity.setEditable(true);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(9);
        grid.setPadding(new Insets(10));

        String[] labels = {"Seminar ID", "Title", "Topic", "Category",
                "Speaker", "Date", "Time (HH:mm)", "Venue", "Capacity"};
        Node[] fields = {id, title, topic, category, speaker, date, time, venue, capacity};
        for (int i = 0; i < labels.length; i++) {
            grid.add(new Label(labels[i]), 0, i);
            grid.add(fields[i], 1, i);
            if (fields[i] instanceof javafx.scene.control.Control control) {
                control.setMaxWidth(Double.MAX_VALUE);
            }
        }
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().setPrefWidth(480);

        Node saveButton = dialog.getDialogPane().lookupButton(saveType);
        saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            String error = null;
            if (id.getText().isBlank() || title.getText().isBlank()
                    || topic.getText().isBlank() || category.getText().isBlank()
                    || venue.getText().isBlank()) {
                error = "Please fill in all fields.";
            } else if (speaker.getValue() == null) {
                error = "Please select a speaker.";
            } else if (date.getValue() == null
                    || date.getValue().isBefore(LocalDate.now())) {
                error = "Seminar date cannot be in the past.";
            } else {
                try {
                    LocalTime.parse(time.getText().trim());
                } catch (Exception ex) {
                    error = "Enter a valid time such as 14:30.";
                }
            }
            if (error != null) {
                event.consume();
                showAlert(Alert.AlertType.WARNING, "Invalid Details", error);
            }
        });

        dialog.setResultConverter(button -> {
            if (button != saveType) return null;
            return new Seminar(id.getText().trim(), title.getText().trim(),
                    topic.getText().trim(), category.getText().trim(),
                    speaker.getValue().getSpeakerId(), date.getValue(),
                    LocalTime.parse(time.getText().trim()), venue.getText().trim(),
                    capacity.getValue());
        });

        Optional<Seminar> result = dialog.showAndWait();
        if (result.isEmpty()) return;

        try {
            boolean saved = existing == null
                    ? seminarService.addSeminar(result.get())
                    : seminarService.updateSeminar(result.get());
            if (saved) {
                showSeminars();
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        existing == null ? "Seminar added successfully."
                                : "Seminar updated successfully.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Saved",
                        "No seminar record was changed.");
            }
        } catch (SQLException | IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Save Failed", ex.getMessage());
        }
    }


    // ---------- REGISTRATION MANAGEMENT ----------

    private void showRegistrations() {
        setPage("Registrations", "View and manage seminar registrations.");

        TextField search = new TextField();
        search.setPromptText("Search by registration ID, student, seminar or status");
        search.setPrefWidth(340);

        Button searchBtn = actionButton("Search", NAVY);
        Button refreshBtn = actionButton("Refresh", BLUE);
        Button addBtn = actionButton("+ Add Registration", "#9A7BEA");
        Button cancelBtn = actionButton("Cancel Registration", "#D9534F");

        TableView<Registration> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Registration, String> idCol = new TableColumn<>("Registration ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("registrationId"));

        TableColumn<Registration, String> studentCol = new TableColumn<>("Student ID");
        studentCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn<Registration, String> seminarCol = new TableColumn<>("Seminar ID");
        seminarCol.setCellValueFactory(new PropertyValueFactory<>("seminarId"));

        TableColumn<Registration, LocalDate> dateCol = new TableColumn<>("Registration Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        TableColumn<Registration, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(idCol, studentCol, seminarCol, dateCol, statusCol);

        Runnable reload = () -> {
            try {
                table.getItems().setAll(registrationService.getAllRegistrations());
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database Error",
                        "Could not load registrations: " + ex.getMessage());
            }
        };

        Runnable filter = () -> {
            String q = search.getText() == null
                    ? "" : search.getText().trim().toLowerCase();
            try {
                List<Registration> all = registrationService.getAllRegistrations();
                table.getItems().setAll(all.stream().filter(r ->
                        q.isEmpty()
                        || safe(r.getRegistrationId()).toLowerCase().contains(q)
                        || safe(r.getStudentId()).toLowerCase().contains(q)
                        || safe(r.getSeminarId()).toLowerCase().contains(q)
                        || safe(r.getStatus()).toLowerCase().contains(q)
                        || String.valueOf(r.getRegistrationDate()).contains(q)
                ).toList());
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Search Failed", ex.getMessage());
            }
        };

        searchBtn.setOnAction(e -> filter.run());
        search.setOnAction(e -> filter.run());
        refreshBtn.setOnAction(e -> {
            search.clear();
            reload.run();
        });
        addBtn.setOnAction(e -> showRegistrationForm());

        cancelBtn.setOnAction(e -> {
            Registration selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.WARNING, "Select Registration",
                        "Select a registration to cancel.");
                return;
            }
            if ("CANCELLED".equalsIgnoreCase(selected.getStatus())) {
                showAlert(Alert.AlertType.INFORMATION, "Already Cancelled",
                        "This registration is already cancelled.");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Cancel Registration");
            confirm.setHeaderText("Cancel " + selected.getRegistrationId() + "?");
            confirm.setContentText("The registration record will be retained as CANCELLED.");
            Optional<ButtonType> answer = confirm.showAndWait();

            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                Registration cancelled = new Registration(
                        selected.getRegistrationId(),
                        selected.getStudentId(),
                        selected.getSeminarId(),
                        selected.getRegistrationDate(),
                        "CANCELLED");

                try {
                    if (registrationService.updateRegistration(cancelled)) {
                        reload.run();
                        showAlert(Alert.AlertType.INFORMATION, "Cancelled",
                                "Registration marked as CANCELLED.");
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Not Updated",
                                "The registration status was not changed.");
                    }
                } catch (SQLException | IllegalArgumentException ex) {
                    showAlert(Alert.AlertType.ERROR, "Cancellation Failed",
                            ex.getMessage());
                }
            }
        });

        HBox searchRow = new HBox(10, search, searchBtn, refreshBtn, addBtn);
        searchRow.setAlignment(Pos.CENTER_LEFT);
        HBox actions = new HBox(10, cancelBtn);

        VBox card = panel("All Registrations",
                "Select a registration to cancel it without deleting its record");
        VBox.setVgrow(table, Priority.ALWAYS);
        card.getChildren().addAll(searchRow, table, actions);
        VBox.setVgrow(card, Priority.ALWAYS);
        content.getChildren().add(card);

        reload.run();
    }

    private void showRegistrationForm() {
        Dialog<Registration> dialog = new Dialog<>();
        dialog.setTitle("Add Registration");
        dialog.setHeaderText("Register a student for a seminar");

        ButtonType saveType = new ButtonType(
                "Register", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveType, ButtonType.CANCEL);

        TextField idField = new TextField();
        idField.setPromptText("e.g. REG107");

        ComboBox<Student> studentBox = new ComboBox<>();
        studentBox.setPromptText("Choose student");
        studentBox.setMaxWidth(Double.MAX_VALUE);

        ComboBox<Seminar> seminarBox = new ComboBox<>();
        seminarBox.setPromptText("Choose seminar");
        seminarBox.setMaxWidth(Double.MAX_VALUE);

        try {
            studentBox.getItems().setAll(studentService.getAllStudents());
            seminarBox.getItems().setAll(seminarService.getAllSeminars());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error",
                    "Could not load students or seminars: " + ex.getMessage());
            return;
        }

        studentBox.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Student value) {
                return value == null ? "" :
                        value.getStudentId() + " - " + value.getName();
            }
            @Override
            public Student fromString(String value) {
                return null;
            }
        });

        seminarBox.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Seminar value) {
                return value == null ? "" :
                        value.getSeminarId() + " - " + value.getTitle();
            }
            @Override
            public Seminar fromString(String value) {
                return null;
            }
        });

        if (!studentBox.getItems().isEmpty()) {
            studentBox.getSelectionModel().selectFirst();
        }
        if (!seminarBox.getItems().isEmpty()) {
            seminarBox.getSelectionModel().selectFirst();
        }

        Label dateValue = new Label(LocalDate.now().toString());

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        grid.add(new Label("Registration ID"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Student"), 0, 1);
        grid.add(studentBox, 1, 1);
        grid.add(new Label("Seminar"), 0, 2);
        grid.add(seminarBox, 1, 2);
        grid.add(new Label("Registration Date"), 0, 3);
        grid.add(dateValue, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().setPrefWidth(480);

        Node saveButton = dialog.getDialogPane().lookupButton(saveType);
        saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            String error = null;
            if (idField.getText().isBlank()) {
                error = "Registration ID is required.";
            } else if (studentBox.getValue() == null) {
                error = "Please select a student.";
            } else if (seminarBox.getValue() == null) {
                error = "Please select a seminar.";
            }

            if (error != null) {
                event.consume();
                showAlert(Alert.AlertType.WARNING, "Invalid Registration", error);
            }
        });

        dialog.setResultConverter(button -> {
            if (button != saveType) return null;
            return new Registration(
                    idField.getText().trim(),
                    studentBox.getValue().getStudentId(),
                    seminarBox.getValue().getSeminarId(),
                    LocalDate.now(),
                    "CONFIRMED");
        });

        Optional<Registration> result = dialog.showAndWait();
        if (result.isEmpty()) return;

        try {
            if (registrationService.addRegistration(result.get())) {
                showRegistrations();
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Registration added successfully.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Added",
                        "Registration was not added.");
            }
        } catch (SQLException | IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", ex.getMessage());
        }
    }


    // ---------- REPORTS & STATISTICS ----------

    private void showReports() {
        setPage("Reports & Statistics",
                "Explore confirmed registration statistics.");

        Button refreshBtn = actionButton("Refresh Reports", BLUE);
        Label departmentHeading = label(
                "Confirmed Registrations by Department", 16, NAVY, true);
        TableView<Map.Entry<String, Long>> departmentTable = createReportTable();

        Label categoryHeading = label(
                "Confirmed Registrations by Seminar Category", 16, NAVY, true);
        TableView<Map.Entry<String, Long>> categoryTable = createReportTable();

        Label combinedHeading = label(
                "Department + Category Breakdown", 16, NAVY, true);
        TableView<Map.Entry<String, Long>> combinedTable = createReportTable();

        Runnable loadReports = () -> {
            try {
                ReportService reportService = new ReportService();

                departmentTable.getItems().setAll(
                        reportService.getDepartmentStatistics().entrySet());
                categoryTable.getItems().setAll(
                        reportService.getCategoryStatistics().entrySet());
                combinedTable.getItems().setAll(
                        reportService.getCombinedStatistics().entrySet());

            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Reports Error",
                        "Could not load reports: " + ex.getMessage());
            }
        };

        refreshBtn.setOnAction(e -> loadReports.run());

        VBox departmentCard = panel("Department Statistics",
                "Number of confirmed registrations per department");
        departmentCard.getChildren().addAll(departmentHeading, departmentTable);

        VBox categoryCard = panel("Category Statistics",
                "Number of confirmed registrations per seminar category");
        categoryCard.getChildren().addAll(categoryHeading, categoryTable);

        VBox combinedCard = panel("Combined Statistics",
                "Confirmed registrations grouped by department and category");
        combinedCard.getChildren().addAll(combinedHeading, combinedTable);

        VBox reportsContent = new VBox(16,
                refreshBtn, departmentCard, categoryCard, combinedCard);
        reportsContent.setPadding(new Insets(5, 0, 10, 0));

        content.getChildren().add(reportsContent);
        loadReports.run();
    }

    private TableView<Map.Entry<String, Long>> createReportTable() {
        TableView<Map.Entry<String, Long>> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(180);
        table.setPlaceholder(new Label("No confirmed registration data available."));

        TableColumn<Map.Entry<String, Long>, String> groupCol =
                new TableColumn<>("Department / Category");
        groupCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getKey()));

        TableColumn<Map.Entry<String, Long>, Number> countCol =
                new TableColumn<>("Confirmed Registrations");
        countCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleLongProperty(
                        cell.getValue().getValue()));

        table.getColumns().addAll(groupCol, countCol);
        return table;
    }

    // ---------- OTHER MODULES ----------

    private void showModule(String title, String description, String moduleName) {
        setPage(title, description);

        VBox intro = panel(moduleName, "This section is ready for the next development step.");

        Label message = label(
                "The " + title + " screen will be connected to its database service here.",
                13, MUTED, false
        );

        Button backButton = actionButton("Back to Dashboard", BLUE);
        backButton.setOnAction(e -> showDashboard());

        intro.getChildren().addAll(message, backButton);

        content.getChildren().add(intro);
    }

    // ---------- ALERTS ----------

    private void showAlert(
            Alert.AlertType type, String title, String message
    ) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
