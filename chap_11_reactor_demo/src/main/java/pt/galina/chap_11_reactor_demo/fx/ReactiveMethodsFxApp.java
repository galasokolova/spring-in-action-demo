package pt.galina.chap_11_reactor_demo.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pt.galina.chap_11_reactor_demo.Chap11ReactorDemoApplication;
import pt.galina.chap_11_reactor_demo.service.CombinationService;
import pt.galina.chap_11_reactor_demo.service.CreationService;
import pt.galina.chap_11_reactor_demo.service.LogicService;
import pt.galina.chap_11_reactor_demo.service.TransformationService;
import pt.galina.chap_11_reactor_demo.util.MethodDescriptionUtil;

import java.util.List;

public class ReactiveMethodsFxApp extends Application {

    private ConfigurableApplicationContext springContext;
    private CreationService creationService;
    private CombinationService combinationService;
    private TransformationService transformationService;
    private LogicService logicService;
    private TextArea resultArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = new SpringApplicationBuilder(Chap11ReactorDemoApplication.class).run();
        creationService = springContext.getBean(CreationService.class);
        combinationService = springContext.getBean(CombinationService.class);
        transformationService = springContext.getBean(TransformationService.class);
        logicService = springContext.getBean(LogicService.class);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Reactor Methods Demo");

        resultArea = new TextArea();
        resultArea.setEditable(false);

        String runButton = "RUN";
        VBox vbox = new VBox(10,
                createMethodRow(runButton, MethodDescriptionUtil.getCreateFluxFromArrayDescription(), this::demoCreateFluxArray),
                createMethodRow(runButton, MethodDescriptionUtil.getCreateAFluxIntervalDescription(), this::demoCreateFluxIntervalWithDelay),
                createMethodRow(runButton, MethodDescriptionUtil.getMergeFluxesDescription(), this::demoMergeFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getZipFluxesDescription(), this::demoZipFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getFirstWithSignalDescription(), this::demoFirstWithSignalFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getSkipAFewDescription(), this::demoSkipFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getTakeMethodDescription(), this::demoTakeFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getFilterMethodDescription(), this::demoFilterFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getDistinctMethodDescription(), this::demoDistinctFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getMapMethodDescription(), this::demoMapFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getFlatMapMethodDescription(), this::demoFlatMapFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getBufferMethodDescription(), this::demoBufferFlux),
                createMethodRow(runButton, MethodDescriptionUtil.getCollectListMethodDescription(), this::demoCollectList),
                createMethodRow(runButton, MethodDescriptionUtil.getCollectMapDescription(), this::demoCollectMap),
                createMethodRow(runButton, MethodDescriptionUtil.getAllMethodDescription(), this::demoAllMatchLogic),
                createMethodRow(runButton, MethodDescriptionUtil.getAnyMethodDescription(), this::demoAnyMatchLogic),
                resultArea
        );

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createMethodRow(String buttonText, String methodDescription, Runnable action) {
        Button button = new Button(buttonText);
        Label label = new Label(methodDescription);
        button.setOnAction(e -> action.run());

        return new HBox(10, button, label);  // 10 - distance between the button and the text
    }



    private void demoCreateFluxArray() {
        resultArea.clear();
        creationService.createAFluxFromArray(new String[]{"Apple", "Orange", "Grape", "Banana", "Strawberry"})
                .subscribe(fruit -> Platform.runLater(() -> resultArea.appendText("⏩ fromArray(Array) : " + fruit + "\n")));
    }

    private void demoCreateFluxIntervalWithDelay() {
        resultArea.clear();
        creationService.createAFluxInterval(1L, 5)
                .subscribe(aLong -> Platform.runLater(() -> resultArea.appendText("⏩ Interval value: " + aLong + "\n")));
    }

    private void demoMergeFlux() {
        resultArea.clear();
        combinationService.mergeFluxes(
                List.of("Garfield", "Kojak", "Barbossa"),
                List.of("Lasagna", "Lollipops", "Apples"),
                500L,
                250L,
                500L
        ).subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ Merged: " + f + "\n")));
    }

    private void demoZipFlux() {
        resultArea.clear();
        combinationService.zipFluxes(
                List.of("Garfield", "Kojak", "Barbossa"),
                List.of("Lasagna", "Lollipops", "Apples")
        ).subscribe(zip -> Platform.runLater(() -> resultArea.appendText("⏩ Zip: " + zip + "\n")));
    }

    private void demoFirstWithSignalFlux() {
        resultArea.clear();
        combinationService.firstWithSignalFlux(
                List.of("tortoise", "snail", "sloth"),
                List.of("hare", "cheetah", "squirrel")
        ).subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ First with signal: " + f + "\n")));
    }

    private void demoSkipFlux() {
        resultArea.clear();
        transformationService.skipAFew(
                List.of("one", "two", "skip a few", "ninety nine", "one hundred"),
                3
        ).subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ Skipped to: " + f + "\n")));
    }

    private void demoTakeFlux() {
        resultArea.clear();
        transformationService.take(
                List.of("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia"),
                3
        ).subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ Take: " + f + "\n")));
    }

    private void demoFilterFlux() {
        resultArea.clear();
        transformationService.filter(List.of("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia"))
                .subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ Filtered: doesn´t contain  \" \"  in its name : " + f + "\n")));
    }

    private void demoDistinctFlux() {
        resultArea.clear();
        transformationService.distinct(List.of("dog", "cat", "bird", "dog", "bird", "anteater"))
                .subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ Returns unique elements: " + f + "\n")));
    }

    private void demoMapFlux() {
        resultArea.clear();
        transformationService.map(List.of("Michael Jordan", "Scottie Pippen", "Steve Kerr"))
                .subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ Mapped: " + f + "\n")));
    }

    private void demoFlatMapFlux() {
        resultArea.clear();
        transformationService.flatMap(List.of("Michael Jordan", "Scottie Pippen", "Steve Kerr"))
                .subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ FlatMapped: " + f + "\n")));
    }

    private void demoBufferFlux() {
        resultArea.clear();
        transformationService.buffer(
                List.of("apple", "orange", "banana", "kiwi", "strawberry"),
                3
        ).subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ Buffered: " + f + "\n")));
    }

    private void demoCollectList() {
        resultArea.clear();
        transformationService.collectList("apple", "orange", "banana", "kiwi", "strawberry")
                .subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ Collected List: " + f + "\n")));
    }

    private void demoCollectMap() {
        resultArea.clear();
        transformationService.collectMap("aardvark", "elephant", "koala", "eagle", "kangaroo")
                .subscribe(f -> Platform.runLater(() -> resultArea.appendText("⏩ Collected Map: " + f + "\n")));
    }

    private void demoAllMatchLogic() {
        resultArea.clear();
        logicService.all("a", "aardvark", "elephant", "koala", "eagle", "kangaroo")
                .subscribe(f -> Platform.runLater(() -> resultArea.appendText("☑️all elements contain letter \"A\" : " + f + "\n")));
    }

    private void demoAnyMatchLogic() {
        resultArea.clear();
        logicService.any("w", "aardvark", "elephant", "koala", "eagle", "kangaroo")
                .subscribe(f -> Platform.runLater(() -> resultArea.appendText("☑️any() elements contain \"W\" ? : " + f + "\n")));
    }

    @Override
    public void stop() throws Exception {
        if (springContext != null) {
            springContext.close();
        }
    }
}
