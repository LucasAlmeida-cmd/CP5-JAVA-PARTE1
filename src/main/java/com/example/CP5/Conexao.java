package com.example.CP5;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Conexao {


    public static void enviar(DataOutputStream out, String mensagem) throws IOException {
        out.writeUTF(mensagem);
        out.flush();
    }

    public static String receber(DataInputStream in) throws IOException {
        return in.readUTF();
    }
}