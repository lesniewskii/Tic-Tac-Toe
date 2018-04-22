package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 2/26/2018.
 */
public class TicTacToe extends Application {

    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(600,600);

        for(int i=0; i < 3; i++){
            for(int j =0; j < 3; j++){
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }

        // pion
        for (int y =0; y < 3; y++){
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }

        // poziom
        for (int x=0; x < 3; x++){
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }

        // skos

        for (int y =0; y < 3; y++){
            combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        }

        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();

    }

    private void checkstate(){

        for(Combo combo : combos){
            if(combo.isComplete()){
                playable = false;
                break;
            }
        }

    }

    private void playWinAnimation(Combo combo) {

    }

    private class Combo{
        private Tile[] tiles;
        public Combo(Tile... tiles){
            this.tiles = tiles;
        }

        public boolean isComplete(){
            if(tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    private class Tile extends StackPane{
        private Text text = new Text();


        public Tile(){
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                if(!playable)
                    return;

                if(event.getButton() == MouseButton.PRIMARY){
                    if(!turnX)
                        return;

                    drawX();
                    turnX = false;
                    checkstate();
                }

                else if(event.getButton() == MouseButton.SECONDARY){
                    if(turnX)
                        return;
                    drawO();
                    turnX = true;
                    checkstate();
                }

            });
        }

        public String getValue(){
            return text.getText();
        }

        private void drawX(){
            text.setText("X");
        }

        private void drawO(){
            text.setText("O");
        }
    }

    public static void main(String[] args){

        launch(args);
    }
}