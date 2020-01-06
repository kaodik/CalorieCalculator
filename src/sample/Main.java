package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;


public class Main extends Application {
    Label lblEnterWeight, lblResults;
    TextField txtWeight;
    RadioButton radbtnFemale, radbtnMale, radbtnSedentary, radbtnModerately, radbtnActive,
            radbtnMaintenance, radbtnFatloss, radbtnLean;

    public static void main(String[] args) {launch(args);}


    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("ULTIMATE Calorie and Macro Calculator");
        stage.setMaxWidth(600);
        stage.setMaxHeight(1000);

        //setup for displaying the food img and putting it in a imgview.
        Image food = new Image("file:food.jpg");
        ImageView imgViewFood = new ImageView(food);

        imgViewFood.setFitWidth(550);
        imgViewFood.setPreserveRatio(true);

        HBox picBox = new HBox(imgViewFood);

        //**********Create titlePane for userinput in gridepane for padding*********
        lblEnterWeight = new Label("Enter Weight in lbs:");
        txtWeight = new TextField();

        //create gridPane
        GridPane wtGrid = new GridPane();

        //add to pane
        wtGrid.add(lblEnterWeight, 0, 0);
        wtGrid.add(txtWeight, 1,0);

        //set padding for controls
        wtGrid.setHgap(50);
        wtGrid.setAlignment(Pos.CENTER);

        //titlePane
        TitledPane enterWeightPane = new TitledPane("Step 1: Enter Weight:",wtGrid);
        enterWeightPane.setCollapsible(false);

        //***********Setting up Bodytype Titlepane and controls********
        radbtnFemale = new RadioButton("Female");
        radbtnMale = new RadioButton("Male");

        //Toggle group radbtn
        ToggleGroup togGrpBodyType = new ToggleGroup();
        radbtnFemale.setToggleGroup(togGrpBodyType);
        radbtnMale.setToggleGroup(togGrpBodyType);

        //positioning
        HBox hboxBodyType = new HBox(50,radbtnFemale, radbtnMale);
        hboxBodyType.setAlignment(Pos.CENTER);

        //titlePane
        TitledPane bodyTypePane = new TitledPane("Step 2: Choose Body Type:",hboxBodyType);
        bodyTypePane.setCollapsible(false);

        //*********Setting up pane for Active level*************
        radbtnSedentary = new RadioButton("Sedentary");
        radbtnModerately = new RadioButton("Moderately Active");
        radbtnActive = new RadioButton("Very Active");

        //Toggle Grp
        ToggleGroup toGrpActiveLevel = new ToggleGroup();
        radbtnActive.setToggleGroup(toGrpActiveLevel);
        radbtnModerately.setToggleGroup(toGrpActiveLevel);
        radbtnSedentary.setToggleGroup(toGrpActiveLevel);

        //positioning
        HBox hboxActiveLevel = new HBox(50, radbtnSedentary, radbtnModerately, radbtnActive);
        hboxActiveLevel.setAlignment(Pos.CENTER);

        //titlePane
        TitledPane activeLevelPane = new TitledPane("Step 3: Choose Activity Level:", hboxActiveLevel);
        activeLevelPane.setCollapsible(false);

        //*************Setting up pane for goals************
        radbtnMaintenance = new RadioButton("Maintenance");
        radbtnFatloss = new RadioButton("Fat Loss");
        radbtnLean = new RadioButton("Lean Gains");

        //Toggle Grp
        ToggleGroup toGrpGoal = new ToggleGroup();
        radbtnMaintenance.setToggleGroup(toGrpGoal);
        radbtnFatloss.setToggleGroup(toGrpGoal);
        radbtnLean.setToggleGroup(toGrpGoal);

        //positioning
        HBox hboxGoal = new HBox(50, radbtnMaintenance, radbtnFatloss, radbtnLean);
        hboxGoal.setAlignment(Pos.CENTER);

        //titlePane
        TitledPane goalPane = new TitledPane("Step 4: Choose Your Fitness Goal:", hboxGoal);
        goalPane.setCollapsible(false);

