package com.example.vhartemam.bingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Gerador de números randômicos para simular o sorteio das pedras
    private Random geraNumeroRandom;

    //Recipinete das pedras enumeradas
    private List<Integer> reciptDePedras = new ArrayList<>();

    //Guardar números sorteados para mostrar na view
    private List<Integer> numerosSorteados = new ArrayList<>();

    //Cartelas
    private List<Integer> card1 = new ArrayList<>();
    private List<Integer> card2 = new ArrayList<>();
    private List<Integer> card3 = new ArrayList<>();

    //Referência para as views do arquivo de layout
    private TextView numeroSorteadoTextView;
    private TextView numeroTextView;
    private TextView card1TextView;
    private TextView card2TextView;
    private TextView card3TextView;
    private TextView bingoTextView;
    private Button sortearButton;

    protected void startCartela(List<Integer> card){
        int posicao;
        startReciptPedras();
        for(int i = 1; i <= 9; i++){
            posicao = geraNumeroRandom.nextInt(reciptDePedras.size());
            card.add(reciptDePedras.remove(posicao));
        }
        Collections.sort(card);
        reciptDePedras.clear();
    }

    protected void startReciptPedras(){
        for(int i = 1; i<= 75; i++){
            reciptDePedras.add(i);
        }
    }

    protected void startBingo(){

        //Inicializando numeros sorteados
        numerosSorteados.clear();

        //Setando cartelas
        startCartela(card1);
        startCartela(card2);
        startCartela(card3);

        //Setando o recipiente de pedras
        startReciptPedras();

        //Setando views
        numeroSorteadoTextView.setText("Números Sorteados: ");
        sortearButton.setText("SORTEAR");
        numeroTextView.setText(null);
        card1TextView.setText("Card 1: " + String.valueOf(card1));
        card2TextView.setText("Card 2: " + String.valueOf(card2));
        card3TextView.setText("Card 3: " + String.valueOf(card3));
        bingoTextView.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Após criar a tela
        geraNumeroRandom = new Random(System.currentTimeMillis());

        //Recuperar referências das views do arquivo de layout
        numeroSorteadoTextView = findViewById(R.id.numeroSorteadoTextView);
        numeroTextView = findViewById(R.id.numeroTextView);
        card1TextView = findViewById(R.id.card1TextView);
        card2TextView = findViewById(R.id.card2TextView);
        card3TextView = findViewById(R.id.card3TextView);
        bingoTextView = findViewById(R.id.bingoTextView);
        sortearButton = findViewById(R.id.sortearButton);

        startBingo();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        //salvar dados
        outState.putIntegerArrayList("reciptDePedras", (ArrayList<Integer>) reciptDePedras);
        outState.putIntegerArrayList("numerosSorteados", (ArrayList<Integer>) numerosSorteados);
        outState.putIntegerArrayList("card1", (ArrayList<Integer>) card1);
        outState.putIntegerArrayList("card2", (ArrayList<Integer>) card2);
        outState.putIntegerArrayList("card3", (ArrayList<Integer>) card3);
        outState.putString("numeroSorteadoTextView", numeroSorteadoTextView.getText().toString());
        outState.putString("numeroTextView", numeroTextView.getText().toString());
        outState.putString("card1TextView", card1TextView.getText().toString());
        outState.putString("card2TextView", card2TextView.getText().toString());
        outState.putString("card3TextView", card3TextView.getText().toString());
        outState.putString("bingoTextView", bingoTextView.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle outState){
        super.onRestoreInstanceState(outState);

        //recuperar dados
        reciptDePedras = outState.getIntegerArrayList("reciptDePedras");
        numerosSorteados = outState.getIntegerArrayList("numerosSorteados");
        card1 = outState.getIntegerArrayList("card1");
        card2 = outState.getIntegerArrayList("card2");
        card3 = outState.getIntegerArrayList("card3");
        numeroSorteadoTextView.setText(outState.getString("numeroSorteadoTextView"));
        numeroTextView.setText(outState.getString("numeroTextView"));
        card1TextView.setText(outState.getString("card1TextView"));
        card2TextView.setText(outState.getString("card2TextView"));
        card3TextView.setText(outState.getString("card3TextView"));
        bingoTextView.setText(outState.getString("bingoTextView"));
    }

    protected boolean checkCard(List<Integer> card, Integer num){
        return card.remove(num);
    }

    protected boolean bingo(){
        String texto = "";
        boolean v_ret = false;
        if (card1.size() == 0){
            texto = "Bingo 1!";
            v_ret = true;
        }

        if (card2.size() == 0){
            texto = "Bingo 2!";
            v_ret = true;
        }

        if (card3.size() == 0){
            texto = "Bingo 3!";
            v_ret = true;
        }
        bingoTextView.setText(texto);
        return v_ret;
    }

    public void sortearPedra(View view){
        //Toast.makeText( this, "AQUI 1", Toast.LENGTH_SHORT).show();
        if(reciptDePedras.size() > 0) {
            int posicaoArrayList = geraNumeroRandom.nextInt(reciptDePedras.size());
            numerosSorteados.add(reciptDePedras.remove(posicaoArrayList));
            numeroTextView.setText(String.valueOf(numerosSorteados.get(numerosSorteados.size() - 1)));

            if(checkCard(card1, numerosSorteados.get(numerosSorteados.size() - 1))) card1TextView.setText("Card 1: " + String.valueOf(card1));;
            if(checkCard(card2, numerosSorteados.get(numerosSorteados.size() - 1))) card2TextView.setText("Card 2: " + String.valueOf(card2));;
            if(checkCard(card3, numerosSorteados.get(numerosSorteados.size() - 1))) card3TextView.setText("Card 3: " + String.valueOf(card3));;

            Collections.sort(numerosSorteados);

            String texto = "Números Sorteados: ";
            texto += String.valueOf(numerosSorteados) + " ";

            numeroSorteadoTextView.setText(texto);

            if (bingo()){
                reciptDePedras.clear();
            }

            if(reciptDePedras.size() == 0){
                sortearButton.setText("Reiniciar");
                card1.clear();
                card2.clear();
                card3.clear();
            }
        }
        else{
            startBingo();
        }

    }
}
