package com.example.tictactoe;

        import androidx.appcompat.app.AppCompatActivity;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //変数設定
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Point;
    private int player2Point;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }
    //○×入力、ターンカウント
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("○");
            ((Button) v).setTextColor(Color.RED);
        } else {
            ((Button) v).setText("×");
            ((Button) v).setTextColor(Color.BLUE);
        }
        roundCount++;
        //ターン判定
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
            //全マス埋まり判定
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }
    //３連揃い判定
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
    //プレイヤー１の勝利時のプログラム
    private void player1Wins(){
        player1Point++;
        Toast.makeText(this,"player1 WIN!!",Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }
    //プレイヤー２の勝利時のプログラム
    private void player2Wins(){
        player2Point++;
        Toast.makeText(this,"player2 WIN!!",Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }
    //引き分け時のプログラム
    private void draw(){
        Toast.makeText(this,"Drow!!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    //勝負終了時のポイント加算プログラム
    private void updatePointText(){
        textViewPlayer1.setText("（○）Player 1:" + player1Point);
        textViewPlayer2.setText("（×）Player 2:" + player2Point);

    }
    //盤面リセット
    private void resetBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }
    //スコアリセット
    private void resetGame(){
        resetBoard();
        player1Point = 0;
        player2Point = 0;
        updatePointText();
        resetBoard();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Point);
        outState.putInt("player2Points", player2Point);
        outState.putBoolean("player1Turn", player1Turn);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Point = savedInstanceState.getInt("player1Points");
        player2Point = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
