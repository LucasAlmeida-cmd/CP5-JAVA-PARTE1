/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CP5;

/**
 *
 * @author MSWagner
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Base64;

public class Conexao {

    public static String receber(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        /* 2048 / 8 = 256 bytes */
        byte infoBytes[] = new byte[256];
        int bytesLidos = in.read(infoBytes);

        if (bytesLidos > 0) {
            return Base64.getEncoder().encodeToString(infoBytes);
        } else {
            return "";
        }
    }

    public static PublicKey receberChave(Socket socket) throws Exception{
        InputStream in = socket.getInputStream();
        byte infoBytes[] = new byte[2048];
        int bytesLidos = in.read(infoBytes);

        if (bytesLidos > 0) {
            return CriptografiaClienteServidor.bytesParaChave(infoBytes);
        } else {
            return null;
        }
    }

    public static void enviarChave(Socket socket, PublicKey chave) throws IOException{
        OutputStream out = socket.getOutputStream();
        out.write(chave.getEncoded());
    }

    public static void enviar(Socket socket, String textoRequisicao) throws IOException {
        byte[] bytesRequisicao = Base64.getDecoder().decode(textoRequisicao);
        OutputStream out = socket.getOutputStream();
        out.write(bytesRequisicao);
    }

}