        //**********Create btns for Stats, clearing input, and closing app********
        Button btnGetStats = new Button("Get Stats");
        btnGetStats.setMinWidth(150);
        btnGetStats.setMinHeight(25);

        Button btnClear = new Button("Clear");
        btnClear.setMinWidth(150);
        btnClear.setMinHeight(25);

        Button btnExit = new Button("Exit");
        btnExit.setMinWidth(150);
        btnExit.setMinHeight(25);

        //wireing up the btns
        btnGetStats.setOnAction(new btnGetCalcClickHandler());
        btnClear.setOnAction(new btnClearClickHandler());
        btnExit.setOnAction(new btnExitClickHandler());

        //position the btns
        HBox btnBox = new HBox(10, btnGetStats, btnClear, btnExit);

        btnBox.setAlignment(Pos.CENTER);

        //padding
        btnBox.setPadding(new Insets(10,0,0,0));

        //************** output Results*******************
        lblResults = new Label("");
        lblResults.setMinHeight(120);
        lblResults.setPadding(new Insets(0,0,0,10));
        //lblResults = new Label("As a "+", you should consume "+" calories daily for "+".\n\n"+
         //       "Aim for:\n"+
         //       "calories from lean proteins\n" +
         //       "calories from complex carbohydrates\n" +
         //       "calories from healthy fats");

        //************combinding everything to vertical order**********
        VBox bigV = new VBox(5, picBox, enterWeightPane, bodyTypePane, activeLevelPane, goalPane, btnBox, lblResults);


        Scene scene = new Scene(bigV);
        stage.setScene(scene);
        //stage.setScene(new Scene(something, 1000, 1000));
        stage.show();
    }

    class btnExitClickHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){ System.exit(0);}
    }

    class btnClearClickHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            txtWeight.setText("");
            radbtnFemale.setSelected(false);
            radbtnMale.setSelected(false);
            radbtnActive.setSelected(false);
            radbtnModerately.setSelected(false);
            radbtnFatloss.setSelected(false);
            radbtnMaintenance.setSelected(false);
            radbtnLean.setSelected(false);
            radbtnSedentary.setSelected(false);
        }
    }

    class btnGetCalcClickHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){ try {
            int baseline = Integer.parseInt(txtWeight.getText());
            double calAdjustment = 0;
            String gender = "";
            String active = "";
            String goal = "";
            double total = 0;
            double protein = 0;
            double carbs = 0;
            double fats = 0;

            //bodytype
            if (radbtnFemale.isSelected()) {
                total = baseline * 14;
                gender = "female";
            } else if (radbtnMale.isSelected()) {
                total = baseline * 16;
                gender = "male";
            }
            //active lvl
            if (radbtnSedentary.isSelected()) {
                active = "Sedentary";
            } else if (radbtnModerately.isSelected()) {
                calAdjustment = total * .15;
                active = "monderately active";
            } else if (radbtnActive.isSelected()) {
                calAdjustment = total * .25;
                active = "very active";
            }
            //goal
            if (radbtnMaintenance.isSelected()) {
                goal = "maintenance";
                protein = total * .3;
                carbs = total * .4;
                fats = total * .3;
            } else if (radbtnFatloss.isSelected()) {
                goal = "fat loss";
                total += calAdjustment - 250;
                protein = total * .5;
                carbs = total * .3;
                fats = total * .2;
            } else if (radbtnLean.isSelected()) {
                goal = "lean gains";
                total += calAdjustment + 250;
                protein = total * .35;
                carbs = total * .45;
                fats = total * .2;
            }
            DecimalFormat df = new DecimalFormat("#,###");
            String Result = "As a " + gender + ", you should consume " + df.format((int) total) + " calories daily for " + active + ".\n\n" +
                    "Aim for:\n" +
                    (int) protein + " calories from lean proteins\n" +
                    (int) carbs + " calories from complex carbohydrates\n" +
                    (int) fats + " calories from healthy fats";

            lblResults.setText(Result);
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Weight");
            alert.setContentText("Weight must be a positive numeric value.");
            alert.setHeaderText("Please try again.");
            alert.showAndWait();
            }
        }
    }
}


