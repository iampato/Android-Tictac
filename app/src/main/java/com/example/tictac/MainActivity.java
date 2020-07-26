package com.example.tictac;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView whichPlayerTurn;
    private Button[][] buttons = new Button[3][3];

    private boolean player1turn = true;
    private int rounds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whichPlayerTurn = findViewById(R.id.textPlayerTurn);
        whichPlayerTurn.setText("Player 1 turn");

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                String buttonID = "button_" + x + y;
                int resourceId = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[x][y] = findViewById(resourceId);
                buttons[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(((Button) v).getText().toString().equals(""))) {
                            return;
                        }
                        if (player1turn) {
                            ((Button) v).setText("X");
                        } else {
                            ((Button) v).setText("O");
                        }
                        rounds++;

                        if (hasSomeoneWon()) {
                            if (player1turn) {
                                showAlert("Player 1 won", getApplicationContext());
                            } else {
                                showAlert("Player 2 won", getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Player 2 won", Toast.LENGTH_LONG).show();
                            }
                        } else if (rounds == 9) {
                            showAlert("A draw", getApplicationContext());
                        } else {
                            player1turn = !player1turn;

                            if (!player1turn) {
                                whichPlayerTurn.setText("Player 2 turn");
                            } else {
                                whichPlayerTurn.setText("Player 1 turn");
                            }
                        }
                    }
                });
            }
        }
    }

    public boolean hasSomeoneWon() {
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

    private void restart() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        rounds = 0;
        player1turn = true;
    }

    public void showAlert(final String msg, Context context) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (!isFinishing()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Game Ended")
                            .setMessage(msg)
                            .setCancelable(false)
                            .setNegativeButton("exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    System.exit(0);
                                }
                            })
                            .setPositiveButton("restart", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    restart();
                                }
                            }).show();
                }
            }
        });
    }
}