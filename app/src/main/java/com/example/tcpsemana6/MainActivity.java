package com.example.tcpsemana6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tcpsemana6.model.Usuario;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedWriter write;
    private BufferedReader read;
    private boolean pasar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText user = findViewById(R.id.user);
        EditText password = findViewById(R.id.password);
        Button btnNext = findViewById(R.id.btnNext);

        initCliente();

        btnNext.setOnClickListener(
                (v) -> {

                    Gson gson = new Gson();

                    String id = UUID.randomUUID().toString();
                    String username = user.getText().toString();
                    String thePassword = password.getText().toString();

                    Usuario users = new Usuario (id, username, thePassword);

                    String json = gson.toJson(users);
                    sendMessage(json);



                    }

        );

    }


    public void initCliente () {
        new Thread(

                () -> {

                    try {
                        socket = new Socket("192.168.1.52", 5000);
                        System.out.println("Conectado");
                        Log.d("COOOOOOONECTO", "Conecto");

                        InputStream is = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader (is);
                        read = new BufferedReader (isr);


                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter (os);
                        write = new BufferedWriter (osw);

                        while (true) {
                            String line = read.readLine();

                            Log.d("LLEGOOOOOOOO", ""+line);

                            runOnUiThread(
                                    () -> {
                                        if (line.contains("Exito")) {
                                            Intent i = new Intent (this, Bienvenido.class);

                                            startActivity(i);
                                        }

                                        if (line.contains("Noes"))
                                            Toast.makeText(this, "El usuario no está registrado", Toast.LENGTH_LONG).show();

                                    }


                            );

                            Log.d("MENSAJEEEE", ""+pasar);
                           // read.readLine();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        ).start();
    }

    public void sendMessage (String msg) {
        new Thread (
                () -> {
                    try {
                        write.write(msg+"\n");
                        write.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }


}